package cn.iocoder.yudao.module.yw.vo;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class YwMemberApplyAuditReqVO {
    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "审核状态不能为空")
    private Integer status; // 1-通过，2-拒绝

    private String auditReason; // 审核意见，可选
}
