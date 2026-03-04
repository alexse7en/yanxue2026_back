package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("yw_org_apply_record")
@KeySequence("yw_org_apply_record_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgApplyRecordDO extends BaseDO {

    @TableId
    private Long id;
    private Long memberId;
    private String applyType;
    private Integer applyStatus;
    private String payloadJson;
    private LocalDateTime submitTime;
}
