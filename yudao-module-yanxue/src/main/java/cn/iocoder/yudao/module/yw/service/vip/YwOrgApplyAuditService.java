package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplySaveReqVO;

public interface YwOrgApplyAuditService {

    YwOrgApplyRespVO getMyOrgApply(String applyType);

    Long saveOrgApplyDraft(YwOrgApplySaveReqVO reqVO);

    Boolean submitOrgApply(YwOrgApplySaveReqVO reqVO);

    YwOrgApplyRespVO parseOrgApply(YwOrgApplyParseReqVO reqVO);

    PageResult<YwOrgApplyRespVO> getOrgApplyPage(YwOrgApplyAuditPageReqVO reqVO);

    YwOrgApplyRespVO getOrgApply(Long id);

    void auditOrgApply(YwOrgApplyAuditReqVO reqVO);
}
