package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelConditionDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDetailDO;
import lombok.Data;

import java.util.List;

/**
 * 评分项 DO
 *
 * @author 芋道源码
 */
@Data

public class YwLevelCondilWithCommentVo extends YwLevelConditionDO {
    private boolean passed; // 是否通过评
    private String detailIsSelected; // 是否通过评
    private String izSelected; // 是否通过评
    private String url; // 是否通过评
    private List<YwAuthCommentDO> comments; // 是否通过评


}