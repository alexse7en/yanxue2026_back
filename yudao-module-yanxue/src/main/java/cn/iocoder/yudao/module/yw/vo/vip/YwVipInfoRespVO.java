package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 会员展示信息 Response VO")
@Data
public class YwVipInfoRespVO {

    private Long id;
    private Long userId;
    private Long applyId;
    private String memberNo;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String website;
    private LocalDate establishedDate;
    private String businessScope;
    private String companyIntro;
    private String logo;
    private String companyType;
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
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private Integer status;
    private String honor;
    private Integer star;
    private String gallery;
    private BigDecimal tokenBalance;
}
