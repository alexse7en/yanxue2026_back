package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentStatusDO;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentStatusPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 专家评审状态 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwAuthCommentStatusMapper extends BaseMapperX<YwAuthCommentStatusDO> {

    default PageResult<YwAuthCommentStatusDO> selectPage(YwAuthCommentStatusPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwAuthCommentStatusDO>()
                .likeIfPresent(YwAuthCommentStatusDO::getName, reqVO.getName())
                .eqIfPresent(YwAuthCommentStatusDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwAuthCommentStatusDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwAuthCommentStatusDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwAuthCommentStatusDO::getAuthId, reqVO.getAuthId())
                .eqIfPresent(YwAuthCommentStatusDO::getTeacherId, reqVO.getTeacherId())
                .orderByDesc(YwAuthCommentStatusDO::getId));
    }

    int insertTeacherCommentStatus(@Param("authId") Long authId, @Param("teacherId") Long teacherId);
    int izAllCondiCommented(@Param("authId") Long authId, @Param("teacherId") Long teacherId);
    int izAllDetailCommented(@Param("authId") Long authId, @Param("teacherId") Long teacherId);
    int izAuthMemberCommented(@Param("authId") Long authId);
}