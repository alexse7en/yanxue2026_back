package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学生证书申请明细 DO
 */
@TableName("yw_yanxue_student_apply")
@KeySequence("yw_yanxue_student_apply_seq")
@Data

public class YwStudentApplyDO   {

    @TableId
    private Long id;
    private Long applyBatchId;
    private Long userId;
    private Long vipinfoId;
    private String studentName;
    private String idCard;
    private String schoolName;
    private String className;
    private String courseName;
    private String courseHours;
    private String courseProvider;
    private LocalDate certDate;
    private String stampUnit;
}
