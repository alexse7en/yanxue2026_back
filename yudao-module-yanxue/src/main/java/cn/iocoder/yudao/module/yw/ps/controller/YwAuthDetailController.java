package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthDetailRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthDetailSaveReqVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthDetailDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthDetailService;

@Tag(name = "管理后台 - 志愿者等级")
@RestController
@RequestMapping("/yw/ps/yw-auth-detail")
@Validated
public class YwAuthDetailController {

    @Resource
    private YwAuthDetailService ywAuthDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建志愿者等级")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:create')")
    public CommonResult<Long> createYwAuthDetail(@Valid @RequestBody YwAuthDetailSaveReqVO createReqVO) {
        return success(ywAuthDetailService.createYwAuthDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新志愿者等级")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:update')")
    public CommonResult<Boolean> updateYwAuthDetail(@Valid @RequestBody YwAuthDetailSaveReqVO updateReqVO) {
        ywAuthDetailService.updateYwAuthDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除志愿者等级")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:delete')")
    public CommonResult<Boolean> deleteYwAuthDetail(@RequestParam("id") Long id) {
        ywAuthDetailService.deleteYwAuthDetail(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除志愿者等级")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:delete')")
    public CommonResult<Boolean> deleteYwAuthDetailList(@RequestParam("ids") List<Long> ids) {
        ywAuthDetailService.deleteYwAuthDetailListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得志愿者等级")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:query')")
    public CommonResult<YwAuthDetailRespVO> getYwAuthDetail(@RequestParam("id") Long id) {
        YwAuthDetailDO ywAuthDetail = ywAuthDetailService.getYwAuthDetail(id);
        return success(BeanUtils.toBean(ywAuthDetail, YwAuthDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得志愿者等级分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:query')")
    public CommonResult<PageResult<YwAuthDetailRespVO>> getYwAuthDetailPage(@Valid YwAuthDetailPageReqVO pageReqVO) {
        PageResult<YwAuthDetailDO> pageResult = ywAuthDetailService.getYwAuthDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出志愿者等级 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwAuthDetailExcel(@Valid YwAuthDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwAuthDetailDO> list = ywAuthDetailService.getYwAuthDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "志愿者等级.xls", "数据", YwAuthDetailRespVO.class,
                        BeanUtils.toBean(list, YwAuthDetailRespVO.class));
    }

}