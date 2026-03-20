package cn.iocoder.yudao.module.yw.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalVipInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.query.YwPortalCertQueryReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalCertRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalOrgInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalVipInfoRespVO;

public interface YwPortalService {

    PageResult<YwPortalArticleRespVO> getPortalArticlePage(YwPortalArticlePageReqVO reqVO);

    YwPortalArticleRespVO getPortalArticle(Long id);

    PageResult<YwPortalVipInfoRespVO> getPortalVipInfoPage(YwPortalVipInfoPageReqVO reqVO);

    YwPortalVipInfoRespVO getPortalVipInfo(Long id);

    PageResult<YwPortalOrgInfoRespVO> getPortalOrgInfoPage(YwPortalOrgInfoPageReqVO reqVO);

    YwPortalOrgInfoRespVO getPortalOrgInfo(Long id);

    PageResult<YwPortalCertRespVO> queryStudentCert(YwPortalCertQueryReqVO reqVO);

    PageResult<YwPortalCertRespVO> queryTutorCert(YwPortalCertQueryReqVO reqVO);
}
