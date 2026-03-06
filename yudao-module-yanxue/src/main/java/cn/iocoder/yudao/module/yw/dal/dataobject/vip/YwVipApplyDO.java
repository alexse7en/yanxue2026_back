package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@TableName("yw_yanxue_vip_apply")
@KeySequence("yw_yanxue_vip_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwVipApplyDO extends TenantBaseDO {

    @TableId
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
}
