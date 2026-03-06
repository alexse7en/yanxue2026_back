package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParseReqVO {

    @NotBlank(message = "文件地址不能为空")
    private String filePath;

    private String fileType;
}