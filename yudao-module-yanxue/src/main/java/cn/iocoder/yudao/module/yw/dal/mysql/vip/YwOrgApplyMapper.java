package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;

@Mapper
public interface YwOrgApplyMapper extends BaseMapperX<YwOrgApplyDO> {

    default YwOrgApplyDO selectLatestByUserAndType(Long userId, String applyType) {
        return selectOne(new LambdaQueryWrapperX<YwOrgApplyDO>()
                .eq(YwOrgApplyDO::getUserId, userId)
                .eq(YwOrgApplyDO::getApplyType, applyType)
                .orderByDesc(YwOrgApplyDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwOrgApplyDO> selectAuditPage(YwOrgApplyAuditPageReqVO reqVO) {
        LambdaQueryWrapperX<YwOrgApplyDO> queryWrapper = new LambdaQueryWrapperX<YwOrgApplyDO>()
                .eqIfPresent(YwOrgApplyDO::getApplyType, reqVO.getApplyType())
                .likeIfPresent(YwOrgApplyDO::getUnitName, reqVO.getUnitName())
                .eqIfPresent(YwOrgApplyDO::getApplyNo, reqVO.getApplyNo())
                .betweenIfPresent(YwOrgApplyDO::getCreateTime,
                        reqVO.getBeginApplyDate() == null ? null : reqVO.getBeginApplyDate().atStartOfDay(),
                        reqVO.getEndApplyDate() == null ? null : reqVO.getEndApplyDate().plusDays(1).atStartOfDay())
                .orderByDesc(YwOrgApplyDO::getId);
        if (reqVO.getAudited() != null) {
            if (reqVO.getAudited().equals(0)) {
                queryWrapper.eq(YwOrgApplyDO::getApplyStatus, 1);
            } else if (reqVO.getAudited().equals(1)) {
                queryWrapper.in(YwOrgApplyDO::getApplyStatus, Arrays.asList(2, 3));
            }
        }
        return selectPage(reqVO, queryWrapper);
    }

    default YwOrgApplyDO selectLatestPendingByUserAndType(Long userId, String applyType) {
        return selectOne(new LambdaQueryWrapperX<YwOrgApplyDO>()
                .eq(YwOrgApplyDO::getUserId, userId)
                .eq(YwOrgApplyDO::getApplyType, applyType)
                .eq(YwOrgApplyDO::getApplyStatus, 1)
                .orderByDesc(YwOrgApplyDO::getId)
                .last("limit 1"));
    }

    default YwOrgApplyDO selectLatestNotApprovedByUnitName(String unitName) {
        return selectOne(new LambdaQueryWrapperX<YwOrgApplyDO>()
                .eq(YwOrgApplyDO::getUnitName, unitName)
                .ne(YwOrgApplyDO::getApplyStatus, 2)
                .orderByDesc(YwOrgApplyDO::getId)
                .last("limit 1"));
    }
}



