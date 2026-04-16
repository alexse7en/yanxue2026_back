package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YwCertStudentApplyAuditReqVO {

    @NotNull(message = "申请批次 ID 不能为空")
    private Long id;

    @NotNull(message = "审核状态不能为空")
    private Integer applyStatus;

    private String auditRemark;
}
