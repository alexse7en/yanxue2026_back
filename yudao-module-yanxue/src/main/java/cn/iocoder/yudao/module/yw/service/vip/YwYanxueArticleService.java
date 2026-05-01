package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleSaveReqVO;

import javax.validation.Valid;
import java.util.List;

public interface YwYanxueArticleService {

    Long createArticle(@Valid YwYanxueArticleSaveReqVO reqVO);

    void updateArticle(@Valid YwYanxueArticleSaveReqVO reqVO);

    void deleteArticle(Long id);

    YwYanxueArticleRespVO getArticle(Long id);

    PageResult<YwYanxueArticleRespVO> getArticlePage(YwYanxueArticlePageReqVO pageReqVO);

    List<YwYanxueArticleRespVO> getArticleList(YwYanxueArticlePageReqVO pageReqVO);
}
