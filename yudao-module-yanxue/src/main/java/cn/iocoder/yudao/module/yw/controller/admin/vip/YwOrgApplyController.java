package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgApplyAuditService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二级认证申请")
@RestController
@RequestMapping("/yw/yw-org-apply")
public class YwOrgApplyController {

    @Resource
    private YwOrgApplyAuditService orgApplyAuditService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的某类申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:query')")
    public CommonResult<YwOrgApplyRespVO> getMy(@RequestParam("applyType") String applyType) {
        return success(orgApplyAuditService.getMyOrgApply(applyType));
    }

    @PostMapping("/save-draft")
    @Operation(summary = "保存草稿")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:save-draft')")
    public CommonResult<Long> saveDraft(@RequestBody YwOrgApplySaveReqVO reqVO) {
        return success(orgApplyAuditService.saveOrgApplyDraft(reqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:submit')")
    public CommonResult<Boolean> submit(@RequestBody YwOrgApplySaveReqVO reqVO) {
        return success(orgApplyAuditService.submitOrgApply(reqVO));
    }

    @PostMapping("/parse")
    @Operation(summary = "解析上传文件")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:parse')")
    public CommonResult<YwOrgApplyRespVO> parse(@Valid @RequestBody YwOrgApplyParseReqVO reqVO) {
        return success(orgApplyAuditService.parseOrgApply(reqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "管理员分页查询机构认证申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:query')")
    public CommonResult<PageResult<YwOrgApplyRespVO>> getPage(@Valid YwOrgApplyAuditPageReqVO pageReqVO) {
        return success(orgApplyAuditService.getOrgApplyPage(pageReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "查询机构认证申请详情")
    @Parameter(name = "id", description = "申请 ID", required = true)
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:query')")
    public CommonResult<YwOrgApplyRespVO> get(@RequestParam("id") Long id) {
        return success(orgApplyAuditService.getOrgApply(id));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核机构认证申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:audit')")
    public CommonResult<Boolean> audit(@Valid @RequestBody YwOrgApplyAuditReqVO reqVO) {
        orgApplyAuditService.auditOrgApply(reqVO);
        return success(true);
    }
}
