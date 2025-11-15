package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwLevelGroupDetailRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupDetailSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDetailDO;
import cn.iocoder.yudao.module.yw.ps.service.YwLevelGroupDetailService;

@Tag(name = "管理后台 - 评分项")
@RestController
@RequestMapping("/yw/ps/yw-level-group-detail")
@Validated
public class YwLevelGroupDetailController {

    @Resource
    private YwLevelGroupDetailService ywLevelGroupDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建评分项")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:create')")
    public CommonResult<Long> createYwLevelGroupDetail(@Valid @RequestBody YwLevelGroupDetailSaveReqVO createReqVO) {
        return success(ywLevelGroupDetailService.createYwLevelGroupDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评分项")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:update')")
    public CommonResult<Boolean> updateYwLevelGroupDetail(@Valid @RequestBody YwLevelGroupDetailSaveReqVO updateReqVO) {
        ywLevelGroupDetailService.updateYwLevelGroupDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评分项")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:delete')")
    public CommonResult<Boolean> deleteYwLevelGroupDetail(@RequestParam("id") Long id) {
        ywLevelGroupDetailService.deleteYwLevelGroupDetail(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评分项")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:delete')")
    public CommonResult<Boolean> deleteYwLevelGroupDetailList(@RequestParam("ids") List<Long> ids) {
        ywLevelGroupDetailService.deleteYwLevelGroupDetailListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评分项")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:query')")
    public CommonResult<YwLevelGroupDetailRespVO> getYwLevelGroupDetail(@RequestParam("id") Long id) {
        YwLevelGroupDetailDO ywLevelGroupDetail = ywLevelGroupDetailService.getYwLevelGroupDetail(id);
        return success(BeanUtils.toBean(ywLevelGroupDetail, YwLevelGroupDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评分项分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:query')")
    public CommonResult<PageResult<YwLevelGroupDetailRespVO>> getYwLevelGroupDetailPage(@Valid YwLevelGroupDetailPageReqVO pageReqVO) {
        PageResult<YwLevelGroupDetailDO> pageResult = ywLevelGroupDetailService.getYwLevelGroupDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwLevelGroupDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评分项 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwLevelGroupDetailExcel(@Valid YwLevelGroupDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwLevelGroupDetailDO> list = ywLevelGroupDetailService.getYwLevelGroupDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评分项.xls", "数据", YwLevelGroupDetailRespVO.class,
                        BeanUtils.toBean(list, YwLevelGroupDetailRespVO.class));
    }

}