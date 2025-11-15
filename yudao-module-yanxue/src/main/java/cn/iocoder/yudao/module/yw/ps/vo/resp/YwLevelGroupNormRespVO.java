package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评审细则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwLevelGroupNormRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21649")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述")
    private String introduce;

    @Schema(description = "讲师头像")
    @ExcelProperty("讲师头像")
    private String countRules;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "最高分")
    @ExcelProperty("最高分")
    private Long maxScore;

    @Schema(description = "归属指标", requiredMode = Schema.RequiredMode.REQUIRED, example = "32002")
    @ExcelProperty("归属指标")
    private Long groupId;

    @Schema(description = "输入类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("输入类型")
    private String inputType;

}