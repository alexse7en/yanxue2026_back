package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoApplyDO;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员展示信息编辑申请")
@RestController
@RequestMapping("/yw/yw-vipinfo-apply")
public class YwVipInfoApplyController {

    @Resource
    private YwVipFacadeService vipFacadeService;

    @GetMapping("/get-latest-my")
    @Operation(summary = "查询我最近一次信息编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vipinfo-apply:query')")
    public CommonResult<YwVipInfoApplyDO> getLatestMy() {
        return success(vipFacadeService.getLatestMyVipInfoApply());
    }

    @PostMapping("/create")
    @Operation(summary = "创建会员信息编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vipinfo-apply:create')")
    public CommonResult<Long> create(@RequestBody YwVipInfoApplySaveReqVO reqVO) {
        return success(vipFacadeService.createVipInfoApply(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新会员信息编辑申请")
    @PreAuthorize("@ss.hasPermission('yw:yw-vipinfo-apply:update')")
    public CommonResult<Boolean> update(@RequestBody YwVipInfoApplySaveReqVO reqVO) {
        vipFacadeService.updateVipInfoApply(reqVO);
        return success(true);
    }
}
