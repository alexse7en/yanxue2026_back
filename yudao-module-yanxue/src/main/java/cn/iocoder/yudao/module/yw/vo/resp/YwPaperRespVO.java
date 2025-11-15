package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 试卷 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwPaperRespVO {


    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17534")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "试卷id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18594")
    @ExcelProperty("试卷id")
    private Long examId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26757")
    @ExcelProperty("用户id")
    private Long memberId;

    @Schema(description = "考试时长")
    @ExcelProperty("考试时长")
    private Integer userTime;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "得分")
    @ExcelProperty("得分")
    private Integer
            userScore;

    @Schema(description = "试卷状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("试卷状态")
    private String status;

    @Schema(description = "是否通过")
    @ExcelProperty("是否通过")
    private String izPass;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述")
    private String content;

    @Schema(description = "课程id", example = "14900")
    @ExcelProperty("课程id")
    private Long courseId;

    @Schema(description = "总分值")
    @ExcelProperty("总分值")
    private Integer totalScore;

    @Schema(description = "及格分值")
    @ExcelProperty("及格分值")
    private Integer qualifyScore;

    @Schema(description = "是否限时")
    @ExcelProperty("是否限时")
    private String timeLimit;

    @Schema(description = "考试时间")
    @ExcelProperty("考试时间")
    private Integer totalTime;

}