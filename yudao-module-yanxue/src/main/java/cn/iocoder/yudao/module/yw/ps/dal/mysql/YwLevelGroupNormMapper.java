package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupNormPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupNormDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评审细则 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwLevelGroupNormMapper extends BaseMapperX<YwLevelGroupNormDO> {

    default PageResult<YwLevelGroupNormDO> selectPage(YwLevelGroupNormPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwLevelGroupNormDO>()
                .likeIfPresent(YwLevelGroupNormDO::getName, reqVO.getName())
                .eqIfPresent(YwLevelGroupNormDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(YwLevelGroupNormDO::getCountRules, reqVO.getCountRules())
                .eqIfPresent(YwLevelGroupNormDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwLevelGroupNormDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwLevelGroupNormDO::getMaxScore, reqVO.getMaxScore())
                .eqIfPresent(YwLevelGroupNormDO::getGroupId, reqVO.getGroupId())
                .eqIfPresent(YwLevelGroupNormDO::getInputType, reqVO.getInputType())
                .orderByDesc(YwLevelGroupNormDO::getId));
    }

}