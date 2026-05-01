package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwYanxueArticleDO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticlePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.util.StringUtils;

@Mapper
public interface YwYanxueArticleMapper extends BaseMapperX<YwYanxueArticleDO> {

    default PageResult<YwYanxueArticleDO> selectPage(YwYanxueArticlePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwYanxueArticleDO>()
                .eqIfPresent(YwYanxueArticleDO::getCategory, reqVO.getCategory())
                .likeIfPresent(YwYanxueArticleDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwYanxueArticleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwYanxueArticleDO::getIsTop, reqVO.getIsTop())
                .likeIfPresent(YwYanxueArticleDO::getAuthor, reqVO.getAuthor())
                .betweenIfPresent(YwYanxueArticleDO::getPublishTime, reqVO.getPublishTime())
                .orderByDesc(YwYanxueArticleDO::getIsTop)
                .orderByAsc(YwYanxueArticleDO::getSortOrder)
                .orderByDesc(YwYanxueArticleDO::getPublishTime)
                .orderByDesc(YwYanxueArticleDO::getId));
    }

    default PageResult<YwYanxueArticleDO> selectPortalPage(YwPortalArticlePageReqVO reqVO) {
        LambdaQueryWrapperX<YwYanxueArticleDO> queryWrapper = new LambdaQueryWrapperX<YwYanxueArticleDO>()
                .eqIfPresent(YwYanxueArticleDO::getCategory, reqVO.getCategory())
                .likeIfPresent(YwYanxueArticleDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwYanxueArticleDO::getStatus, reqVO.getStatus())
                .orderByDesc(YwYanxueArticleDO::getIsTop)
                .orderByAsc(YwYanxueArticleDO::getSortOrder)
                .orderByDesc(YwYanxueArticleDO::getPublishTime)
                .orderByDesc(YwYanxueArticleDO::getId);
        queryWrapper.and(StringUtils.hasText(reqVO.getKeyword()),
                wrapper -> wrapper.like(YwYanxueArticleDO::getTitle, reqVO.getKeyword())
                        .or().like(YwYanxueArticleDO::getSummary, reqVO.getKeyword())
                        .or().like(YwYanxueArticleDO::getContent, reqVO.getKeyword()));
        return selectPage(reqVO, queryWrapper);
    }

    @Update("UPDATE yw_yanxue_article SET view_count = view_count + 1 WHERE id = #{id}")
    int addViewCount(Long id);
}
