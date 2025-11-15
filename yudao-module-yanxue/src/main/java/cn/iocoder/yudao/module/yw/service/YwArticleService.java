package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleDO;
import cn.iocoder.yudao.module.yw.vo.YwArticlePageReqLimitVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleUpvoteVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleWithAuthorVO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleSaveReqVO;

/**
 * 文章管理 Service 接口
 *
 * @author 芋道源码
 */
public interface YwArticleService {

    /**
     * 创建文章管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createArticle(@Valid YwArticleSaveReqVO createReqVO);

    /**
     * 更新文章管理
     *
     * @param updateReqVO 更新信息
     */
    void updateArticle(@Valid YwArticleSaveReqVO updateReqVO);

    /**
     * 删除文章管理
     *
     * @param id 编号
     */
    void deleteArticle(Long id);

    /**
    * 批量删除文章管理
    *
    * @param ids 编号
    */
    void deleteArticleListByIds(List<Long> ids);

    /**
     * 获得文章管理
     *
     * @param id 编号
     * @return 文章管理
     */
    YwArticleDO getArticle(Long id);
    YwArticleUpvoteVO getArticleWithUpvote(Long id);
    int addReadCount(Long id);

    /**
     * 获得文章管理分页
     *
     * @param pageReqVO 分页查询
     * @return 文章管理分页
     */
    PageResult<YwArticleDO> getArticlePage(YwArticlePageReqVO pageReqVO);
    List<YwArticleDO> selectList(YwArticlePageReqLimitVO pageReqVO);

    public Boolean upvote(Long id);
    public Boolean downvote(Long id);
    public List<YwArticleDO> my();
    public List<YwArticleWithAuthorVO> getArticleListWithAuthor(YwArticlePageReqLimitVO pageReqVO);

}