package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 评审种类 DO
 *
 * @author 芋道源码
 */

@Data

public class YwLevelGroupWithNormVo extends YwLevelGroupDO {
    List<YwLevelNormWithDetailVo> list=new ArrayList<>();
    Long score;

}