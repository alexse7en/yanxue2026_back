package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 志愿者评审条件新增/修改 Request VO")
@Data
public class YwAuthConditionSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26677")
    private Long id;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "会员号", example = "6701")
    private Long memberId;

    @Schema(description = "认证等级", example = "4955")
    private Long levelId;

    @Schema(description = "图片路径")
    private String urls;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "认证id", example = "32717")
    private Long authId;

}