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
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二级认证申请")
@RestController
@RequestMapping("/yw/yw-org-apply")
@Validated
public class YwOrgApplyController {

    @Resource
    private YwVipFacadeService vipFacadeService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的某类申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:query')")
    public CommonResult<Map<String, Object>> getMy(@RequestParam("applyType") String applyType) {
        return success(vipFacadeService.getMyOrgApply(applyType));
    }

    @PostMapping("/save-draft")
    @Operation(summary = "保存草稿")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:save-draft')")
    public CommonResult<Long> saveDraft(@RequestBody GenericApplyReqVO reqVO) {
        return success(vipFacadeService.saveOrgApplyDraft(reqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:submit')")
    public CommonResult<Boolean> submit(@RequestBody GenericApplyReqVO reqVO) {
        return success(vipFacadeService.submitOrgApply(reqVO));
    }

    @PostMapping("/parse")
    @Operation(summary = "解析上传文件")
    @PreAuthorize("@ss.hasPermission('yw:yw-org-apply:parse')")
    public CommonResult<Map<String, Object>> parse(@Valid @RequestBody ParseReqVO reqVO) {
        return success(vipFacadeService.parseFile("org", reqVO.getApplyType(), reqVO.getFilePath(), reqVO.getFileType()));
    }
}
