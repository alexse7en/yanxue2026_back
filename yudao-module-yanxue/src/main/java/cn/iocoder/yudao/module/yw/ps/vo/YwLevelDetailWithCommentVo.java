package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDetailDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * 评分项 DO
 *
 * @author 芋道源码
 */
@Data

public class YwLevelDetailWithCommentVo extends YwLevelGroupDetailDO {
    private boolean passed; // 是否通过评
    private String detailIsSelected; // 是否通过评
    private String detailUrls;
    private Long detailScore;
    private List<YwAuthCommentDO> comments; // 是否通过评


}