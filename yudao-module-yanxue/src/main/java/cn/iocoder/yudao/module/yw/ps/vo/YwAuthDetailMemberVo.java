package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthDetailDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 志愿者等级 DO
 *
 * @author 芋道源码
 */

@Data

public class YwAuthDetailMemberVo extends YwAuthDetailDO {

     Integer score;
     Long excludeLevelId;
     Long groupId;
     Long normId;
     Long detailId;
     String inputType;
     String normName;
     String normIntroduce;
     Integer normScore;
     String groupName;
     String groupIntroduce;
     Integer groupScore;

     String commentStatus;
     String bz;
     Long commentId;

}