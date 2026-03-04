package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.GenericApplyReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.ParseReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员申请")
@RestController
@RequestMapping("/yw/yw-vip-apply")
@Validated
public class YwVipApplyController {

    @Resource
    private YwVipFacadeService vipFacadeService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的会员申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:query')")
    public CommonResult<Map<String, Object>> getMy() {
        return success(vipFacadeService.getMyVipApply());
    }

    @PostMapping("/create")
    @Operation(summary = "创建会员申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:create')")
    public CommonResult<Long> create(@RequestBody GenericApplyReqVO reqVO) {
        return success(vipFacadeService.createVipApply(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新会员申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:update')")
    public CommonResult<Boolean> update(@RequestBody GenericApplyReqVO reqVO) {
        vipFacadeService.updateVipApply(reqVO);
        return success(true);
    }

    @PostMapping("/save-draft")
    @Operation(summary = "保存草稿")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:save-draft')")
    public CommonResult<Long> saveDraft(@RequestBody GenericApplyReqVO reqVO) {
        return success(vipFacadeService.saveVipApplyDraft(reqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:submit')")
    public CommonResult<Boolean> submit(@RequestBody GenericApplyReqVO reqVO) {
        return success(vipFacadeService.submitVipApply(reqVO));
    }

    @PostMapping("/parse")
    @Operation(summary = "解析上传文件")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:parse')")
    public CommonResult<Map<String, Object>> parse(@Valid @RequestBody ParseReqVO reqVO) {
        return success(vipFacadeService.parseFile("vip", null, reqVO.getFilePath(), reqVO.getFileType()));
    }

    @GetMapping("/download-template")
    @Operation(summary = "下载模板")
    @PreAuthorize("@ss.hasPermission('yw:yw-vip-apply:download-template')")
    public byte[] downloadTemplate(@RequestParam("type") String type) {
        return ("vip " + type + " template").getBytes(StandardCharsets.UTF_8);
    }
}
