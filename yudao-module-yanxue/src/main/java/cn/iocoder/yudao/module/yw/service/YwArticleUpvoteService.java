package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleUpvoteDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwArticleUpvotePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleUpvoteSaveReqVO;

/**
 * 文章管理 Service 接口
 *
 * @author 科协超级管理员
 */
public interface YwArticleUpvoteService {

    /**
     * 创建文章管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwArticleUpvote(@Valid YwArticleUpvoteSaveReqVO createReqVO);

    /**
     * 更新文章管理
     *
     * @param updateReqVO 更新信息
     */
    void updateYwArticleUpvote(@Valid YwArticleUpvoteSaveReqVO updateReqVO);

    /**
     * 删除文章管理
     *
     * @param id 编号
     */
    void deleteYwArticleUpvote(Long id);

    /**
    * 批量删除文章管理
    *
    * @param ids 编号
    */
    void deleteYwArticleUpvoteListByIds(List<Long> ids);

    /**
     * 获得文章管理
     *
     * @param id 编号
     * @return 文章管理
     */
    YwArticleUpvoteDO getYwArticleUpvote(Long id);

    /**
     * 获得文章管理分页
     *
     * @param pageReqVO 分页查询
     * @return 文章管理分页
     */
    PageResult<YwArticleUpvoteDO> getYwArticleUpvotePage(YwArticleUpvotePageReqVO pageReqVO);

    Long createYwArticle(Long memberId, Long articleId);
    int downvote(Long memberId, Long articleId);
}