package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentStatusPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthCommentStatusRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentStatusSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentStatusDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthCommentStatusService;

@Tag(name = "管理后台 - 专家评审状态")
@RestController
@RequestMapping("/yw/ps/yw-auth-comment-status")
@Validated
public class YwAuthCommentStatusController {

    @Resource
    private YwAuthCommentStatusService ywAuthCommentStatusService;

    @PostMapping("/create")
    @Operation(summary = "创建专家评审状态")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:create')")
    public CommonResult<Long> createYwAuthCommentStatus(@Valid @RequestBody YwAuthCommentStatusSaveReqVO createReqVO) {
        return success(ywAuthCommentStatusService.createYwAuthCommentStatus(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新专家评审状态")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:update')")
    public CommonResult<Boolean> updateYwAuthCommentStatus(@Valid @RequestBody YwAuthCommentStatusSaveReqVO updateReqVO) {
        ywAuthCommentStatusService.updateYwAuthCommentStatus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除专家评审状态")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:delete')")
    public CommonResult<Boolean> deleteYwAuthCommentStatus(@RequestParam("id") Long id) {
        ywAuthCommentStatusService.deleteYwAuthCommentStatus(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除专家评审状态")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:delete')")
    public CommonResult<Boolean> deleteYwAuthCommentStatusList(@RequestParam("ids") List<Long> ids) {
        ywAuthCommentStatusService.deleteYwAuthCommentStatusListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得专家评审状态")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:query')")
    public CommonResult<YwAuthCommentStatusRespVO> getYwAuthCommentStatus(@RequestParam("id") Long id) {
        YwAuthCommentStatusDO ywAuthCommentStatus = ywAuthCommentStatusService.getYwAuthCommentStatus(id);
        return success(BeanUtils.toBean(ywAuthCommentStatus, YwAuthCommentStatusRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得专家评审状态分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:query')")
    public CommonResult<PageResult<YwAuthCommentStatusRespVO>> getYwAuthCommentStatusPage(@Valid YwAuthCommentStatusPageReqVO pageReqVO) {
        PageResult<YwAuthCommentStatusDO> pageResult = ywAuthCommentStatusService.getYwAuthCommentStatusPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthCommentStatusRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出专家评审状态 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment-status:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwAuthCommentStatusExcel(@Valid YwAuthCommentStatusPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwAuthCommentStatusDO> list = ywAuthCommentStatusService.getYwAuthCommentStatusPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "专家评审状态.xls", "数据", YwAuthCommentStatusRespVO.class,
                        BeanUtils.toBean(list, YwAuthCommentStatusRespVO.class));
    }

}