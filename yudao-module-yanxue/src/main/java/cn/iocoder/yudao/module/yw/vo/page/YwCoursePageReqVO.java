package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 课程分页 Request VO")
@Data
public class YwCoursePageReqVO extends PageParam {

    @Schema(description = "课程id", example = "11157")
    private Long id;

    @Schema(description = "讲师id", example = "12469")
    private String teacherId;

    @Schema(description = "课程分类id", example = "18369")
    private String category;


    @Schema(description = "课程标题")
    private String title;

    @Schema(description = "课程状态，Draft已保存未发布，Provisional未保存临时数据，Normal已发布", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "课程简介", example = "随便")
    private String description;

}