package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 学生证书生成申请 DO
 *
 * 对应建议新增主表 yw_yanxue_cert_student_apply
 */
@TableName("yw_yanxue_cert_student_apply")
@KeySequence("yw_yanxue_cert_student_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwCertStudentApplyDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long vipinfoId;
    private String applyNo;
    private String filePath;
    private String fileType;
    private Integer parseStatus;
    private String parseError;
    private Integer parseCount;
    private Integer certStatus;
    private String certNo;
    private String certName;
    private String certUrl;
    private String downloadUrl;
    private LocalDateTime finishTime;
}
