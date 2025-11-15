package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 考卷设计 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwExamRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18335")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述")
    private String content;

    @Schema(description = "课程id", example = "27486")
    @ExcelProperty("课程id")
    private Long courseId;

    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否限时")
    @ExcelProperty("是否限时")
    private String timeLimit;

    @Schema(description = "考试时间")
    @ExcelProperty("考试时间")
    private Integer totalTime;

    @Schema(description = "总分值")
    @ExcelProperty("总分值")
    private Integer 
totalScore;

    @Schema(description = "及格分值")
    @ExcelProperty("及格分值")
    private Integer qualifyScore;

}