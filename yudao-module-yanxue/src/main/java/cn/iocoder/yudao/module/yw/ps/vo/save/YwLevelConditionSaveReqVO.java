package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 评审条件新增/修改 Request VO")
@Data
public class YwLevelConditionSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15939")
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

    @Schema(description = "证明图片")
    private String urls;

    @Schema(description = "强制填写")
    private String izForce;

    @Schema(description = "勾选类型", example = "1")
    private String inputType;

    @Schema(description = "关联课程", example = "12216")
    private Long examId;

    @Schema(description = "关联课程", requiredMode = Schema.RequiredMode.REQUIRED, example = "8049")
    @NotNull(message = "关联课程不能为空")
    private Long levelId;

}