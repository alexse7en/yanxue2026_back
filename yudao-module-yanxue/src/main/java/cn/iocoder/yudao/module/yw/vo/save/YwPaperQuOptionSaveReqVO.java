package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 试卷选项新增/修改 Request VO")
@Data
public class YwPaperQuOptionSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24814")
    private Long id;

    @Schema(description = "是否选中", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否选中不能为空")
    private String izAnswered;

    @Schema(description = "标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标签不能为空")
    private String abcd;

    @Schema(description = "讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "选项id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17423")
    @NotNull(message = "选项id不能为空")
    private Long optionId;

    @Schema(description = "考题id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19535")
    @NotNull(message = "考题id不能为空")
    private Long quId;

    @Schema(description = "是否正确", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否正确不能为空")
    private String izRight;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "题干不能为空")
    private String content;

    @Schema(description = "解析")
    private String analysis;

}