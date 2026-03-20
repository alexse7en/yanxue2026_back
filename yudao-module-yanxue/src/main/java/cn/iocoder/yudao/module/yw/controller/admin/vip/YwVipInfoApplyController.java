package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.vip.YwVipInfoApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员展示信息编辑申请")
@RestController
@RequestMapping("/yw/yw-vipinfo-apply")
public class YwVipInfoApplyController {

    @Resource
    private YwVipInfoApplyService vipInfoApplyService;

    @GetMapping("/get")
    @Operation(summary = "查询会员展示信息编辑申请")
    public CommonResult<YwVipInfoApplyRespVO> get(@RequestParam(value = "id", required = false) Long id,
                                                  @RequestParam(value = "userId", required = false) Long userId) {
        return success(vipInfoApplyService.getVipInfoApply(id, userId));
    }

    @PostMapping("/create")
    @Operation(summary = "创建会员信息编辑申请")
    public CommonResult<Long> create(@RequestBody YwVipInfoApplySaveReqVO reqVO) {
        return success(vipInfoApplyService.createVipInfoApply(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新会员信息编辑申请")
    public CommonResult<Boolean> update(@RequestBody YwVipInfoApplySaveReqVO reqVO) {
        vipInfoApplyService.updateVipInfoApply(reqVO);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询会员展示信息编辑申请")
    public CommonResult<PageResult<YwVipInfoApplyRespVO>> page(@Valid YwVipInfoApplyPageReqVO reqVO) {
        return success(vipInfoApplyService.getVipInfoApplyPage(reqVO));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核会员展示信息编辑申请")
    public CommonResult<Boolean> audit(@Valid @RequestBody YwVipInfoApplyAuditReqVO reqVO) {
        vipInfoApplyService.auditVipInfoApply(reqVO);
        return success(true);
    }
}
