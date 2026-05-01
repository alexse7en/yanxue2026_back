package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.convert.vip.YwYanxueArticleConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwYanxueArticleDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwYanxueArticleMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwYanxueArticleService;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.ARTICLE_NOT_EXISTS;

@Service
@Validated
public class YwYanxueArticleServiceImpl implements YwYanxueArticleService {

    @Resource
    private YwYanxueArticleMapper yanxueArticleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createArticle(YwYanxueArticleSaveReqVO reqVO) {
        YwYanxueArticleDO article = YwYanxueArticleConvert.INSTANCE.convert(reqVO);
        yanxueArticleMapper.insert(article);
        return article.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(YwYanxueArticleSaveReqVO reqVO) {
        validateArticleExists(reqVO.getId());
        YwYanxueArticleDO article = YwYanxueArticleConvert.INSTANCE.convert(reqVO);
        yanxueArticleMapper.updateById(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(Long id) {
        validateArticleExists(id);
        yanxueArticleMapper.deleteById(id);
    }

    @Override
    public YwYanxueArticleRespVO getArticle(Long id) {
        YwYanxueArticleDO article = yanxueArticleMapper.selectById(id);
        if (article == null) {
            throw exception(ARTICLE_NOT_EXISTS);
        }
        return YwYanxueArticleConvert.INSTANCE.convert(article);
    }

    @Override
    public PageResult<YwYanxueArticleRespVO> getArticlePage(YwYanxueArticlePageReqVO pageReqVO) {
        return YwYanxueArticleConvert.INSTANCE.convertPage(yanxueArticleMapper.selectPage(pageReqVO));
    }

    @Override
    public List<YwYanxueArticleRespVO> getArticleList(YwYanxueArticlePageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return YwYanxueArticleConvert.INSTANCE.convertList(yanxueArticleMapper.selectPage(pageReqVO).getList());
    }

    private void validateArticleExists(Long id) {
        if (id == null || yanxueArticleMapper.selectById(id) == null) {
            throw exception(ARTICLE_NOT_EXISTS);
        }
    }
}
