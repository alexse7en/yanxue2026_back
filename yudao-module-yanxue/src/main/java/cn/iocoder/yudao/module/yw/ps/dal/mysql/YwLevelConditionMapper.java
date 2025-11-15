package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.YwLevelCondilWithCommentVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwLevelGroupWithNormVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelConditionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 评审条件 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwLevelConditionMapper extends BaseMapperX<YwLevelConditionDO> {

    default PageResult<YwLevelConditionDO> selectPage(YwLevelConditionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwLevelConditionDO>()
                .likeIfPresent(YwLevelConditionDO::getName, reqVO.getName())
                .eqIfPresent(YwLevelConditionDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(YwLevelConditionDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(YwLevelConditionDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwLevelConditionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwLevelConditionDO::getUrls, reqVO.getUrls())
                .eqIfPresent(YwLevelConditionDO::getIzForce, reqVO.getIzForce())
                .eqIfPresent(YwLevelConditionDO::getInputType, reqVO.getInputType())
                .eqIfPresent(YwLevelConditionDO::getExamId, reqVO.getExamId())
                .eqIfPresent(YwLevelConditionDO::getLevelId, reqVO.getLevelId())
                .orderByDesc(YwLevelConditionDO::getId));
    }
    List<YwLevelCondilWithCommentVo> selectAllCondiWithDetails(@Param("authId") Long authId);
}