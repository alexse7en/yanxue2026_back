package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.hutool.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.service.auth.JWTOAuthClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CozeService {

    @Value("${coze.api-key}")
    private String apiKey;

    @Value("${coze.bot-id}")
    private String botId;

    @Value("${coze.api-url}")
    private String url;

    @Value("${coze.public-key}")
    private String publicKey;

    @Value("${coze.private-key}")
    private String privateKey;

    @Value("${coze.oauth-key}")
    private String oauthKey;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 生成简单的用户token（不使用JWT）
    public String generateUserToken(String userId) {
        try {
            // 检查缓存中是否有有效的token
            String cacheKey = "coze:access_token:" + userId;
            String cachedToken = (String) redisTemplate.opsForValue().get(cacheKey);

            if (cachedToken != null) {
                return cachedToken;
            }

            JWTOAuthClient oauth = null;
            try {
                oauth =
                        new JWTOAuthClient.JWTOAuthBuilder()
                                .clientID(oauthKey)
                                .privateKey(privateKey)
                                .publicKey(publicKey)
                                .baseURL(url)
                                .build();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            String resp=null;
            try {
                resp = oauth.getAccessToken(userId).getAccessToken();
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (resp != null) {
                // 缓存访问令牌，设置合适的过期时间（通常1小时）
                redisTemplate.opsForValue().set(cacheKey, resp, 10, TimeUnit.MINUTES);
                return resp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 验证token
    public boolean validateToken(String token) {
        String tokenKey = "user:token:" + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }

    // 从token获取用户ID
    public String getUserIdFromToken(String token) {
        String tokenKey = "user:token:" + token;
        return (String) redisTemplate.opsForValue().get(tokenKey);
    }

    // 发送消息到Coze
    public Map<String, Object> sendMessage(String userId, String message) {
        String url = "https://api.coze.cn/v3/chat";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("bot_id", botId);
            requestBody.put("user_id", userId);
            requestBody.put("message", message);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            Map<String, Object> responseBody = response.getBody();

            // 保存聊天记录
            if (responseBody != null && responseBody.containsKey("message")) {
                String botResponse = (String) responseBody.get("message");
                saveChatRecord(userId, message, botResponse);
            }

            return responseBody != null ? responseBody : new HashMap<>();

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "请求Coze API失败: " + e.getMessage());
            return errorResponse;
        }
    }

    // 保存聊天记录
    private void saveChatRecord(String userId, String userMessage, String botMessage) {
        try {
            String key = "chat:history:" + userId;
            Map<String, Object> record = new HashMap<>();
            record.put("userMessage", userMessage);
            record.put("botMessage", botMessage);
            record.put("timestamp", System.currentTimeMillis());

            // 使用Redis存储
            redisTemplate.opsForList().rightPush(key, record);

            // 只保留最近50条记录
            Long size = redisTemplate.opsForList().size(key);
            if (size != null && size > 50) {
                redisTemplate.opsForList().leftPop(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取聊天历史
    public List<Map<String, Object>> getChatHistory(String userId) {
        try {
            String key = "chat:history:" + userId;
            List<Object> records = redisTemplate.opsForList().range(key, 0, -1);

            if (records == null) {
                return new ArrayList<>();
            }

            List<Map<String, Object>> result = new ArrayList<>();
            for (Object record : records) {
                if (record instanceof Map) {
                    result.add((Map<String, Object>) record);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 清空聊天记录
    public void clearChatHistory(String userId) {
        try {
            String key = "chat:history:" + userId;
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

