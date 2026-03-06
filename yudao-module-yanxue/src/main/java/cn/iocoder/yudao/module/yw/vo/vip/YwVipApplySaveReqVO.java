package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import java.time.LocalDate;

@Data
public class YwVipApplySaveReqVO {
    private Long id;
    private String filePath;
    private String fileType;
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
}
