package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 用户等级新增/修改 Request VO")
@Data
public class YwMemberLevelSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22793")
    private Long id;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "会员号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9886")
    @NotNull(message = "会员号不能为空")
    private Long memberId;

    @Schema(description = "认证等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "26931")
    @NotNull(message = "认证等级不能为空")
    private Long levelId;

}