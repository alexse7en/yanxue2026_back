package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 组织-志愿者挂靠审核 DO
 *
 * @author 芋道源码
 */
@TableName("yw_member_apply")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwMemberApplyDO  {

    /**
     * 表ID
     */
    @TableId
    private Long id;
    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 志愿者ID
     */
    private Long memberId;
    /**
     * 审核状态 0-待审核 1-通过 2-拒绝
     */
    private Integer status;
    /**
     * 申请时间
     */
    private LocalDateTime applyTime;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 审核人ID
     */
    private Long auditUser;
    /**
     * 审核意见
     */
    private String auditReason;



}
