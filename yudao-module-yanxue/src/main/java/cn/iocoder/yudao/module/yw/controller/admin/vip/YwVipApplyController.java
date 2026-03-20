package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.convert.vip.YwVipApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipApplyDO;
import cn.iocoder.yudao.module.yw.service.vip.YwVipApplyAuditService;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.ParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员申请")
@RestController
@RequestMapping("/yw/yw-vip-apply")
public class YwVipApplyController {

    @Resource
    private YwVipFacadeService vipFacadeService;
    @Resource
    private YwVipApplyAuditService vipApplyAuditService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的会员申请")
    public CommonResult<YwVipApplyAuditRespVO> getMy() {
        YwVipApplyDO apply = vipFacadeService.getMyVipApply();
        return success(apply == null ? null : YwVipApplyConvert.INSTANCE.convertAudit(apply));
    }

    @GetMapping("/page")
    @Operation(summary = "管理员分页查询会员申请")
    public CommonResult<PageResult<YwVipApplyAuditRespVO>> getPage(@Valid YwVipApplyAuditPageReqVO pageReqVO) {
        return success(vipApplyAuditService.getVipApplyPage(pageReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "查询会员申请详情")
    @Parameter(name = "id", description = "申请 ID", required = true)
    public CommonResult<YwVipApplyAuditRespVO> get(@RequestParam("id") Long id) {
        return success(vipApplyAuditService.getVipApply(id));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核会员申请")
    public CommonResult<Boolean> audit(@Valid @RequestBody YwVipApplyAuditReqVO reqVO) {
        vipApplyAuditService.auditVipApply(reqVO);
        return success(true);
    }

    @PostMapping("/save-draft")
    @Operation(summary = "保存草稿")
    public CommonResult<Long> saveDraft(@RequestBody YwVipApplySaveReqVO reqVO) {
        return success(vipFacadeService.saveVipApplyDraft(reqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交申请")
    public CommonResult<Boolean> submit(@RequestBody YwVipApplySaveReqVO reqVO) {
        return success(vipFacadeService.submitVipApply(reqVO));
    }

    @PostMapping("/parse")
    @Operation(summary = "解析上传文件")
    public CommonResult<YwVipApplyAuditRespVO> parse(@RequestBody ParseReqVO reqVO) {
        return success(YwVipApplyConvert.INSTANCE.convertAudit(vipFacadeService.parseVipApply(reqVO)));
    }
}
