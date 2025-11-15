package cn.iocoder.yudao.module.yw.ps.service.impl;

import lombok.Data;

// 响应DTO
@Data
public class CozeResponse {
    private String message;
    private String conversationId;
    private Boolean success;
}
