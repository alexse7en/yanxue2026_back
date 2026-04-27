package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 会员数据中心保存 Request VO")
@Data
public class YwVipInfoSaveReqVO {

    @NotNull(message = "会员信息 ID 不能为空")
    private Long id;

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
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private String honor;
    private Integer star;
    private String gallery;
    private BigDecimal tokenBalance;
}
