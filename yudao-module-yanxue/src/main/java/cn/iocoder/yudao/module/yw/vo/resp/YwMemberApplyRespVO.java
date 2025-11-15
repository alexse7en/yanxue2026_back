package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 组织-志愿者挂靠审核 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwMemberApplyRespVO {

    @Schema(description = "表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2342")
    @ExcelProperty("表ID")
    private Long id;

    @Schema(description = "组织ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12712")
    @ExcelProperty("组织ID")
    private Long orgId;

    @Schema(description = "志愿者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21791")
    @ExcelProperty("志愿者ID")
    private Long memberId;

    @Schema(description = "审核状态 0-待审核 1-通过 2-拒绝", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("审核状态 0-待审核 1-通过 2-拒绝")
    private Integer status;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审核时间")
    @ExcelProperty("审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人ID")
    @ExcelProperty("审核人ID")
    private Long auditUser;

    @Schema(description = "审核意见", example = "不喜欢")
    @ExcelProperty("审核意见")
    private String auditReason;

    private String name; // 志愿者姓名
    private String idCard;         // 志愿者身份证号

}
