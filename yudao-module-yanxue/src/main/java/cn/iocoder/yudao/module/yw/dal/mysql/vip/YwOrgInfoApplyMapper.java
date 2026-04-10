package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;

@Mapper
public interface YwOrgInfoApplyMapper extends BaseMapperX<YwOrgInfoApplyDO> {

    default YwOrgInfoApplyDO selectLatestByUserAndOrginfoId(Long userId, Long orginfoId) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoApplyDO>()
                .eq(YwOrgInfoApplyDO::getUserId, userId)
                .eq(YwOrgInfoApplyDO::getOrginfoId, orginfoId)
                .orderByDesc(YwOrgInfoApplyDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwOrgInfoApplyDO> selectPage(YwOrgInfoApplyPageReqVO reqVO) {
        LambdaQueryWrapperX<YwOrgInfoApplyDO> queryWrapper = new LambdaQueryWrapperX<YwOrgInfoApplyDO>()
                .likeIfPresent(YwOrgInfoApplyDO::getUnitName, reqVO.getUnitName())
                .betweenIfPresent(YwOrgInfoApplyDO::getCreateTime,
                        reqVO.getBeginApplyTime() == null ? null : reqVO.getBeginApplyTime().atStartOfDay(),
                        reqVO.getEndApplyTime() == null ? null : reqVO.getEndApplyTime().plusDays(1).atStartOfDay())
                .orderByDesc(YwOrgInfoApplyDO::getId);
        if (reqVO.getAudited() != null) {
            if (reqVO.getAudited().equals(0)) {
                queryWrapper.eq(YwOrgInfoApplyDO::getApplyStatus, 0);
            } else if (reqVO.getAudited().equals(1)) {
                queryWrapper.in(YwOrgInfoApplyDO::getApplyStatus, Arrays.asList(1, 2));
            }
        }
        return selectPage(reqVO, queryWrapper);
    }

    default YwOrgInfoApplyDO selectPendingByUserAndOrginfoId(Long userId, Long orginfoId) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoApplyDO>()
                .eq(YwOrgInfoApplyDO::getUserId, userId)
                .eq(YwOrgInfoApplyDO::getOrginfoId, orginfoId)
                .eq(YwOrgInfoApplyDO::getApplyStatus, 0)
                .orderByDesc(YwOrgInfoApplyDO::getId)
                .last("limit 1"));
    }
}
