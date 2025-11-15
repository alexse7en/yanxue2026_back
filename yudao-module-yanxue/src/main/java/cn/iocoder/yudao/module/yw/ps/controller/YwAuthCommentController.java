package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthCommentRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentSaveReqVO;
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

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthCommentService;

@Tag(name = "管理后台 - 评审结果")
@RestController
@RequestMapping("/yw/ps/yw-auth-comment")
@Validated
public class YwAuthCommentController {

    @Resource
    private YwAuthCommentService ywAuthCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建评审结果")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:create')")
    public CommonResult<Long> createYwAuthComment(@Valid @RequestBody YwAuthCommentSaveReqVO createReqVO) {
        return success(ywAuthCommentService.createYwAuthComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评审结果")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:update')")
    public CommonResult<Boolean> updateYwAuthComment(@Valid @RequestBody YwAuthCommentSaveReqVO updateReqVO) {
        ywAuthCommentService.updateYwAuthComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评审结果")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:delete')")
    public CommonResult<Boolean> deleteYwAuthComment(@RequestParam("id") Long id) {
        ywAuthCommentService.deleteYwAuthComment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评审结果")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:delete')")
    public CommonResult<Boolean> deleteYwAuthCommentList(@RequestParam("ids") List<Long> ids) {
        ywAuthCommentService.deleteYwAuthCommentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评审结果")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:query')")
    public CommonResult<YwAuthCommentRespVO> getYwAuthComment(@RequestParam("id") Long id) {
        YwAuthCommentDO ywAuthComment = ywAuthCommentService.getYwAuthComment(id);
        return success(BeanUtils.toBean(ywAuthComment, YwAuthCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评审结果分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:query')")
    public CommonResult<PageResult<YwAuthCommentRespVO>> getYwAuthCommentPage(@Valid YwAuthCommentPageReqVO pageReqVO) {
        PageResult<YwAuthCommentDO> pageResult = ywAuthCommentService.getYwAuthCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthCommentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评审结果 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-comment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwAuthCommentExcel(@Valid YwAuthCommentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwAuthCommentDO> list = ywAuthCommentService.getYwAuthCommentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评审结果.xls", "数据", YwAuthCommentRespVO.class,
                        BeanUtils.toBean(list, YwAuthCommentRespVO.class));
    }

}