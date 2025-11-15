package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupNormPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwLevelGroupNormRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupNormSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupNormDO;
import cn.iocoder.yudao.module.yw.ps.service.YwLevelGroupNormService;

@Tag(name = "管理后台 - 评审细则")
@RestController
@RequestMapping("/yw/ps/yw-level-group-norm")
@Validated
public class YwLevelGroupNormController {

    @Resource
    private YwLevelGroupNormService ywLevelGroupNormService;

    @PostMapping("/create")
    @Operation(summary = "创建评审细则")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:create')")
    public CommonResult<Long> createYwLevelGroupNorm(@Valid @RequestBody YwLevelGroupNormSaveReqVO createReqVO) {
        return success(ywLevelGroupNormService.createYwLevelGroupNorm(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评审细则")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:update')")
    public CommonResult<Boolean> updateYwLevelGroupNorm(@Valid @RequestBody YwLevelGroupNormSaveReqVO updateReqVO) {
        ywLevelGroupNormService.updateYwLevelGroupNorm(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评审细则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:delete')")
    public CommonResult<Boolean> deleteYwLevelGroupNorm(@RequestParam("id") Long id) {
        ywLevelGroupNormService.deleteYwLevelGroupNorm(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评审细则")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:delete')")
    public CommonResult<Boolean> deleteYwLevelGroupNormList(@RequestParam("ids") List<Long> ids) {
        ywLevelGroupNormService.deleteYwLevelGroupNormListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评审细则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:query')")
    public CommonResult<YwLevelGroupNormRespVO> getYwLevelGroupNorm(@RequestParam("id") Long id) {
        YwLevelGroupNormDO ywLevelGroupNorm = ywLevelGroupNormService.getYwLevelGroupNorm(id);
        return success(BeanUtils.toBean(ywLevelGroupNorm, YwLevelGroupNormRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评审细则分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:query')")
    public CommonResult<PageResult<YwLevelGroupNormRespVO>> getYwLevelGroupNormPage(@Valid YwLevelGroupNormPageReqVO pageReqVO) {
        PageResult<YwLevelGroupNormDO> pageResult = ywLevelGroupNormService.getYwLevelGroupNormPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwLevelGroupNormRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评审细则 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-norm:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwLevelGroupNormExcel(@Valid YwLevelGroupNormPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwLevelGroupNormDO> list = ywLevelGroupNormService.getYwLevelGroupNormPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评审细则.xls", "数据", YwLevelGroupNormRespVO.class,
                        BeanUtils.toBean(list, YwLevelGroupNormRespVO.class));
    }

}