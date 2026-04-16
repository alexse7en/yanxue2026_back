package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 学生证书申请批次 DO
 */
@TableName("yw_yanxue_student_apply_batch")
@KeySequence("yw_yanxue_student_apply_batch_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwStudentApplyBatchDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long vipinfoId;
    private String applyNo;
    /**
     * 申请状态：0-草稿 1-待审核 2-审核通过 3-审核拒绝
     */
    private Integer applyStatus;
    private String uploadFilePath;
    private String fileType;
    /**
     * 解析状态：0-未解析 1-解析成功 2-解析失败
     */
    private Integer parseStatus;
    private String parseError;
    private Integer parseCount;
    private String downloadUrl;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
}
