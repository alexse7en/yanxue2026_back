package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDO;
import cn.iocoder.yudao.module.yw.ps.vo.YwLevelGroupWithNormVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwLevelNormWithDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 评审种类 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwLevelGroupMapper extends BaseMapperX<YwLevelGroupDO> {

    default PageResult<YwLevelGroupDO> selectPage(YwLevelGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwLevelGroupDO>()
                .likeIfPresent(YwLevelGroupDO::getName, reqVO.getName())
                .eqIfPresent(YwLevelGroupDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(YwLevelGroupDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(YwLevelGroupDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwLevelGroupDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwLevelGroupDO::getMaxScore, reqVO.getMaxScore())
                .orderByDesc(YwLevelGroupDO::getId));
    }
    List<YwLevelGroupWithNormVo> selectAllGroupsWithDetails(@Param("authId") Long authId);
}