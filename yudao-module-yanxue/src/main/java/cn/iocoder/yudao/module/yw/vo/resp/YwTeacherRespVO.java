package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 教师 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwTeacherRespVO {

    @Schema(description = "讲师id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7615")
    @ExcelProperty("讲师id")
    private String id;

    @Schema(description = "讲师姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("讲师姓名")
    private String name;

    @Schema(description = "讲师简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("讲师简介")
    private String introduce;

    @Schema(description = "讲师头像")
    @ExcelProperty("讲师头像")
    private String avatar;

    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}