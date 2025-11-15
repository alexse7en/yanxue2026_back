package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 组织-志愿者挂靠审核新增/修改 Request VO")
@Data
public class YwMemberApplySaveReqVO {

    @Schema(description = "表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2342")
    private Long id;

    @Schema(description = "组织ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12712")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @Schema(description = "志愿者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21791")
    @NotNull(message = "志愿者ID不能为空")
    private Long memberId;

    @Schema(description = "审核状态 0-待审核 1-通过 2-拒绝", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审核状态 0-待审核 1-通过 2-拒绝不能为空")
    private Integer status;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请时间不能为空")
    private LocalDateTime applyTime;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人ID")
    private Long auditUser;

    @Schema(description = "审核意见", example = "不喜欢")
    private String auditReason;



}
