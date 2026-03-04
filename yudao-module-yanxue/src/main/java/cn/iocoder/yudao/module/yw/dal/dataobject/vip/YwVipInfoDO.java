package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("yw_yanxue_vipinfo")
@KeySequence("yw_yanxue_vipinfo_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwVipInfoDO extends BaseDO {

    @TableId
    private Long id;
    private Long memberId;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private BigDecimal tokenBalance;
    private String payloadJson;
    private Integer status;
}
