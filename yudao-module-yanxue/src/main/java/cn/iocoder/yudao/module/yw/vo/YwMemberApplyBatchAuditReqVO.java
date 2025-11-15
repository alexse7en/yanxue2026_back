package cn.iocoder.yudao.module.yw.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class YwMemberApplyBatchAuditReqVO {
    @NotEmpty(message = "ID列表不能为空")
    private List<Long> ids;

    @NotNull(message = "审核状态不能为空")
    private Integer status; // 1-通过，2-拒绝

    private String auditReason; // 审核意见，可选
}
