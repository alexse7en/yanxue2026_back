package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthConditionDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 志愿者评审条件 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwAuthConditionMapper extends BaseMapperX<YwAuthConditionDO> {

    default PageResult<YwAuthConditionDO> selectPage(YwAuthConditionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwAuthConditionDO>()
                .likeIfPresent(YwAuthConditionDO::getName, reqVO.getName())
                .eqIfPresent(YwAuthConditionDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwAuthConditionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwAuthConditionDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwAuthConditionDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(YwAuthConditionDO::getUrls, reqVO.getUrls())
                .eqIfPresent(YwAuthConditionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwAuthConditionDO::getAuthId, reqVO.getAuthId())
                .orderByDesc(YwAuthConditionDO::getId));
    }

}