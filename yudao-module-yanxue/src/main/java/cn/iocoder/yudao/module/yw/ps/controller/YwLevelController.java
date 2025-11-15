package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwLevelRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelDO;
import cn.iocoder.yudao.module.yw.ps.service.YwLevelService;

@Tag(name = "管理后台 - 志愿者等级")
@RestController
@RequestMapping("/yw/ps/yw-level")
@Validated
public class YwLevelController {

    @Resource
    private YwLevelService ywLevelService;

    @PostMapping("/create")
    @Operation(summary = "创建志愿者等级")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:create')")
    public CommonResult<Long> createYwLevel(@Valid @RequestBody YwLevelSaveReqVO createReqVO) {
        return success(ywLevelService.createYwLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新志愿者等级")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:update')")
    public CommonResult<Boolean> updateYwLevel(@Valid @RequestBody YwLevelSaveReqVO updateReqVO) {
        ywLevelService.updateYwLevel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除志愿者等级")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:delete')")
    public CommonResult<Boolean> deleteYwLevel(@RequestParam("id") Long id) {
        ywLevelService.deleteYwLevel(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除志愿者等级")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:delete')")
    public CommonResult<Boolean> deleteYwLevelList(@RequestParam("ids") List<Long> ids) {
        ywLevelService.deleteYwLevelListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得志愿者等级")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:query')")
    public CommonResult<YwLevelRespVO> getYwLevel(@RequestParam("id") Long id) {
        YwLevelDO ywLevel = ywLevelService.getYwLevel(id);
        return success(BeanUtils.toBean(ywLevel, YwLevelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得志愿者等级分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:query')")
    public CommonResult<PageResult<YwLevelRespVO>> getYwLevelPage(@Valid YwLevelPageReqVO pageReqVO) {
        PageResult<YwLevelDO> pageResult = ywLevelService.getYwLevelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwLevelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出志愿者等级 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwLevelExcel(@Valid YwLevelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwLevelDO> list = ywLevelService.getYwLevelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "志愿者等级.xls", "数据", YwLevelRespVO.class,
                        BeanUtils.toBean(list, YwLevelRespVO.class));
    }

}