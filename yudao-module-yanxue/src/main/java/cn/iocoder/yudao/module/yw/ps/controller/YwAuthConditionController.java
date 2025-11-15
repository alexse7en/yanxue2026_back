package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthConditionRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthConditionSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthConditionDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthConditionService;

@Tag(name = "管理后台 - 志愿者评审条件")
@RestController
@RequestMapping("/yw/ps/yw-auth-condition")
@Validated
public class YwAuthConditionController {

    @Resource
    private YwAuthConditionService ywAuthConditionService;

    @PostMapping("/create")
    @Operation(summary = "创建志愿者评审条件")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:create')")
    public CommonResult<Long> createYwAuthCondition(@Valid @RequestBody YwAuthConditionSaveReqVO createReqVO) {
        return success(ywAuthConditionService.createYwAuthCondition(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新志愿者评审条件")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:update')")
    public CommonResult<Boolean> updateYwAuthCondition(@Valid @RequestBody YwAuthConditionSaveReqVO updateReqVO) {
        ywAuthConditionService.updateYwAuthCondition(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除志愿者评审条件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:delete')")
    public CommonResult<Boolean> deleteYwAuthCondition(@RequestParam("id") Long id) {
        ywAuthConditionService.deleteYwAuthCondition(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除志愿者评审条件")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:delete')")
    public CommonResult<Boolean> deleteYwAuthConditionList(@RequestParam("ids") List<Long> ids) {
        ywAuthConditionService.deleteYwAuthConditionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得志愿者评审条件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:query')")
    public CommonResult<YwAuthConditionRespVO> getYwAuthCondition(@RequestParam("id") Long id) {
        YwAuthConditionDO ywAuthCondition = ywAuthConditionService.getYwAuthCondition(id);
        return success(BeanUtils.toBean(ywAuthCondition, YwAuthConditionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得志愿者评审条件分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:query')")
    public CommonResult<PageResult<YwAuthConditionRespVO>> getYwAuthConditionPage(@Valid YwAuthConditionPageReqVO pageReqVO) {
        PageResult<YwAuthConditionDO> pageResult = ywAuthConditionService.getYwAuthConditionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthConditionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出志愿者评审条件 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-condition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwAuthConditionExcel(@Valid YwAuthConditionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwAuthConditionDO> list = ywAuthConditionService.getYwAuthConditionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "志愿者评审条件.xls", "数据", YwAuthConditionRespVO.class,
                        BeanUtils.toBean(list, YwAuthConditionRespVO.class));
    }

}