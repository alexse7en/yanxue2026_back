package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 用户收件地址新增/修改 Request VO")
@Data
public class YwMemberLevelAddressSaveReqVO {

    @Schema(description = "收件人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "收件人名称不能为空")
    private String name;
    private Long  id;
    private Long  memberId;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @Schema(description = "地区编码")
    private Long areaId;

    @Schema(description = "收件详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "收件详细地址不能为空")
    private String detailAddress;

    @Schema(description = "是否默认")
    private Boolean defaultStatus;

    @Schema(description = "证书编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18647")
    @NotNull(message = "证书编号不能为空")
    private Long levelId;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "快递公司")
    private String company;

    @Schema(description = "快递单号")
    private String deliveryNo;

}