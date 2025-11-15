package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评分项 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwLevelGroupDetailRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3420")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述")
    private String introduce;

    @Schema(description = "讲师头像")
    @ExcelProperty("讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "得分", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("得分")
    private Long score;

    @Schema(description = "归属指标", requiredMode = Schema.RequiredMode.REQUIRED, example = "146")
    @ExcelProperty("归属指标")
    private Long normId;

    @Schema(description = "破格条件", example = "15917")
    @ExcelProperty("破格条件")
    private Long excludeLevelId;

}