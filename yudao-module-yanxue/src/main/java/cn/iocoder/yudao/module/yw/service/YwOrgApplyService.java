package cn.iocoder.yudao.module.yw.service;

import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwOrgApplyRespVO;
import java.util.List;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwOrgApplyPageReqVO; // 你定义的分页查询VO



public interface YwOrgApplyService
{
    PageResult<YwOrgApplyRespVO> getPage(YwOrgApplyPageReqVO reqVO);
    List<YwOrgApplyRespVO> getList();
    YwOrgApplyRespVO getDetail(Long id);
    void audit(YwOrganizationApplyAuditReqVO reqVO);
}
