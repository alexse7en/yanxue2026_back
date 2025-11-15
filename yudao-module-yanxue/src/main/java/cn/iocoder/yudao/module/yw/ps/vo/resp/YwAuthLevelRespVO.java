package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评审申请 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwAuthLevelRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21762")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "分数")
    @ExcelProperty("分数")
    private Integer score;

    @Schema(description = "会员号", example = "27537")
    @ExcelProperty("会员号")
    private Long memberId;

    @Schema(description = "认证等级", example = "26559")
    @ExcelProperty("认证等级")
    private Long levelId;

    @Schema(description = "审核导师")
    @ExcelProperty("审核导师")
    private String teacher;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private String status;

    private String memberName;
    private String idCard;
    private String teacherName; // 由 "1,2,250" → "张三,李四,王五"

}
