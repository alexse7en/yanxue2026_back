package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学生证书正式表 DO
 */
@TableName("yw_yanxue_cert_student")
@KeySequence("yw_yanxue_cert_student_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwCertStudentDO extends BaseDO {

    @TableId
    private Long id;
    private Long applyDetailId;
    private Long userId;
    private Long vipinfoId;
    private Integer certYear;
    private String studentName;
    private String idCard;
    private String certNo;
    private String schoolName;
    private String className;
    private String courseName;
    private String courseHours;
    private String courseProvider;
    private LocalDate certDate;
    private LocalDate courseDate;
    private LocalDate stampDate;
    private String stampUnit;
    private String certImageUrl;
    private java.time.LocalDateTime issueTime;
}
