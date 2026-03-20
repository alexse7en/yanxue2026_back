package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalVipInfoPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.StringUtils;

@Mapper
public interface YwVipInfoMapper extends BaseMapperX<YwVipInfoDO> {

    default YwVipInfoDO selectByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoDO>()
                .eq(YwVipInfoDO::getUserId, userId)
                .orderByDesc(YwVipInfoDO::getId)
                .last("limit 1"));
    }

    default YwVipInfoDO selectByMemberNo(String memberNo) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoDO>()
                .eq(YwVipInfoDO::getMemberNo, memberNo)
                .orderByDesc(YwVipInfoDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwVipInfoDO> selectPortalPage(YwPortalVipInfoPageReqVO reqVO) {
        LambdaQueryWrapperX<YwVipInfoDO> queryWrapper = new LambdaQueryWrapperX<YwVipInfoDO>()
                .likeIfPresent(YwVipInfoDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(YwVipInfoDO::getMemberLevel, reqVO.getMemberLevel())
                .eqIfPresent(YwVipInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(YwVipInfoDO::getId);
        queryWrapper.and(StringUtils.hasText(reqVO.getKeyword()),
                wrapper -> wrapper.like(YwVipInfoDO::getCompanyName, reqVO.getKeyword())
                        .or().like(YwVipInfoDO::getCompanyAddress, reqVO.getKeyword()));
        return selectPage(reqVO, queryWrapper);
    }
}
