package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("yw_yanxue_org_apply")
@KeySequence("yw_yanxue_org_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgApplyRecordDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long vipinfoId;
    private String applyType;
    private Integer applyStatus;
    private String applyNo;
    private String filePath;
    private String fileType;
    private Integer parseStatus;
    private String parseError;
    private String unitName;
    private String destinationName;
    private String baseTheme;
    private String unitType;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
}
