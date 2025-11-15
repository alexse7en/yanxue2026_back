package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleDO;
import cn.iocoder.yudao.module.yw.dal.mysql.YwArticleMapper;
import cn.iocoder.yudao.module.yw.service.YwArticleService;
import cn.iocoder.yudao.module.yw.service.YwArticleUpvoteService;
import cn.iocoder.yudao.module.yw.vo.YwArticlePageReqLimitVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleUpvoteVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleWithAuthorVO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;


import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.member.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 文章管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwArticleServiceImpl implements YwArticleService {

    @Resource
    private YwArticleMapper articleMapper;
    @Resource
    private YwArticleUpvoteService ywArticleUpvoteService;
    @Override
    public Long createArticle(YwArticleSaveReqVO createReqVO) {
        // 插入
        YwArticleDO article = BeanUtils.toBean(createReqVO, YwArticleDO.class);
        articleMapper.insert(article);

        // 返回
        return article.getId();
    }

    @Override
    public void updateArticle(YwArticleSaveReqVO updateReqVO) {
        // 校验存在
        validateArticleExists(updateReqVO.getId());
        // 更新
        YwArticleDO updateObj = BeanUtils.toBean(updateReqVO, YwArticleDO.class);
        articleMapper.updateById(updateObj);
    }

    @Override
    public void deleteArticle(Long id) {
        // 校验存在
        validateArticleExists(id);
        // 删除
        articleMapper.deleteById(id);
    }

    @Override
        public void deleteArticleListByIds(List<Long> ids) {
        // 删除
        articleMapper.deleteByIds(ids);
        }


    private void validateArticleExists(Long id) {
        if (articleMapper.selectById(id) == null) {
            throw exception(ARTICLE_NOT_EXISTS);
        }
    }

    @Override
    public YwArticleDO getArticle(Long id) {
        return articleMapper.selectById(id);
    }

    public YwArticleUpvoteVO getArticleWithUpvote(Long id){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        return articleMapper.getArticleWithUpvote(id,memberId);
    }

    @Override
    public int addReadCount(Long id) {
        return articleMapper.addReadCount(id);
    }

    @Override
    public PageResult<YwArticleDO> getArticlePage(YwArticlePageReqVO pageReqVO) {
        return articleMapper.selectPage(pageReqVO);
    }
    @Override
    public List<YwArticleDO> selectList(YwArticlePageReqLimitVO reqVO) {
        LambdaQueryWrapperX<YwArticleDO> queryWrapper = new LambdaQueryWrapperX<YwArticleDO>()
                .eqIfPresent(YwArticleDO::getCategory, reqVO.getCategory())
                .likeIfPresent(YwArticleDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwArticleDO::getAuthor, reqVO.getAuthor())
                .likeIfPresent(YwArticleDO::getContent, reqVO.getContent())
                .likeIfPresent(YwArticleDO::getIntroduction, reqVO.getIntroduction())
                .eqIfPresent(YwArticleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwArticleDO::getRecommendHot, reqVO.getRecommendHot())
                .eqIfPresent(YwArticleDO::getRecommendBanner, reqVO.getRecommendBanner())
                .eqIfPresent(YwArticleDO::getSpuId, reqVO.getSpuId())
                .eqIfPresent(YwArticleDO::getClassification, reqVO.getClassification())
                .orderByAsc(YwArticleDO::getSort);  // 改为按序号正序
        if (reqVO.getLimit() != null && reqVO.getLimit() > 0) {
            queryWrapper.last("LIMIT " + reqVO.getLimit());
        }
        return articleMapper.selectList(queryWrapper);
    }
    @Override
    public Boolean upvote(Long id){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        if (memberId==null ) {
            throw exception(USER_NOT_EXISTS);
        }
        Long result=ywArticleUpvoteService.createYwArticle(memberId,id);
        if(result>0){
            articleMapper.addUpvoteCount(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean downvote(Long id){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        if (memberId==null ) {
            throw exception(USER_NOT_EXISTS);
        }
        int result=ywArticleUpvoteService.downvote(memberId,id);
        if(result>0){
            articleMapper.addUpvoteCount(id);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public List<YwArticleDO> my(){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        return articleMapper.my(memberId);
    }

    @Override
    public List<YwArticleWithAuthorVO> getArticleListWithAuthor(YwArticlePageReqLimitVO pageReqVO){
        return articleMapper.getArticleListWithAuthor(pageReqVO);
    }

}
