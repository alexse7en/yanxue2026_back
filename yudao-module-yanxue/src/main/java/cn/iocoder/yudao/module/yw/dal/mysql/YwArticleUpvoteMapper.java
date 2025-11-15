package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleUpvoteDO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticleUpvotePageReqVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文章管理 Mapper
 *
 * @author 科协超级管理员
 */
@Mapper
public interface YwArticleUpvoteMapper extends BaseMapperX<YwArticleUpvoteDO> {

    default PageResult<YwArticleUpvoteDO> selectPage(YwArticleUpvotePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwArticleUpvoteDO>()
                .eqIfPresent(YwArticleUpvoteDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwArticleUpvoteDO::getArticleId, reqVO.getArticleId())
                .eqIfPresent(YwArticleUpvoteDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwArticleUpvoteDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(YwArticleUpvoteDO::getId));
    }

    @Delete("delete from yw_article_upvote where member_id=#{memberId} and article_id=#{articleId}")
    int downvote(@Param("memberId") Long memberId, @Param("articleId")Long articleId);
}