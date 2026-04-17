package cn.iocoder.yudao.module.yw.vo.portal.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "用户 APP - 首页会员中心 Response VO")
@Data
public class YwPortalVipInfoRespVO {

    private Long id;
    private String memberNo;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String website;
    private String companyIntro;
    private String logo;
    private String memberLevel;
    private String contactName;
    private String contactPhone;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String mapAddress;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
    private Integer status;
    private String businessScope;
    private String unitType;


    @Schema(description = "星级，待数据库真实列确认")
    private Integer star;

    @Schema(description = "荣誉，待数据库真实列确认")
    private String honor;

    @Schema(description = "图集，待数据库真实列确认")
    private String gallery;

    @Schema(description = "是否具备基地资质，待数据库真实列确认")
    private Boolean hasBaseQualification;

    @Schema(description = "是否具备机构资质，待数据库真实列确认")
    private Boolean hasOrgQualification;
}
