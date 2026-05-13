package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwYanxueArticleConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwYanxueArticleDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwYanxueArticleMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwYanxueArticleService;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleExcelVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleImportRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Override
    public List<YwYanxueArticleExcelVO> getArticleExcelList(YwYanxueArticlePageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return convertExcelList(yanxueArticleMapper.selectPage(pageReqVO).getList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YwYanxueArticleImportRespVO importArticleList(List<YwYanxueArticleExcelVO> importArticles,
                                                         boolean updateSupport) {
        YwYanxueArticleImportRespVO respVO = YwYanxueArticleImportRespVO.builder()
                .createTitles(new ArrayList<>())
                .updateTitles(new ArrayList<>())
                .failureTitles(new LinkedHashMap<>())
                .build();
        if (CollUtil.isEmpty(importArticles)) {
            return respVO;
        }

        for (int i = 0; i < importArticles.size(); i++) {
            YwYanxueArticleExcelVO importArticle = importArticles.get(i);
            if (isBlankImportArticle(importArticle)) {
                continue;
            }
            String failureKey = getImportFailureKey(importArticle, i);
            try {
                YwYanxueArticleDO article = convertImportArticle(importArticle);
                validateImportArticle(article);

                if (article.getId() == null) {
                    yanxueArticleMapper.insert(article);
                    respVO.getCreateTitles().add(article.getTitle());
                    continue;
                }

                if (yanxueArticleMapper.selectById(article.getId()) == null) {
                    respVO.getFailureTitles().put(failureKey, ARTICLE_NOT_EXISTS.getMsg());
                    continue;
                }
                if (!updateSupport) {
                    respVO.getFailureTitles().put(failureKey, "文章已存在，未开启更新支持");
                    continue;
                }
                yanxueArticleMapper.updateById(article);
                respVO.getUpdateTitles().add(article.getTitle());
            } catch (ConstraintViolationException ex) {
                respVO.getFailureTitles().put(failureKey, ex.getMessage());
            } catch (IllegalArgumentException ex) {
                respVO.getFailureTitles().put(failureKey, ex.getMessage());
            }
        }
        return respVO;
    }

    private void validateArticleExists(Long id) {
        if (id == null || yanxueArticleMapper.selectById(id) == null) {
            throw exception(ARTICLE_NOT_EXISTS);
        }
    }

    private List<YwYanxueArticleExcelVO> convertExcelList(List<YwYanxueArticleDO> list) {
        List<YwYanxueArticleExcelVO> excelList = new ArrayList<>(list.size());
        for (YwYanxueArticleDO article : list) {
            excelList.add(YwYanxueArticleExcelVO.builder()
                    .id(article.getId())
                    .category(article.getCategory())
                    .title(article.getTitle())
                    .author(article.getAuthor())
                    .content(article.getContent())
                    .image(article.getImage())
                    .summary(article.getSummary())
                    .sortOrder(article.getSortOrder())
                    .status(article.getStatus() == null ? null : String.valueOf(article.getStatus()))
                    .isTop(formatIsTop(article.getIsTop()))
                    .viewCount(article.getViewCount())
                    .publishTime(article.getPublishTime())
                    .downloadurl(article.getDownloadurl())
                    .build());
        }
        return excelList;
    }

    private YwYanxueArticleDO convertImportArticle(YwYanxueArticleExcelVO importArticle) {
        return YwYanxueArticleDO.builder()
                .id(importArticle.getId())
                .category(trimToNull(importArticle.getCategory()))
                .title(trimToNull(importArticle.getTitle()))
                .author(trimToNull(importArticle.getAuthor()))
                .content(importArticle.getContent())
                .image(trimToNull(importArticle.getImage()))
                .summary(trimToNull(importArticle.getSummary()))
                .sortOrder(importArticle.getSortOrder())
                .status(parseStatus(importArticle.getStatus()))
                .isTop(parseIsTop(importArticle.getIsTop()))
                .viewCount(importArticle.getViewCount())
                .publishTime(importArticle.getPublishTime())
                .downloadurl(trimToNull(importArticle.getDownloadurl()))
                .build();
    }

    private void validateImportArticle(YwYanxueArticleDO article) {
        YwYanxueArticleSaveReqVO reqVO = new YwYanxueArticleSaveReqVO();
        reqVO.setId(article.getId());
        reqVO.setCategory(article.getCategory());
        reqVO.setTitle(article.getTitle());
        reqVO.setContent(article.getContent());
        reqVO.setImage(article.getImage());
        reqVO.setSummary(article.getSummary());
        reqVO.setSortOrder(article.getSortOrder());
        reqVO.setStatus(article.getStatus());
        reqVO.setIsTop(article.getIsTop());
        reqVO.setViewCount(article.getViewCount());
        reqVO.setPublishTime(article.getPublishTime());
        reqVO.setDownloadurl(article.getDownloadurl());
        reqVO.setAuthor(article.getAuthor());
        ValidationUtils.validate(reqVO);

        if (StrUtil.isBlank(article.getContent())) {
            throw new IllegalArgumentException("文章内容不能为空");
        }
        if (article.getStatus() == null) {
            throw new IllegalArgumentException("状态不能为空");
        }
    }

    private String getImportFailureKey(YwYanxueArticleExcelVO importArticle, int index) {
        if (importArticle != null && StrUtil.isNotBlank(importArticle.getTitle())) {
            return importArticle.getTitle();
        }
        return "第 " + (index + 2) + " 行";
    }

    private boolean isBlankImportArticle(YwYanxueArticleExcelVO importArticle) {
        return importArticle == null
                || importArticle.getId() == null
                && StrUtil.isBlank(importArticle.getCategory())
                && StrUtil.isBlank(importArticle.getTitle())
                && StrUtil.isBlank(importArticle.getAuthor())
                && StrUtil.isBlank(importArticle.getContent())
                && StrUtil.isBlank(importArticle.getImage())
                && StrUtil.isBlank(importArticle.getSummary())
                && importArticle.getSortOrder() == null
                && StrUtil.isBlank(importArticle.getStatus())
                && StrUtil.isBlank(importArticle.getIsTop())
                && importArticle.getViewCount() == null
                && importArticle.getPublishTime() == null
                && StrUtil.isBlank(importArticle.getDownloadurl());
    }

    private Integer parseStatus(String status) {
        String value = trimToNull(status);
        if (value == null) {
            return null;
        }
        if ("草稿".equals(value)) {
            return 0;
        }
        if ("发布".equals(value) || "已发布".equals(value)) {
            return 1;
        }
        if ("下架".equals(value) || "已下架".equals(value)) {
            return 2;
        }
        try {
            int statusValue = Integer.parseInt(removeNumberSuffix(value));
            if (statusValue < 0 || statusValue > 2) {
                throw new IllegalArgumentException("状态只能为 0、1、2");
            }
            return statusValue;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("状态只能为 0、1、2、草稿、发布、下架");
        }
    }

    private Boolean parseIsTop(String isTop) {
        String value = trimToNull(isTop);
        if (value == null) {
            return false;
        }
        if ("1".equals(removeNumberSuffix(value)) || "true".equalsIgnoreCase(value) || "是".equals(value)
                || "置顶".equals(value)) {
            return true;
        }
        if ("0".equals(removeNumberSuffix(value)) || "false".equalsIgnoreCase(value) || "否".equals(value)
                || "不置顶".equals(value)) {
            return false;
        }
        throw new IllegalArgumentException("是否置顶只能为 true/false、1/0、是/否");
    }

    private String formatIsTop(Boolean isTop) {
        if (isTop == null) {
            return null;
        }
        return Boolean.TRUE.equals(isTop) ? "是" : "否";
    }

    private String trimToNull(String value) {
        return StrUtil.blankToDefault(StrUtil.trim(value), null);
    }

    private String removeNumberSuffix(String value) {
        return StrUtil.removeSuffix(value, ".0");
    }
}
