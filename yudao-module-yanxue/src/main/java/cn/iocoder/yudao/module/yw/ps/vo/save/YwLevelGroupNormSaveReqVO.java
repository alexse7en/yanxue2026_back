package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 评审细则新增/修改 Request VO")
@Data
public class YwLevelGroupNormSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21649")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "描述不能为空")
    private String introduce;

    @Schema(description = "讲师头像")
    private String countRules;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "最高分")
    private Long maxScore;

    @Schema(description = "归属指标", requiredMode = Schema.RequiredMode.REQUIRED, example = "32002")
    @NotNull(message = "归属指标不能为空")
    private Long groupId;

    @Schema(description = "输入类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "输入类型不能为空")
    private String inputType;

}