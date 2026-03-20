package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditRespVO;

public interface YwVipApplyAuditService {

    PageResult<YwVipApplyAuditRespVO> getVipApplyPage(YwVipApplyAuditPageReqVO reqVO);

    YwVipApplyAuditRespVO getVipApply(Long id);

    void auditVipApply(YwVipApplyAuditReqVO reqVO);
}
