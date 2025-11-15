package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 考卷设计分页 Request VO")
@Data
public class YwExamPageReqVO extends PageParam {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "描述")
    private String content;

    @Schema(description = "课程id", example = "27486")
    private Long courseId;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "是否限时")
    private String timeLimit;

    @Schema(description = "考试时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] totalTime;

    @Schema(description = "总分值")
    private Integer 
totalScore;

    @Schema(description = "及格分值")
    private Integer qualifyScore;

}