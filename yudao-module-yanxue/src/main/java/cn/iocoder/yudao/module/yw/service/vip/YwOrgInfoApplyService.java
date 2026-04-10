package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplySaveReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;

import java.util.List;

public interface YwOrgInfoApplyService {

    List<YwOrgInfoRespVO> listMyOrgInfo();

    YwOrgInfoApplyRespVO getLatestMyOrgInfoApply(Long orginfoId);

    Long createOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO);

    void updateOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO);

    PageResult<YwOrgInfoApplyRespVO> getOrgInfoApplyPage(YwOrgInfoApplyPageReqVO reqVO);

    YwOrgInfoApplyRespVO getOrgInfoApply(Long id);

    void auditOrgInfoApply(YwOrgInfoApplyAuditReqVO reqVO);
}
