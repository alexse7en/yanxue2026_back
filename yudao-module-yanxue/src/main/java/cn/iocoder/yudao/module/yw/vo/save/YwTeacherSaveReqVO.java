package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 教师新增/修改 Request VO")
@Data
public class YwTeacherSaveReqVO {

    @Schema(description = "讲师id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7615")
    private String id;

    @Schema(description = "讲师姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "讲师姓名不能为空")
    private String name;

    @Schema(description = "讲师简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "讲师简介不能为空")
    private String introduce;

    @Schema(description = "讲师头像")
    private String avatar;

}