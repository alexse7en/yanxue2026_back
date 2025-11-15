package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 试题选项新增/修改 Request VO")
@Data
public class YwQuOptionSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30579")
    private Long id;

    @Schema(description = "试题id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17703")
    @NotNull(message = "试题id不能为空")
    private Long quId;

    

    @Schema(description = "是否正确")
    private String izRight;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "题干不能为空")
    private String content;

    @Schema(description = "解析", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "解析不能为空")
    private String analysis;
    @Schema(description = "选项", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "选项不能为空")
    private String abcd;

}