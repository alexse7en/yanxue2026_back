package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthConditionDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 志愿者评审条件 DO
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class YwAuthConditionMemberVo extends YwAuthConditionDO {
    String introduce;
    String inputType;
    String izForce;
    String commentStatus;
    Long commentId;
    String bz;

}