package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 文章管理新增/修改 Request VO")
@Data
public class YwArticleUpvoteSaveReqVO {

    @Schema(description = "文章管理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32637")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "515")
    @NotNull(message = "用户id不能为空")
    private Long memberId;

    @Schema(description = "文章id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2743")
    @NotNull(message = "文章id不能为空")
    private Long articleId;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Integer delFlag;

}