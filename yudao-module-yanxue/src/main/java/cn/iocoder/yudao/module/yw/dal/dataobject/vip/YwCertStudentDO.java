package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学生证书明细 DO
 *
 * 对应用户提供表 yw_yanxue_cert_student
 */
@TableName("yw_yanxue_cert_student")
@KeySequence("yw_yanxue_cert_student_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwCertStudentDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long vipinfoId;
    private String studentName;
    private String idCard;
    private String certNo;
    private String schoolName;
    private String className;
    private String courseName;
    private String courseHours;
    private String courseProvider;
    private LocalDate certDate;
    private String stampUnit;
    private String certImageUrl;
    private String uploadFilePath;
    /**
     * 状态：0-待处理 1-处理成功 2-处理失败
     */
    private Integer status;
    private String errorMsg;
}
