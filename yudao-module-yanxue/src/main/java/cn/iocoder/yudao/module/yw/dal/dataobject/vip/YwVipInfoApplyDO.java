package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("yw_yanxue_vipinfo_apply")
@KeySequence("yw_yanxue_vipinfo_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwVipInfoApplyDO extends TenantBaseDO {

    @TableId
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
    private String memberLevel;
    private String contactName;
    private String contactPhone;
    private String repName;
    private String repPosition;
    private String repPhone;
    private String repEmail;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private BigDecimal tokenBalanceChange;
    private Integer applyStatus;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
}
