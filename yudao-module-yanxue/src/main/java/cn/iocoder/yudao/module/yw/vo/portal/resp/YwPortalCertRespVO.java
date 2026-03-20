package cn.iocoder.yudao.module.yw.vo.portal.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 首页证书查询 Response VO")
@Data
public class YwPortalCertRespVO {

    private Long id;
    private String certNo;
    private String certName;
    private String userName;
    private String idCard;

    @Schema(description = "证书图片地址，待数据库真实列确认")
    private String certImageUrl;

    private LocalDateTime issueDate;
}
