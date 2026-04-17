package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("yw_yanxue_vipinfo_apply")
@KeySequence("yw_yanxue_vipinfo_apply_seq")
@Data
// 不继承租户 / BaseDO：该结构由用户手动调整，勿改继承关系
public class YwVipInfoApplyDO {

    @TableId
    private Long id;
    private Long vipinfoId;
    private Long userId;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String website;
    private java.time.LocalDate establishedDate;
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
    private Integer applyStatus;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private String gallery;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
