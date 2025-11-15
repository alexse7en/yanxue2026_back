package cn.iocoder.yudao.module.yw.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberLevelAddressDO;
import cn.iocoder.yudao.module.yw.service.YwMemberLevelAddressService;
import cn.iocoder.yudao.module.yw.vo.YwMemberLevelAddressUpdateDeliveryReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberLevelAddressPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberLevelAddressRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberLevelAddressSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户收件地址")
@RestController
@RequestMapping("/yw/member-level-address")
@Validated
public class YwMemberLevelAddressAppController {

    @Resource
    private YwMemberLevelAddressService memberLevelAddressService;

    @PostMapping("/create")
    @Operation(summary = "创建用户收件地址")
    public CommonResult<Long> createMemberLevelAddress(@Valid @RequestBody YwMemberLevelAddressSaveReqVO createReqVO) {
        return success(memberLevelAddressService.createMemberLevelAddress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户收件地址")
    public CommonResult<Boolean> updateMemberLevelAddress(@Valid @RequestBody YwMemberLevelAddressSaveReqVO updateReqVO) {
        memberLevelAddressService.updateMemberLevelAddress(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-delivery")
    @Operation(summary = "更新快递公司与单号（memberId + levelId）")
    @PreAuthorize("@ss.hasPermission('yw:member-level-address:update')")
    public CommonResult<Boolean> updateDelivery(@Valid @RequestBody YwMemberLevelAddressUpdateDeliveryReqVO reqVO) {
        memberLevelAddressService.updateDeliveryByMemberIdAndLevelId(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户收件地址")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:member-level-address:delete')")
    public CommonResult<Boolean> deleteMemberLevelAddress(@RequestParam("id") Long id) {
        memberLevelAddressService.deleteMemberLevelAddress(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除用户收件地址")
                @PreAuthorize("@ss.hasPermission('yw:member-level-address:delete')")
    public CommonResult<Boolean> deleteMemberLevelAddressList(@RequestParam("ids") List<Long> ids) {
        memberLevelAddressService.deleteMemberLevelAddressListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户收件地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:member-level-address:query')")
    public CommonResult<YwMemberLevelAddressRespVO> getMemberLevelAddress(@RequestParam("id") Long id) {
        YwMemberLevelAddressDO memberLevelAddress = memberLevelAddressService.getMemberLevelAddress(id);
        return success(BeanUtils.toBean(memberLevelAddress, YwMemberLevelAddressRespVO.class));
    }

    @GetMapping("/get1")
    @Operation(summary = "获得用户收件地址（memberId + levelId）")
    @PreAuthorize("@ss.hasPermission('yw:member-level-address:query')")
    public CommonResult<YwMemberLevelAddressRespVO> getMemberLevelAddress(
            @RequestParam("memberId") Long memberId,
            @RequestParam("levelId") Long levelId) {
        YwMemberLevelAddressDO address = memberLevelAddressService.getByMemberIdAndLevelId(memberId, levelId);
        return success(BeanUtils.toBean(address, YwMemberLevelAddressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户收件地址分页")
    @PreAuthorize("@ss.hasPermission('yw:member-level-address:query')")
    public CommonResult<PageResult<YwMemberLevelAddressRespVO>> getMemberLevelAddressPage(@Valid YwMemberLevelAddressPageReqVO pageReqVO) {
        PageResult<YwMemberLevelAddressDO> pageResult = memberLevelAddressService.getMemberLevelAddressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwMemberLevelAddressRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户收件地址 Excel")
    @PreAuthorize("@ss.hasPermission('yw:member-level-address:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMemberLevelAddressExcel(@Valid YwMemberLevelAddressPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwMemberLevelAddressDO> list = memberLevelAddressService.getMemberLevelAddressPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户收件地址.xls", "数据", YwMemberLevelAddressRespVO.class,
                        BeanUtils.toBean(list, YwMemberLevelAddressRespVO.class));
    }

}
