package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("yw_yanxue_orginfo")
@KeySequence("yw_yanxue_orginfo_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgInfoDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long vipinfoId;
    private Long applyId;
    private String certNo;
    private String orgType;
    private Integer status;
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
