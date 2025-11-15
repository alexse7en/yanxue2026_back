package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDetailDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评分项 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwLevelGroupDetailMapper extends BaseMapperX<YwLevelGroupDetailDO> {

    default PageResult<YwLevelGroupDetailDO> selectPage(YwLevelGroupDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwLevelGroupDetailDO>()
                .likeIfPresent(YwLevelGroupDetailDO::getName, reqVO.getName())
                .eqIfPresent(YwLevelGroupDetailDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(YwLevelGroupDetailDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(YwLevelGroupDetailDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwLevelGroupDetailDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwLevelGroupDetailDO::getScore, reqVO.getScore())
                .eqIfPresent(YwLevelGroupDetailDO::getNormId, reqVO.getNormId())
                .eqIfPresent(YwLevelGroupDetailDO::getExcludeLevelId, reqVO.getExcludeLevelId())
                .orderByDesc(YwLevelGroupDetailDO::getId));
    }

}