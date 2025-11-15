package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthLevelDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 评审申请 DO
 *
 * @author 芋道源码
 */
@Data
public class YwAuthLevelMemberVo extends YwAuthLevelDO {
    List<YwAuthConditionMemberVo> condiList =new ArrayList<>();
    List<YwAuthDetailMemberVo> detailList =new ArrayList<>();
    Long teacherId;
    String teacherStatus;

}