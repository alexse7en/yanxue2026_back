package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoApplyDO;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二级认证展示资料编辑申请")
@RestController
@RequestMapping("/yw/yw-orginfo-apply")
public class YwOrgInfoApplyController {

    @Resource
    private YwVipFacadeService vipFacadeService;

    @GetMapping("/get-latest-my")
    @Operation(summary = "查询某认证单位最近申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:query')")
    public CommonResult<YwOrgInfoApplyDO> getLatestMy(@RequestParam("orginfoId") Long orginfoId) {
        return success(vipFacadeService.getLatestMyOrgInfoApply(orginfoId));
    }

    @PostMapping("/create")
    @Operation(summary = "创建展示资料编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:create')")
    public CommonResult<Long> create(@RequestBody YwOrgInfoApplySaveReqVO reqVO) {
        return success(vipFacadeService.createOrgInfoApply(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新展示资料编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-orginfo-apply:update')")
    public CommonResult<Boolean> update(@RequestBody YwOrgInfoApplySaveReqVO reqVO) {
        vipFacadeService.updateOrgInfoApply(reqVO);
        return success(true);
    }
}
