package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 课程分类新增/修改 Request VO")
@Data
public class YwCourseCategorySaveReqVO {

    @Schema(description = "课程分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11721")
    private String id;

    @Schema(description = "课程分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "课程分类名称不能为空")
    private String categoryName;

    @Schema(description = "课程分类父类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "319")
    @NotEmpty(message = "课程分类父类id不能为空")
    private String parentId;

}