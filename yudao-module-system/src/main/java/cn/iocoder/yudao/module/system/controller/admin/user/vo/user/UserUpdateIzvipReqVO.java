package cn.iocoder.yudao.module.system.controller.admin.user.vo.user;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserUpdateIzvipReqVO {
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Schema(description = "VIP：0-否，1-是", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "VIP 状态不能为空")
    @Min(value = 0, message = "VIP 状态非法")
    private Integer izvip; // 0/1
}
