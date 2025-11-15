package cn.iocoder.yudao.module.yw.ps.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.ps.service.impl.ChatRecord;
import cn.iocoder.yudao.module.yw.ps.service.impl.CozeResponse;
import cn.iocoder.yudao.module.yw.ps.service.impl.CozeService;
import cn.iocoder.yudao.module.yw.vo.resp.YwArticleRespVO;
import com.coze.openapi.client.auth.OAuthToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@RestController
@RequestMapping("/api/coze")
public class ChatController {

    @Value("${coze.bot-id}")
    private String botId;

    @Value("${coze.api-key}")
    private String apiKey;
    @Autowired
    private CozeService cozeService;

    // 获取用户token
    @GetMapping("/token")
    public CommonResult<Map<String, Object>> getToken(@RequestParam("userId") String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = cozeService.generateUserToken(userId);
            response.put("success", true);
            response.put("token", token);
            response.put("message", "token生成成功");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "token生成失败: " + e.getMessage());
        }
        return success(response);
    }

    // 发送消息
    @PostMapping("/message")
    public Map<String, Object> sendMessage(
            @RequestHeader("userId") String userId,
            @RequestBody Map<String, String> request) {

        String message = request.get("message");
        Map<String, Object> response = cozeService.sendMessage(userId, message);

        return response;
    }

    // 获取聊天历史
    @GetMapping("/history")
    public CommonResult<List<Map<String, Object>>> getChatHistory(@RequestParam("userId") String userId) {
        List<Map<String, Object>> history = cozeService.getChatHistory(userId);
        return success(history);
    }

    @PermitAll
    @GetMapping("/chat-page")
    public String getChatPage(@RequestParam("userId") String userId,
                              HttpServletResponse response) {
        try {
            // 设置响应类型为HTML
            response.setContentType("text/html;charset=UTF-8");

            // 为用户生成专属token
            String userToken = cozeService.generateUserToken(userId);

            // 构建HTML页面
            String html = buildChatHtml(userId, userToken);
            return html;

        } catch (Exception e) {
            e.printStackTrace();
            return "<html><body>页面生成失败</body></html>";
        }
    }
    private String buildChatHtml(String userId, String userToken) {
        return "<!doctype html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\" />\n" +
                "  <title>AI助手</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\" />\n" +
                "  <!-- 引入微信 JSSDK -->\n" +
                "  <script src=\"https://res.wx.qq.com/open/js/jweixin-1.6.0.js\"></script>\n" +
                "  <style>\n" +
                "    html,body { margin:0; height:100%; }\n" +
                "    #coze-container { \n" +
                "      width:100%; \n" +
                "      height:50%; /* 只占用一半高度 */\n" +
                "      position: relative;\n" +
                "      top: 50%; /* 从页面中间开始 */\n" +
                "    }\n" +
                "    \n" +
                "    /* 固定顶部返回按钮 */\n" +
                "    .fixed-header {\n" +
                "      position: fixed;\n" +
                "      top: 0;\n" +
                "      left: 0;\n" +
                "      width: 100%;\n" +
                "      height: 50px;\n" +
                "      background: #fff;\n" +
                "      border-bottom: 1px solid #e0e0e0;\n" +
                "      display: flex;\n" +
                "      align-items: center;\n" +
                "      padding: 0 15px;\n" +
                "      z-index: 1000;\n" +
                "      box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n" +
                "    }\n" +
                "    \n" +
                "    .back-button {\n" +
                "      background: none;\n" +
                "      border: none;\n" +
                "      font-size: 16px;\n" +
                "      color: #007AFF;\n" +
                "      cursor: pointer;\n" +
                "      display: flex;\n" +
                "      align-items: center;\n" +
                "      gap: 8px;\n" +
                "      padding: 8px 12px;\n" +
                "    }\n" +
                "    \n" +
                "    .back-button:hover {\n" +
                "      background: #f5f5f5;\n" +
                "      border-radius: 6px;\n" +
                "    }\n" +
                "    \n" +
                "    .header-title {\n" +
                "      font-size: 18px;\n" +
                "      font-weight: 600;\n" +
                "      margin-left: 15px;\n" +
                "    }\n" +
                "    \n" +
                "    .loading {\n" +
                "      display: flex;\n" +
                "      justify-content: center;\n" +
                "      align-items: center;\n" +
                "      height: 100vh;\n" +
                "      font-size: 16px;\n" +
                "      color: #666;\n" +
                "    }\n" +
                "    \n" +
                "    /* 调整聊天区域样式 */\n" +
                "    .chat-area {\n" +
                "      height: 50vh;\n" +
                "      margin-top: 50px; /* 为固定头部留出空间 */\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <!-- 固定顶部返回按钮 -->\n" +
                "  <div class=\"fixed-header\">\n" +
                "    <button class=\"back-button\" id=\"backButton\">\n" +
                "      <svg width=\"16\" height=\"16\" viewBox=\"0 0 24 24\" fill=\"currentColor\">\n" +
                "        <path d=\"M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z\"/>\n" +
                "      </svg>\n" +
                "      返回\n" +
                "    </button>\n" +
                "    <div class=\"header-title\">AI生成，仅供参考</div>\n" +
                "  </div>\n" +
                "\n" +
                "  <!-- 聊天容器 -->\n" +
                "  <div id=\"coze-container\">\n" +
                "    <div class=\"loading\">加载中...</div>\n" +
                "  </div>\n" +
                "\n" +
                "  <script>\n" +
                "  // 修改后的 handleBack 函数\n" +
                "  function handleBack() {\n" +
                "    console.log('返回按钮被点击');\n" +
                "    wx.miniProgram.switchTab({url: '/pages/index/index'   });"+
        "    // 适配小程序的通信方式\n" +
                "  }\n" +
                "\n" +
                "  (function () {\n" +
                "    // 您的原有初始化代码保持不变\n" +
                "    function getQuery(name) {\n" +
                "      try {\n" +
                "        return new URLSearchParams(location.search).get(name);\n" +
                "      } catch (e) { return null; }\n" +
                "    }\n" +
                "\n" +
                "    const SDK_URL = 'https://lf-cdn.coze.cn/obj/unpkg/flow-platform/chat-app-sdk/1.2.0-beta.10/libs/cn/index.js';\n" +
                "\n" +
                "    function loadSdk(onLoad) {\n" +
                "      if (window.CozeWebSDK) { return onLoad(); }\n" +
                "      const s = document.createElement('script');\n" +
                "      s.src = SDK_URL;\n" +
                "      s.async = true;\n" +
                "      s.onload = onLoad;\n" +
                "      document.head.appendChild(s);\n" +
                "    }\n" +
                "\n" +
                "    // 绑定返回按钮事件 - 改进版本\n" +
                "    function bindBackButton() {\n" +
                "      const backButton = document.getElementById('backButton');\n" +
                "      if (backButton) {\n" +
                "        // 移除可能已存在的事件监听器\n" +
                "        backButton.removeEventListener('click', handleBack);\n" +
                "        // 添加新的事件监听器\n" +
                "        backButton.addEventListener('click', handleBack);\n" +
                "        console.log('返回按钮事件绑定成功');\n" +
                "        \n" +
                "        // 测试按钮是否可点击\n" +
                "        backButton.style.cursor = 'pointer';\n" +
                "        backButton.disabled = false;\n" +
                "      } else {\n" +
                "        console.error('返回按钮元素未找到');\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    function initCoze() {\n" +
                "      if (!window.CozeWebSDK) {\n" +
                "        console.error('CozeWebSDK 未定义');\n" +
                "        document.getElementById('coze-container').innerHTML = '<div class=\"loading\">SDK加载失败</div>';\n" +
                "        return;\n" +
                "      }\n" +
                "\n" +
                "      try {\n" +
                "        const BOT_ID = '" + botId + "';\n" +
                "        const token = '" + userToken + "';\n" +
                "        const userId = '" + userId + "';\n" +
                "        \n" +
                "        // 使用用户ID生成唯一的会话ID，确保用户隔离\n" +
                "        const convId = 'conv_' + userId + '_' + Date.now();\n" +
                "        \n" +
                "        console.log('初始化Coze聊天，用户ID:', userId, '会话ID:', convId);\n" +
                "\n" +
                "        const client = new CozeWebSDK.WebChatClient({\n" +
                "          config: {\n" +
                "            type: 'bot',\n" +
                "            bot_id: BOT_ID,\n" +
                "            conversation_id: convId, // 每个用户独立会话\n" +
                "            isIframe: false\n" +
                "          },\n" +
                "          auth: {\n" +
                "            type: 'token',\n" +
                "            token: token,\n" +
                "            onRefreshToken: () => token\n" +
                "          },\n" +
                "          componentProps: {\n" +
                "            title: 'AI生成，仅供参考',\n" +
                "            // 隐藏SDK自带的头部，使用我们自定义的头部\n" +
                "            header: {\n" +
                "              isNeed: false // 隐藏SDK自带的头部\n" +
                "            }\n" +
                "          },\n" +
                "          ui: {\n" +
                "            base: { \n" +
                "              layout: 'mobile', \n" +
                "              zIndex: -1,\n" +
                "              height: '50vh' // 设置聊天区域高度\n" +
                "            },\n" +
                "            asstBtn: { isNeed: false },\n" +
                "            footer: { \n" +
                "              isShow: true, \n" +
                "              expressionText: '本回答由AI生成，仅供参考', \n" +
                "            }\n" +
                "          }\n" +
                "        });\n" +
                "\n" +
                "        if (typeof client.showChatBot === 'function') {\n" +
                "          client.showChatBot();\n" +
                "        } else if (typeof client.showChatWidget === 'function') {\n" +
                "          client.showChatWidget();\n" +
                "        } else if (typeof client.mount === 'function') {\n" +
                "          // 如果使用mount方法\n" +
                "          client.mount(document.getElementById('coze-container'));\n" +
                "        }\n" +
                "\n" +
                "        window.__cozeClient = client;\n" +
                "        \n" +
                "        console.log('Coze聊天初始化成功');\n" +
                "        \n" +
                "        // 通知小程序加载完成\n" +
                "        if (typeof wx !== 'undefined' && wx.miniProgram) {\n" +
                "          wx.miniProgram.postMessage({\n" +
                "            data: {\n" +
                "              type: 'chat_ready',\n" +
                "              userId: userId,\n" +
                "              message: '聊天加载完成'\n" +
                "            }\n" +
                "          });\n" +
                "        }\n" +
                "      } catch (error) {\n" +
                "        console.error('Coze初始化失败:', error);\n" +
                "        document.getElementById('coze-container').innerHTML = '<div class=\"loading\">聊天加载失败: ' + error.message + '</div>';\n" +
                "        \n" +
                "        // 通知小程序加载失败\n" +
                "        if (typeof wx !== 'undefined' && wx.miniProgram) {\n" +
                "          wx.miniProgram.postMessage({\n" +
                "            data: {\n" +
                "              type: 'chat_error',\n" +
                "              message: error.message\n" +
                "            }\n" +
                "          });\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    // 处理来自小程序的消息\n" +
                "    window.addEventListener('message', function(event) {\n" +
                "      console.log('收到小程序消息:', event.data);\n" +
                "    });\n" +
                "\n" +
                "    // 页面加载完成后绑定事件 - 改进的初始化顺序\n" +
                "    document.addEventListener('DOMContentLoaded', function() {\n" +
                "      console.log('DOM加载完成，开始初始化');\n" +
                "      \n" +
                "      // 先绑定返回按钮\n" +
                "      bindBackButton();\n" +
                "      \n" +
                "      // 等待JSSDK加载完成后再加载Coze SDK\n" +
                "      function waitForJSSDK(callback) {\n" +
                "        if (typeof wx !== 'undefined') {\n" +
                "          console.log('微信JSSDK已加载');\n" +
                "          callback();\n" +
                "        } else {\n" +
                "          console.log('等待微信JSSDK加载...');\n" +
                "          setTimeout(() => waitForJSSDK(callback), 100);\n" +
                "        }\n" +
                "      }\n" +
                "      \n" +
                "      waitForJSSDK(() => {\n" +
                "        loadSdk(() => setTimeout(initCoze, 80));\n" +
                "      });\n" +
                "    });\n" +
                "\n" +
                "    // 备用方案：在window.onload时重新绑定事件\n" +
                "    window.onload = function() {\n" +
                "      console.log('窗口完全加载完成，重新绑定返回按钮');\n" +
                "      setTimeout(bindBackButton, 100);\n" +
                "    };\n" +
                "\n" +
                "  })();\n" +
                "  </script>\n" +
                "</body>\n" +
                "</html>";
    }

}
