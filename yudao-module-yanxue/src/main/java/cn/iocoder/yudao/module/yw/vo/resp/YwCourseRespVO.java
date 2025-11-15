package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 课程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwCourseRespVO {

    @Schema(description = "课程id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11157")
    @ExcelProperty("课程id")
    private Long id;

    @Schema(description = "讲师id", example = "12469")
    @ExcelProperty("讲师id")
    private String teacherId;

    @Schema(description = "课程分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18369")
    @ExcelProperty("课程分类id")
    private String category;


    @Schema(description = "课程标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程标题")
    private String title;

    @Schema(description = "课时数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课时数")
    private Integer lessonNum;

    @Schema(description = "课程封面图片url", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程封面图片url")
    private String cover;

    @Schema(description = "浏览量", example = "4802")
    @ExcelProperty("浏览量")
    private Integer viewCount;

    @Schema(description = "课程参与人数")
    @ExcelProperty("课程参与人数")
    private Integer learningNum;

    @Schema(description = "评论数")
    @ExcelProperty("评论数")
    private Integer commentNum;

    @Schema(description = "课程状态，Draft已保存未发布，Provisional未保存临时数据，Normal已发布", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("课程状态，Draft已保存未发布，Provisional未保存临时数据，Normal已发布")
    private String status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "课程简介", requiredMode = Schema.RequiredMode.REQUIRED, example = "随便")
    @ExcelProperty("课程简介")
    private String description;

}