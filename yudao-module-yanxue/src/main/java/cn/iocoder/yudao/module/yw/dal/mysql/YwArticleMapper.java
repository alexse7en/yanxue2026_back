package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleDO;
import cn.iocoder.yudao.module.yw.vo.YwArticlePageReqLimitVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleUpvoteVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleWithAuthorVO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文章管理 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwArticleMapper extends BaseMapperX<YwArticleDO> {

    default PageResult<YwArticleDO> selectPage(YwArticlePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwArticleDO>()
                .eqIfPresent(YwArticleDO::getCategory, reqVO.getCategory())
                .likeIfPresent(YwArticleDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwArticleDO::getAuthor, reqVO.getAuthor())
                .likeIfPresent(YwArticleDO::getContent, reqVO.getContent())
                .likeIfPresent(YwArticleDO::getIntroduction, reqVO.getIntroduction())
                .eqIfPresent(YwArticleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwArticleDO::getRecommendHot, reqVO.getRecommendHot())
                .eqIfPresent(YwArticleDO::getRecommendBanner, reqVO.getRecommendBanner())
                .eqIfPresent(YwArticleDO::getSpuId, reqVO.getSpuId())
                .orderByDesc(YwArticleDO::getId));
    }

    default List<YwArticleDO> selectList(YwArticlePageReqVO reqVO) {
        return selectList( new LambdaQueryWrapperX<YwArticleDO>()
                .eqIfPresent(YwArticleDO::getCategory, reqVO.getCategory())
                .likeIfPresent(YwArticleDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwArticleDO::getAuthor, reqVO.getAuthor())
                .likeIfPresent(YwArticleDO::getContent, reqVO.getContent())
                .likeIfPresent(YwArticleDO::getIntroduction, reqVO.getIntroduction())
                .eqIfPresent(YwArticleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwArticleDO::getRecommendHot, reqVO.getRecommendHot())
                .eqIfPresent(YwArticleDO::getRecommendBanner, reqVO.getRecommendBanner())
                .eqIfPresent(YwArticleDO::getSpuId, reqVO.getSpuId())
                .orderByDesc(YwArticleDO::getId));
    }

    @Update("update yw_article set browse_count=browse_count+1 where id=#{id}")
    int addReadCount(Long id);

    YwArticleUpvoteVO getArticleWithUpvote(@Param("id") Long id, @Param("memberId") Long memberId);

    @Update("update yw_article set upvote_count=upvote_count+1 where id=#{id}")
    int addUpvoteCount(Long id);

    @Update("update yw_article set upvote_count=upvote_count-1 where id=#{id}")
    int deleteUpvoteCount(Long id);

    List<YwArticleDO> my(Long memberId);
    List<YwArticleWithAuthorVO> getArticleListWithAuthor(@Param("reqVO") YwArticlePageReqLimitVO pageReqVO);
}