package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组织-志愿者挂靠审核分页 Request VO")
@Data
public class YwMemberApplyPageReqVO extends PageParam {

    @Schema(description = "组织ID", example = "12712")
    private Long orgId;

    @Schema(description = "志愿者ID", example = "21791")
    private Long memberId;

    @Schema(description = "审核状态 0-待审核 1-通过 2-拒绝", example = "1")
    private Integer status;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "审核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] auditTime;

    @Schema(description = "审核人ID")
    private Long auditUser;

    @Schema(description = "审核意见", example = "不通过")
    private String auditReason;

    private String memberRealName; // 志愿者姓名
    private String idCard;         // 志愿者身份证号

}
