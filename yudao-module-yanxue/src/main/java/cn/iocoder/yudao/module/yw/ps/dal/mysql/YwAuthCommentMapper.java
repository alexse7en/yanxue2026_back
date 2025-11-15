package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentDO;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评审结果 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwAuthCommentMapper extends BaseMapperX<YwAuthCommentDO> {

    default PageResult<YwAuthCommentDO> selectPage(YwAuthCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwAuthCommentDO>()
                .likeIfPresent(YwAuthCommentDO::getName, reqVO.getName())
                .eqIfPresent(YwAuthCommentDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwAuthCommentDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwAuthCommentDO::getScore, reqVO.getScore())
                .eqIfPresent(YwAuthCommentDO::getCommentType, reqVO.getCommentType())
                .eqIfPresent(YwAuthCommentDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwAuthCommentDO::getAuthId, reqVO.getAuthId())
                .eqIfPresent(YwAuthCommentDO::getDetailId, reqVO.getDetailId())
                .eqIfPresent(YwAuthCommentDO::getTeacherId, reqVO.getTeacherId())
                .eqIfPresent(YwAuthCommentDO::getBz, reqVO.getBz())
                .orderByDesc(YwAuthCommentDO::getId));
    }

}