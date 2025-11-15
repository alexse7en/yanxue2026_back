package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 专家评审状态新增/修改 Request VO")
@Data
public class YwAuthCommentStatusSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14713")
    private Long id;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "认证id", example = "18228")
    private Long authId;

    @Schema(description = "条件id", example = "6461")
    private Long teacherId;

}