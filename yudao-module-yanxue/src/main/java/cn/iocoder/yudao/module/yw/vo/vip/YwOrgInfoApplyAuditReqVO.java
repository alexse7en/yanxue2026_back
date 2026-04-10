package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 二级认证展示资料编辑申请审核 Request VO")
@Data
public class YwOrgInfoApplyAuditReqVO {

    @Schema(description = "申请 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请 ID 不能为空")
    private Long id;

    @Schema(description = "审核状态：1-通过 2-拒绝", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审核状态不能为空")
    private Integer applyStatus;

    @Schema(description = "审核备注")
    private String auditRemark;
}

