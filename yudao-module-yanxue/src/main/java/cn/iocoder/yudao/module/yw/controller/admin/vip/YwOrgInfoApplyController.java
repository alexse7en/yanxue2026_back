package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgInfoApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二级认证展示资料编辑申请")
@RestController
@RequestMapping("/yw/yw-orginfo-apply")
public class YwOrgInfoApplyController {

    @Resource
    private YwOrgInfoApplyService orgInfoApplyService;

    @GetMapping("/get-latest-my")
    @Operation(summary = "查询某认证单位最近申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:query')")
    public CommonResult<YwOrgInfoApplyRespVO> getLatestMy(@RequestParam("orginfoId") Long orginfoId) {
        return success(orgInfoApplyService.getLatestMyOrgInfoApply(orginfoId));
    }

    @PostMapping("/create")
    @Operation(summary = "创建展示资料编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:create')")
    public CommonResult<Long> create(@RequestBody YwOrgInfoApplySaveReqVO reqVO) {
        return success(orgInfoApplyService.createOrgInfoApply(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新展示资料编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:update')")
    public CommonResult<Boolean> update(@RequestBody YwOrgInfoApplySaveReqVO reqVO) {
        orgInfoApplyService.updateOrgInfoApply(reqVO);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询展示资料编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:query')")
    public CommonResult<PageResult<YwOrgInfoApplyRespVO>> page(@Valid YwOrgInfoApplyPageReqVO reqVO) {
        return success(orgInfoApplyService.getOrgInfoApplyPage(reqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "查询展示资料编辑申请详情")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:query')")
    public CommonResult<YwOrgInfoApplyRespVO> get(@RequestParam("id") Long id) {
        return success(orgInfoApplyService.getOrgInfoApply(id));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核展示资料编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:audit')")
    public CommonResult<Boolean> audit(@Valid @RequestBody YwOrgInfoApplyAuditReqVO reqVO) {
        orgInfoApplyService.auditOrgInfoApply(reqVO);
        return success(true);
    }
}
