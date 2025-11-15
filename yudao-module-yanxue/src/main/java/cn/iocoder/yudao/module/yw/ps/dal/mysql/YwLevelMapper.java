package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 志愿者等级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwLevelMapper extends BaseMapperX<YwLevelDO> {

    default PageResult<YwLevelDO> selectPage(YwLevelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwLevelDO>()
                .likeIfPresent(YwLevelDO::getName, reqVO.getName())
                .eqIfPresent(YwLevelDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(YwLevelDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(YwLevelDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwLevelDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwLevelDO::getScore, reqVO.getScore())
                .orderByDesc(YwLevelDO::getId));
    }

}