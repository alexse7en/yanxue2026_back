package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyBatchDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;

@Mapper
public interface YwStudentApplyBatchMapper extends BaseMapperX<YwStudentApplyBatchDO> {

    default YwStudentApplyBatchDO selectLatestDraftByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwStudentApplyBatchDO>()
                .eq(YwStudentApplyBatchDO::getUserId, userId)
                .eq(YwStudentApplyBatchDO::getApplyStatus, 0)
                .orderByDesc(YwStudentApplyBatchDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwStudentApplyBatchDO> selectPageMy(Long userId, YwCertStudentApplyPageReqVO reqVO) {
        LambdaQueryWrapperX<YwStudentApplyBatchDO> queryWrapper = new LambdaQueryWrapperX<YwStudentApplyBatchDO>()
                .eq(YwStudentApplyBatchDO::getUserId, userId)
                .orderByDesc(YwStudentApplyBatchDO::getId);
        if (reqVO.getKeyword() != null && !reqVO.getKeyword().trim().isEmpty()) {
            queryWrapper.like(YwStudentApplyBatchDO::getApplyNo, reqVO.getKeyword().trim());
        }
        return selectPage(reqVO, queryWrapper);
    }

    default PageResult<YwStudentApplyBatchDO> selectAuditPage(YwCertStudentApplyAuditPageReqVO reqVO) {
        LambdaQueryWrapperX<YwStudentApplyBatchDO> queryWrapper = new LambdaQueryWrapperX<YwStudentApplyBatchDO>()
                .eqIfPresent(YwStudentApplyBatchDO::getApplyStatus, reqVO.getApplyStatus())
                .likeIfPresent(YwStudentApplyBatchDO::getApplyNo, reqVO.getApplyNo())
                .betweenIfPresent(YwStudentApplyBatchDO::getCreateTime,
                        reqVO.getBeginCreateTime() == null ? null : reqVO.getBeginCreateTime().atStartOfDay(),
                        reqVO.getEndCreateTime() == null ? null : reqVO.getEndCreateTime().plusDays(1).atStartOfDay())
                .orderByDesc(YwStudentApplyBatchDO::getId);
        if (reqVO.getAudited() != null) {
            if (reqVO.getAudited().equals(0)) {
                queryWrapper.eq(YwStudentApplyBatchDO::getApplyStatus, 1);
            } else if (reqVO.getAudited().equals(1)) {
                queryWrapper.in(YwStudentApplyBatchDO::getApplyStatus, Arrays.asList(2, 3));
            }
        }
        return selectPage(reqVO, queryWrapper);
    }
}
