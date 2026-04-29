package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgInfoApplyService;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgInfoService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoSaveReqVO;
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
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二级认证展示资料")
@RestController
@RequestMapping("/yw/yw-orginfo")
@Validated
public class YwOrgInfoController {

    @Resource
    private YwOrgInfoApplyService orgInfoApplyService;
    @Resource
    private YwOrgInfoService orgInfoService;

    @GetMapping("/page")
    @Operation(summary = "机构数据中心分页")
    public CommonResult<PageResult<YwOrgInfoRespVO>> getPage(@Valid YwOrgInfoPageReqVO pageReqVO) {
        return success(orgInfoService.getOrgInfoPage(pageReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "机构数据中心详情")
    public CommonResult<YwOrgInfoRespVO> get(@RequestParam("id") Long id) {
        return success(orgInfoService.getOrgInfo(id));
    }

    @PutMapping("/update")
    @Operation(summary = "机构数据中心更新")
    public CommonResult<Boolean> update(@Valid @RequestBody YwOrgInfoSaveReqVO reqVO) {
        orgInfoService.updateOrgInfo(reqVO);
        return success(true);
    }

    @GetMapping("/list-my")
    @Operation(summary = "查询我的已认证单位列表")
    //@PreAuthorize("@ss.hasPermission('yw:yw-orginfo:query')")
    public CommonResult<List<YwOrgInfoRespVO>> listMy() {
        return success(orgInfoApplyService.listMyOrgInfo());
    }
}
