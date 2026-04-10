package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalOrgInfoPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper
public interface YwOrgInfoMapper extends BaseMapperX<YwOrgInfoDO> {

    default List<YwOrgInfoDO> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getUserId, userId)
                .orderByDesc(YwOrgInfoDO::getId));
    }

    default YwOrgInfoDO selectByApplyId(Long applyId) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getApplyId, applyId)
                .last("limit 1"));
    }

    default YwOrgInfoDO selectByUserIdAndOrgType(Long userId, String orgType) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getUserId, userId)
                .eqIfPresent(YwOrgInfoDO::getOrgType, orgType)
                .orderByDesc(YwOrgInfoDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwOrgInfoDO> selectPortalPage(YwPortalOrgInfoPageReqVO reqVO) {
        LambdaQueryWrapperX<YwOrgInfoDO> queryWrapper = new LambdaQueryWrapperX<YwOrgInfoDO>()
                .likeIfPresent(YwOrgInfoDO::getUnitName, reqVO.getUnitName())
                .eqIfPresent(YwOrgInfoDO::getOrgType, reqVO.getOrgType())
                .eqIfPresent(YwOrgInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(YwOrgInfoDO::getId);
        queryWrapper.and(StringUtils.hasText(reqVO.getKeyword()),
                wrapper -> wrapper.like(YwOrgInfoDO::getUnitName, reqVO.getKeyword())
                        .or().like(YwOrgInfoDO::getAddress, reqVO.getKeyword())
                        .or().like(YwOrgInfoDO::getDestinationName, reqVO.getKeyword()));
        return selectPage(reqVO, queryWrapper);
    }
}
