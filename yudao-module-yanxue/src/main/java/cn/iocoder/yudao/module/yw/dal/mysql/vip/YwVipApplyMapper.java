package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;

@Mapper
public interface YwVipApplyMapper extends BaseMapperX<YwVipApplyDO> {

    default YwVipApplyDO selectLatestByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwVipApplyDO>()
                .eq(YwVipApplyDO::getUserId, userId)
                .orderByDesc(YwVipApplyDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwVipApplyDO> selectAuditPage(YwVipApplyAuditPageReqVO reqVO) {
        LambdaQueryWrapperX<YwVipApplyDO> queryWrapper = new LambdaQueryWrapperX<YwVipApplyDO>()
                .likeIfPresent(YwVipApplyDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(YwVipApplyDO::getApplyLevel, reqVO.getApplyLevel())
                .eqIfPresent(YwVipApplyDO::getMemberNo, reqVO.getMemberNo())
                .betweenIfPresent(YwVipApplyDO::getApplyDate, reqVO.getBeginApplyDate(), reqVO.getEndApplyDate())
                .orderByDesc(YwVipApplyDO::getId);
        if (reqVO.getAudited() != null) {
            if (reqVO.getAudited().equals(0)) {
                queryWrapper.eq(YwVipApplyDO::getApplyStatus, 1);
            } else if (reqVO.getAudited().equals(1)) {
                queryWrapper.in(YwVipApplyDO::getApplyStatus, Arrays.asList(10, 11));
            }
        }
        return selectPage(reqVO, queryWrapper);
    }
}
