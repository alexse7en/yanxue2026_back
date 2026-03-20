package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplySaveReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;

public interface YwVipInfoApplyService {

    YwVipInfoRespVO getMyVipInfo();

    YwVipInfoApplyRespVO getVipInfoApply(Long id, Long userId);

    Long createVipInfoApply(YwVipInfoApplySaveReqVO reqVO);

    void updateVipInfoApply(YwVipInfoApplySaveReqVO reqVO);

    PageResult<YwVipInfoApplyRespVO> getVipInfoApplyPage(YwVipInfoApplyPageReqVO reqVO);

    void auditVipInfoApply(YwVipInfoApplyAuditReqVO reqVO);
}
