package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 评审申请新增/修改 Request VO")
@Data
public class YwAuthLevelSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21762")
    private Long id;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "分数")
    private Integer score;

    @Schema(description = "会员号", example = "27537")
    private Long memberId;

    @Schema(description = "认证等级", example = "26559")
    private Long levelId;

    @Schema(description = "审核导师")
    private String teacher;

    @Schema(description = "状态", example = "1")
    private String status;

}