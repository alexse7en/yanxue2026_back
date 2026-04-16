package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplySubmitReqVO;

public interface YwCertStudentApplyService {

    PageResult<YwCertStudentApplyRespVO> getApplyPageMy(YwCertStudentApplyPageReqVO reqVO);

    YwCertStudentApplyRespVO getApply(Long id);

    YwCertStudentApplyRespVO parseApply(YwCertStudentApplyParseReqVO reqVO);

    Long submitApply(YwCertStudentApplySubmitReqVO reqVO);

    PageResult<YwCertStudentApplyRespVO> getApplyAuditPage(YwCertStudentApplyAuditPageReqVO reqVO);

    void auditApply(YwCertStudentApplyAuditReqVO reqVO);
}
