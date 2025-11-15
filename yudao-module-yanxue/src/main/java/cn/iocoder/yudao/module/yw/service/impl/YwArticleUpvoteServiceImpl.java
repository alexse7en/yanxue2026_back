package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwArticleUpvoteService;
import cn.iocoder.yudao.module.yw.vo.page.YwArticleUpvotePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleUpvoteSaveReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleUpvoteDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwArticleUpvoteMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ARTICLE_UPVOTE_NOT_EXISTS;

/**
 * 文章管理 Service 实现类
 *
 * @author 科协超级管理员
 */
@Service
@Validated
public class YwArticleUpvoteServiceImpl implements YwArticleUpvoteService {

    @Resource
    private YwArticleUpvoteMapper ywArticleUpvoteMapper;

    @Override
    public Long createYwArticleUpvote(YwArticleUpvoteSaveReqVO createReqVO) {
        // 插入
        YwArticleUpvoteDO ywArticleUpvote = BeanUtils.toBean(createReqVO, YwArticleUpvoteDO.class);
        ywArticleUpvoteMapper.insert(ywArticleUpvote);

        // 返回
        return ywArticleUpvote.getId();
    }

    @Override
    public void updateYwArticleUpvote(YwArticleUpvoteSaveReqVO updateReqVO) {
        // 校验存在
        validateYwArticleUpvoteExists(updateReqVO.getId());
        // 更新
        YwArticleUpvoteDO updateObj = BeanUtils.toBean(updateReqVO, YwArticleUpvoteDO.class);
        ywArticleUpvoteMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwArticleUpvote(Long id) {
        // 校验存在
        validateYwArticleUpvoteExists(id);
        // 删除
        ywArticleUpvoteMapper.deleteById(id);
    }

    @Override
        public void deleteYwArticleUpvoteListByIds(List<Long> ids) {
        // 删除
        ywArticleUpvoteMapper.deleteByIds(ids);
        }


    private void validateYwArticleUpvoteExists(Long id) {
        if (ywArticleUpvoteMapper.selectById(id) == null) {
            throw exception(YW_ARTICLE_UPVOTE_NOT_EXISTS);
        }
    }

    @Override
    public YwArticleUpvoteDO getYwArticleUpvote(Long id) {
        return ywArticleUpvoteMapper.selectById(id);
    }

    @Override
    public PageResult<YwArticleUpvoteDO> getYwArticleUpvotePage(YwArticleUpvotePageReqVO pageReqVO) {
        return ywArticleUpvoteMapper.selectPage(pageReqVO);
    }

    @Override
    public Long createYwArticle(Long memberId, Long articleId) {
        LambdaQueryWrapper<YwArticleUpvoteDO> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YwArticleUpvoteDO::getArticleId,articleId).eq(YwArticleUpvoteDO::getMemberId,memberId);
        List<YwArticleUpvoteDO> list=ywArticleUpvoteMapper.selectList(lambdaQueryWrapper);
        if(list==null || list.size()==0){
            YwArticleUpvoteDO ywArticleUpvote = new YwArticleUpvoteDO();
            ywArticleUpvote.setMemberId(memberId);
            ywArticleUpvote.setArticleId(articleId);
            ywArticleUpvoteMapper.insert(ywArticleUpvote);

            // 返回
            return ywArticleUpvote.getId();
        }else{
            return list.get(0).getId();
        }
        // 插入

    }
    @Override
    public int downvote(Long memberId, Long articleId){
        return ywArticleUpvoteMapper.downvote(memberId,articleId);
    }

}