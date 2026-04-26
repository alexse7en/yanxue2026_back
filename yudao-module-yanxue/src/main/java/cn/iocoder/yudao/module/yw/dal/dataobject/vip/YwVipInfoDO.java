package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@TableName("yw_yanxue_vipinfo")
@KeySequence("yw_yanxue_vipinfo_seq")
@Data
//@EqualsAndHashCode(callSuper = true)
public class YwVipInfoDO  {

    @TableId
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
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private java.math.BigDecimal tokenBalance;
    private Integer lastTokenRefreshYear;
    private Integer status;
    private String honor;
    private Integer star;
    private String gallery;

}
