package cn.iocoder.yudao.module.yw.ps.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

// 聊天记录DTO
@Data
@AllArgsConstructor
public
class ChatRecord {
    private String userMessage;
    private String botMessage;
    private Date timestamp;
}
