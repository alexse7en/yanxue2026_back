package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 会员申请审核 Request VO")
@Data
public class YwVipApplyAuditReqVO {

    @Schema(description = "申请 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请 ID 不能为空")
    private Long id;

    @Schema(description = "审核状态：10-通过 11-不通过", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审核状态不能为空")
    private Integer applyStatus;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "会员编号")
    private String memberNo;

    @Schema(description = "会员编号后 3 位")
    private String memberNoSuffix;
}
