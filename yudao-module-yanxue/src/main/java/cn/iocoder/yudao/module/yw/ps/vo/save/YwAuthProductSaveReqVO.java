package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 作品申请新增/修改 Request VO")
@Data
public class YwAuthProductSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15981")
    private Long id;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "分数1")
    private Integer score1;

    @Schema(description = "会员号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19910")
    private Long memberId;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "总得分")
    private Integer realScore;

    @Schema(description = "宣传id", example = "29453")
    private Long articleId;

    @Schema(description = "分数2")
    private Integer score2;

    @Schema(description = "分数3")
    private Integer score3;

    @Schema(description = "分数4")
    private Integer score4;

    @Schema(description = "分数5")
    private Integer score5;

    @Schema(description = "作品路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "作品路径不能为空")
    private String url;

    @Schema(description = "后台照片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "后台照片不能为空")
    private String images;

}