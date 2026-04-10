package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 二级认证申请解析 Request VO")
@Data
public class YwOrgApplyParseReqVO {

    @NotBlank(message = "申请类型不能为空")
    @Schema(description = "申请类型：base/organization/support", requiredMode = Schema.RequiredMode.REQUIRED)
    private String applyType;

    @NotBlank(message = "文件地址不能为空")
    @Schema(description = "文件地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;

    @Schema(description = "文件类型")
    private String fileType;
}

