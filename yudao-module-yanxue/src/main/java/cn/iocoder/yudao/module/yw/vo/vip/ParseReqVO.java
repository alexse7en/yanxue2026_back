package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParseReqVO {
    private String applyType;
    @NotBlank(message = "filePath 不能为空")
    private String filePath;
    @NotBlank(message = "fileType 不能为空")
    private String fileType;
}
