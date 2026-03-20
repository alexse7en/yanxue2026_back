package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.service.vip.YwVipInfoApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员展示信息")
@RestController
@RequestMapping("/yw/yw-vipinfo")
public class YwVipInfoController {

    @Resource
    private YwVipInfoApplyService vipInfoApplyService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的会员信息")
    public CommonResult<YwVipInfoRespVO> getMy() {
        return success(vipInfoApplyService.getMyVipInfo());
    }
}
