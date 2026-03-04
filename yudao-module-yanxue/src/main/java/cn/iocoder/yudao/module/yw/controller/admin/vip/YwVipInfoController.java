package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员展示信息")
@RestController
@RequestMapping("/yw/yw-vipinfo")
public class YwVipInfoController {

    @Resource
    private YwVipFacadeService vipFacadeService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的会员信息")
    @PreAuthorize("@ss.hasPermission('yw:yw-vipinfo:query')")
    public CommonResult<Map<String, Object>> getMy() {
        return success(vipFacadeService.getMyVipInfo());
    }
}
