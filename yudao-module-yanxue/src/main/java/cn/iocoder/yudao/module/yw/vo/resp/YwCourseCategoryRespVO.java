package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 课程分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwCourseCategoryRespVO {

    @Schema(description = "课程分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11721")
    @ExcelProperty("课程分类id")
    private String id;

    @Schema(description = "课程分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("课程分类名称")
    private String categoryName;

    @Schema(description = "课程分类父类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "319")
    @ExcelProperty("课程分类父类id")
    private String parentId;

    @Schema(description = "层级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("层级")
    private Integer level;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}