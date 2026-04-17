package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 会员展示信息编辑申请 Response VO")
@Data
public class YwVipInfoApplyRespVO {

    private Long id;
    private Long vipinfoId;
    private Long userId;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String website;
    private LocalDate establishedDate;
    private String businessScope;
    private String companyIntro;
    private String logo;
    private String companyType;
    private String memberNo;
    private String memberLevel;
    private String contactName;
    private String contactPhone;
    private String repName;
    private String repPosition;
    private String repPhone;
    private String repEmail;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String mapAddress;
    private Integer star;
    private String honor;
    private Integer applyStatus;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private String gallery;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
