package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 评分项新增/修改 Request VO")
@Data
public class YwLevelGroupDetailSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3420")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "描述不能为空")
    private String introduce;

    @Schema(description = "讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "得分", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "得分不能为空")
    private Long score;

    @Schema(description = "归属指标", requiredMode = Schema.RequiredMode.REQUIRED, example = "146")
    @NotNull(message = "归属指标不能为空")
    private Long normId;

    @Schema(description = "破格条件", example = "15917")
    private Long excludeLevelId;

}