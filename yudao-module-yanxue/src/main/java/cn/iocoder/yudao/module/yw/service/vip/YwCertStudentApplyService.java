package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplySubmitReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailUpdateReqVO;

public interface YwCertStudentApplyService {

    PageResult<YwCertStudentApplyRespVO> getApplyPageMy(YwCertStudentApplyPageReqVO reqVO);

    YwCertStudentApplyRespVO getApply(Long id);

    YwCertStudentApplyRespVO getApplyForAudit(Long id);

    PageResult<YwStudentApplyDetailRespVO> getDetailPage(YwStudentApplyDetailPageReqVO reqVO);

    PageResult<YwStudentApplyDetailRespVO> getDetailPageForAudit(YwStudentApplyDetailPageReqVO reqVO);

    YwCertStudentApplyRespVO parseApply(YwCertStudentApplyParseReqVO reqVO);

    Long submitApply(YwCertStudentApplySubmitReqVO reqVO);

    void updateDetails(YwStudentApplyDetailUpdateReqVO reqVO);

    PageResult<YwCertStudentApplyRespVO> getApplyAuditPage(YwCertStudentApplyAuditPageReqVO reqVO);

    void auditApply(YwCertStudentApplyAuditReqVO reqVO);
}
