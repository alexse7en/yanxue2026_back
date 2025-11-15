package cn.iocoder.yudao.module.system.controller.admin.auth.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - Register Request VO")
@Data
public class AuthRegisterReqVO extends CaptchaVerificationReqVO {

    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 2, max = 30, message = "用户账号长度为 2-30 个字符")
    private String username;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotBlank(message = "联系人姓名不能为空")
    @Size(max = 30, message = "联系人姓名长度不能超过 30 个字符")
    private String nickname;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED, example = "13903006987")
    @NotEmpty(message = "联系人电话不能为空")
    @Length(min = 11, max = 11, message = "")
    private String mobile;

    @Schema(description = "联系人职务", requiredMode = Schema.RequiredMode.REQUIRED, example = "13903006987")
    @NotEmpty(message = "联系人职务不能为空")
    @Length(min = 1, max = 16, message = "")
    private String jobtitle;
}
