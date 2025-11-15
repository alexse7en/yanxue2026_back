package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwLevelConditionRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelConditionSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelConditionDO;
import cn.iocoder.yudao.module.yw.ps.service.YwLevelConditionService;

@Tag(name = "管理后台 - 评审条件")
@RestController
@RequestMapping("/yw/ps/yw-level-condition")
@Validated
public class YwLevelConditionController {

    @Resource
    private YwLevelConditionService ywLevelConditionService;

    @PostMapping("/create")
    @Operation(summary = "创建评审条件")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:create')")
    public CommonResult<Long> createYwLevelCondition(@Valid @RequestBody YwLevelConditionSaveReqVO createReqVO) {
        return success(ywLevelConditionService.createYwLevelCondition(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评审条件")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:update')")
    public CommonResult<Boolean> updateYwLevelCondition(@Valid @RequestBody YwLevelConditionSaveReqVO updateReqVO) {
        ywLevelConditionService.updateYwLevelCondition(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评审条件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:delete')")
    public CommonResult<Boolean> deleteYwLevelCondition(@RequestParam("id") Long id) {
        ywLevelConditionService.deleteYwLevelCondition(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评审条件")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:delete')")
    public CommonResult<Boolean> deleteYwLevelConditionList(@RequestParam("ids") List<Long> ids) {
        ywLevelConditionService.deleteYwLevelConditionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评审条件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:query')")
    public CommonResult<YwLevelConditionRespVO> getYwLevelCondition(@RequestParam("id") Long id) {
        YwLevelConditionDO ywLevelCondition = ywLevelConditionService.getYwLevelCondition(id);
        return success(BeanUtils.toBean(ywLevelCondition, YwLevelConditionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评审条件分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:query')")
    public CommonResult<PageResult<YwLevelConditionRespVO>> getYwLevelConditionPage(@Valid YwLevelConditionPageReqVO pageReqVO) {
        PageResult<YwLevelConditionDO> pageResult = ywLevelConditionService.getYwLevelConditionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwLevelConditionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评审条件 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-condition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwLevelConditionExcel(@Valid YwLevelConditionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwLevelConditionDO> list = ywLevelConditionService.getYwLevelConditionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评审条件.xls", "数据", YwLevelConditionRespVO.class,
                        BeanUtils.toBean(list, YwLevelConditionRespVO.class));
    }

}