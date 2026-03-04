package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipApplyDO;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.ParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员申请")
@RestController
@RequestMapping("/yw/yw-vip-apply")
public class YwVipApplyController {

    @Resource
    private YwVipFacadeService vipFacadeService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的会员申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:query')")
    public CommonResult<YwVipApplyDO> getMy() {
        return success(vipFacadeService.getMyVipApply());
    }

    @PostMapping("/save-draft")
    @Operation(summary = "保存草稿")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:save-draft')")
    public CommonResult<Long> saveDraft(@RequestBody YwVipApplySaveReqVO reqVO) {
        return success(vipFacadeService.saveVipApplyDraft(reqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:submit')")
    public CommonResult<Boolean> submit(@RequestBody YwVipApplySaveReqVO reqVO) {
        return success(vipFacadeService.submitVipApply(reqVO));
    }

    @PostMapping("/parse")
    @Operation(summary = "解析上传文件")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:parse')")
    public CommonResult<Object> parse(@RequestBody ParseReqVO reqVO) {
        return CommonResult.error(501, "解析暂未实现");
    }

    @GetMapping("/download-template")
    @Operation(summary = "下载模板")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:download-template')")
    public CommonResult<Object> downloadTemplate(@RequestParam("type") String type) {
        return CommonResult.error(501, "下载模板暂未实现");
    }
}
