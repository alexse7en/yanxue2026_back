package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("yw_orginfo_apply")
@KeySequence("yw_orginfo_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgInfoApplyDO extends BaseDO {

    @TableId
    private Long id;
    private Long orginfoId;
    private Long memberId;
    private Integer applyStatus;
    private String payloadJson;
    private LocalDateTime submitTime;
}
