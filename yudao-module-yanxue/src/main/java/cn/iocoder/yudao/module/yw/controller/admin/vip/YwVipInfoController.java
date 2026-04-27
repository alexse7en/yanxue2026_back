package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.vip.YwVipInfoApplyService;
import cn.iocoder.yudao.module.yw.service.vip.YwVipInfoService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员展示信息")
@RestController
@RequestMapping("/yw/yw-vipinfo")
@Validated
public class YwVipInfoController {

    @Resource
    private YwVipInfoApplyService vipInfoApplyService;
    @Resource
    private YwVipInfoService vipInfoService;

    @GetMapping("/page")
    @Operation(summary = "会员数据中心分页")
    public CommonResult<PageResult<YwVipInfoRespVO>> getPage(@Valid YwVipInfoPageReqVO pageReqVO) {
        return success(vipInfoService.getVipInfoPage(pageReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "会员数据中心详情")
    public CommonResult<YwVipInfoRespVO> get(@RequestParam("id") Long id) {
        return success(vipInfoService.getVipInfo(id));
    }

    @PutMapping("/update")
    @Operation(summary = "会员数据中心更新")
    public CommonResult<Boolean> update(@Valid @RequestBody YwVipInfoSaveReqVO reqVO) {
        vipInfoService.updateVipInfo(reqVO);
        return success(true);
    }

    @GetMapping("/get-my")
    @Operation(summary = "查询我的会员信息")
    public CommonResult<YwVipInfoRespVO> getMy() {
        return success(vipInfoApplyService.getMyVipInfo());
    }
}
