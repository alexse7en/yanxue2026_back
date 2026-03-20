package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 会员申请审核 Response VO")
@Data
public class YwVipApplyAuditRespVO {

    private Long id;
    private Long userId;
    private Integer applyStatus;
    private String filePath;
    private String fileType;
    private Integer parseStatus;
    private String parseError;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String website;
    private LocalDate establishedDate;
    private String businessScope;
    private String companyIntro;
    private String repName;
    private String repPolitical;
    private String repGender;
    private String repEducation;
    private String repPhone;
    private String repPosition;
    private String repEmail;
    private String repIdcard;
    private String contactName;
    private String contactPhone;
    private String companyType;
    private String applyLevel;
    private LocalDate applyDate;
    private String memberNo;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private LocalDateTime createTime;
}
