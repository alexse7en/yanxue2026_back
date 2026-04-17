package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class YwVipInfoApplySaveReqVO {
    private Long id;
    private Long vipinfoId;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String website;
    private LocalDate establishedDate;
    private String businessScope;
    private String companyIntro;
    private String logo;
    private String companyType;
    private String contactName;
    private String contactPhone;
    private String repName;
    private String repPosition;
    private String repPhone;
    private String repEmail;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String mapAddress;
    private String gallery;

}
