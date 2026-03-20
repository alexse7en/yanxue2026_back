package cn.iocoder.yudao.module.yw.vo.portal.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 首页证书查询 Request VO")
@Data
public class YwPortalCertQueryReqVO {

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "身份证后缀")
    private String idCardSuffix;

    @Schema(description = "证书编号")
    private String certNo;
}
