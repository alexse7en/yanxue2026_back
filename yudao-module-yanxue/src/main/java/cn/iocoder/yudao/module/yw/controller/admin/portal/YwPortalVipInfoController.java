package cn.iocoder.yudao.module.yw.controller.admin.portal;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.YwPortalService;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalVipInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalVipInfoRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 首页会员中心")
@RestController
@RequestMapping("/yw-vipinfo")
@Validated
public class YwPortalVipInfoController {

    @Resource
    private YwPortalService ywPortalService;

    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "首页会员中心分页")
    public CommonResult<PageResult<YwPortalVipInfoRespVO>> getVipInfoPage(@Valid YwPortalVipInfoPageReqVO pageReqVO) {
        return success(ywPortalService.getPortalVipInfoPage(pageReqVO));
    }

    @GetMapping("/get")
    @PermitAll
    @Operation(summary = "首页会员中心详情")
    public CommonResult<YwPortalVipInfoRespVO> getVipInfo(@RequestParam("id") Long id) {
        return success(ywPortalService.getPortalVipInfo(id));
    }
}

