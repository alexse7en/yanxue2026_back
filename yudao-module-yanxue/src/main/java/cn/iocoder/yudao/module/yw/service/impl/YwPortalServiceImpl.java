package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.yw.convert.portal.YwPortalConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwYanxueArticleDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwOrgInfoMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwYanxueArticleMapper;
import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthLevelMapper;
import cn.iocoder.yudao.module.yw.service.YwPortalService;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalVipInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.query.YwPortalCertQueryReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalCertRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalOrgInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalVipInfoRespVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_PORTAL_CERT_QUERY_CONDITION_REQUIRED;

@Service
@Validated
public class YwPortalServiceImpl implements YwPortalService {

    private static final Integer PUBLISHED_STATUS = 1;

    @Resource
    private YwYanxueArticleMapper ywYanxueArticleMapper;
    @Resource
    private YwVipInfoMapper ywVipInfoMapper;
    @Resource
    private YwOrgInfoMapper ywOrgInfoMapper;
    @Resource
    private YwAuthLevelMapper ywAuthLevelMapper;

    @Override
    public PageResult<YwPortalArticleRespVO> getPortalArticlePage(YwPortalArticlePageReqVO reqVO) {
        reqVO.setStatus(PUBLISHED_STATUS);
        PageResult<YwYanxueArticleDO> pageResult = ywYanxueArticleMapper.selectPortalPage(reqVO);
        return new PageResult<>(YwPortalConvert.INSTANCE.convertArticleList(pageResult.getList()), pageResult.getTotal());
    }

    @Override
    public YwPortalArticleRespVO getPortalArticle(Long id) {
        YwYanxueArticleDO article = ywYanxueArticleMapper.selectById(id);
        if (article == null || !PUBLISHED_STATUS.equals(article.getStatus())) {
            return null;
        }
        ywYanxueArticleMapper.addViewCount(id);
        return YwPortalConvert.INSTANCE.convert(article);
    }

    @Override
    @TenantIgnore
    public PageResult<YwPortalVipInfoRespVO> getPortalVipInfoPage(YwPortalVipInfoPageReqVO reqVO) {
        reqVO.setStatus(PUBLISHED_STATUS);
        PageResult<YwVipInfoDO> pageResult = ywVipInfoMapper.selectPortalPage(reqVO);
        return new PageResult<>(YwPortalConvert.INSTANCE.convertVipList(pageResult.getList()), pageResult.getTotal());
    }

    @Override
    @TenantIgnore
    public YwPortalVipInfoRespVO getPortalVipInfo(Long id) {
        YwVipInfoDO vipInfo = ywVipInfoMapper.selectById(id);
        if (vipInfo == null || !PUBLISHED_STATUS.equals(vipInfo.getStatus())) {
            return null;
        }
        return YwPortalConvert.INSTANCE.convertVip(vipInfo);
    }

    @Override
    @TenantIgnore
    public PageResult<YwPortalOrgInfoRespVO> getPortalOrgInfoPage(YwPortalOrgInfoPageReqVO reqVO) {
        reqVO.setStatus(PUBLISHED_STATUS);
        PageResult<YwOrgInfoDO> pageResult = ywOrgInfoMapper.selectPortalPage(reqVO);
        return new PageResult<>(YwPortalConvert.INSTANCE.convertOrgList(pageResult.getList()), pageResult.getTotal());
    }

    @Override
    @TenantIgnore
    public YwPortalOrgInfoRespVO getPortalOrgInfo(Long id) {
        YwOrgInfoDO orgInfo = ywOrgInfoMapper.selectById(id);
        if (orgInfo == null || !PUBLISHED_STATUS.equals(orgInfo.getStatus())) {
            return null;
        }
        return YwPortalConvert.INSTANCE.convertOrg(orgInfo);
    }

    @Override
    public PageResult<YwPortalCertRespVO> queryStudentCert(YwPortalCertQueryReqVO reqVO) {
        validateCertQueryCondition(reqVO);
        List<YwPortalCertRespVO> list = ywAuthLevelMapper.selectPortalStudentCertList(reqVO);
        return new PageResult<>(list, (long) list.size());
    }

    @Override
    public PageResult<YwPortalCertRespVO> queryTutorCert(YwPortalCertQueryReqVO reqVO) {
        validateCertQueryCondition(reqVO);
        List<YwPortalCertRespVO> list = ywAuthLevelMapper.selectPortalTutorCertList(reqVO);
        return new PageResult<>(list, (long) list.size());
    }

    private void validateCertQueryCondition(YwPortalCertQueryReqVO reqVO) {
        boolean hasCertNo = StringUtils.hasText(reqVO.getCertNo());
        boolean hasNameAndSuffix = StringUtils.hasText(reqVO.getName()) && StringUtils.hasText(reqVO.getIdCardSuffix());
        if (!hasCertNo && !hasNameAndSuffix) {
            throw exception(YW_PORTAL_CERT_QUERY_CONDITION_REQUIRED);
        }
    }
}
