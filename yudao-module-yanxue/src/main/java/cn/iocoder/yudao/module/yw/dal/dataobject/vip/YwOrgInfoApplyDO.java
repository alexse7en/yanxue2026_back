package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("yw_yanxue_orginfo_apply")
@KeySequence("yw_yanxue_orginfo_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgInfoApplyDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long orginfoId;
    private Long userId;
    private Integer applyStatus;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private String unitName;
    private String destinationName;
    private String baseTheme;
    private String unitType;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private Integer fulltimeTutorCount;
    private Integer certFulltimeTutorCount;
    private Integer parttimeTutorCount;
}
