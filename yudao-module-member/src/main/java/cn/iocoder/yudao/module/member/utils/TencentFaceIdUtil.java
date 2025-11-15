package cn.iocoder.yudao.module.member.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.IdCardVerificationRequest;
import com.tencentcloudapi.faceid.v20180301.models.IdCardVerificationResponse;

/**
 * 腾讯云人脸核身工具类 (单例模式)
 * 封装身份证验证功能（基础版）
 */
public class TencentFaceIdUtil {

    // 单例实例
    private static volatile FaceidClient client;

    // 默认配置参数
    private static final String DEFAULT_ENDPOINT = "faceid.tencentcloudapi.com";
    private static final String DEFAULT_REGION = "ap-guangzhou";
    private static final int DEFAULT_TIMEOUT = 30; // 秒

    // 私有构造器防止外部实例化
    private TencentFaceIdUtil() {}

    /**
     * 身份证验证
     */
    public static Boolean verifyIdCard(String secretId, String secretKey,
                                                          String name, String idCard)
            throws TencentCloudSDKException {
        // 参数校验
        validateParameters(secretId, secretKey, name, idCard);

        // 获取单例客户端
        FaceidClient client = getInstance(secretId, secretKey);

        // 创建请求对象
        IdCardVerificationRequest req = new IdCardVerificationRequest();
        req.setName(name);
        req.setIdCard(idCard);

        // 发送请求
        IdCardVerificationResponse response= client.IdCardVerification(req);
        if(response.getResult()!=null && response.getResult().equals("0")){
            return true;
        }
        return false;
    }

    /**
     * 获取单例Faceid客户端（线程安全）
     */
    private static FaceidClient getInstance(String secretId, String secretKey) {
        if (client == null) {
            synchronized (TencentFaceIdUtil.class) {
                if (client == null) {
                    client = createClient(secretId, secretKey);
                }
            }
        }
        return client;
    }

    /**
     * 创建客户端实例
     */
    private static FaceidClient createClient(String secretId, String secretKey) {
        Credential cred = new Credential(secretId, secretKey);

        // 配置HTTP参数
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(DEFAULT_ENDPOINT);
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(DEFAULT_TIMEOUT);
        httpProfile.setReadTimeout(DEFAULT_TIMEOUT);

        // 配置客户端参数
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

        return new FaceidClient(cred, DEFAULT_REGION, clientProfile);
    }

    // 以下方法保持不变...
    private static void validateParameters(String secretId, String secretKey, String name, String idCard) {
        if (secretId == null || secretId.trim().isEmpty()) {
            throw new IllegalArgumentException("SecretId不能为空");
        }
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalArgumentException("SecretKey不能为空");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }
        if (idCard == null || idCard.trim().isEmpty()) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        // 简单的身份证格式校验
        if (!idCard.matches("^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$")) {
            throw new IllegalArgumentException("身份证格式不正确");
        }
    }

    public static String formatResponse(IdCardVerificationResponse response) {
        if (response == null) return "无响应数据";

        StringBuilder sb = new StringBuilder();
        sb.append("\n===== 身份证核验结果 =====\n");
        sb.append("请求ID: ").append(response.getRequestId()).append("\n");
        sb.append("姓名: ").append(response.getResult()).append("\n");
        sb.append("身份证号: ").append(response.getDescription()).append("\n");

        return sb.toString();
    }

    public static String handleTencentException(TencentCloudSDKException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n>>>> 腾讯云API错误 <<<<\n");
        sb.append("错误码: ").append(e.getErrorCode()).append("\n");
        sb.append("请求ID: ").append(e.getRequestId()).append("\n");
        sb.append("错误信息: ").append(e.getMessage()).append("\n");

        // 常见错误码处理建议
        if ("AuthFailure.SecretIdNotFound".equals(e.getErrorCode())) {
            sb.append("处理建议: 检查SecretId是否正确或是否已被禁用\n");
        } else if ("InvalidParameterValue.InvalidIdCard".equals(e.getErrorCode())) {
            sb.append("处理建议: 身份证格式错误，请检查身份证号码\n");
        } else if ("RequestLimitExceeded".equals(e.getErrorCode())) {
            sb.append("处理建议: 请求频率超限，请稍后重试或联系腾讯云提升配额\n");
        } else if ("ResourceUnavailable.ServiceIsolate".equals(e.getErrorCode())) {
            sb.append("处理建议: 服务已欠费停用，请充值后使用\n");
        } else if ("InternalError".equals(e.getErrorCode())) {
            sb.append("处理建议: 腾讯云内部错误，请稍后重试\n");
        }

        return sb.toString();
    }
}