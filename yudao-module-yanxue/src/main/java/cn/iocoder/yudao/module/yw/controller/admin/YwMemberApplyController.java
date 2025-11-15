package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.service.YwMemberApplyService;
import cn.iocoder.yudao.module.yw.vo.YwMemberApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.YwMemberApplyBatchAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwOrgApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberApplySaveReqVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
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


import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO;


@Tag(name = "管理后台 - 组织-志愿者挂靠审核")
@RestController
@RequestMapping("/yw/yw-member-apply")
@Validated
public class YwMemberApplyController {

    @Resource
    private YwMemberApplyService ywMemberApplyService;

    @PostMapping("/create")
    @Operation(summary = "创建组织-志愿者挂靠审核")
  //  @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:create')")
    public CommonResult<Long> createYwMemberApply(@Valid @RequestBody YwMemberApplySaveReqVO createReqVO) {
        return success(ywMemberApplyService.createYwMemberApply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新组织-志愿者挂靠审核")
   // @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:update')")
    public CommonResult<Boolean> updateYwMemberApply(@Valid @RequestBody YwMemberApplySaveReqVO updateReqVO) {
        ywMemberApplyService.updateYwMemberApply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除组织-志愿者挂靠审核")
    @Parameter(name = "id", description = "编号", required = true)
  //  @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:delete')")
    public CommonResult<Boolean> deleteYwMemberApply(@RequestParam("id") Long id) {
        ywMemberApplyService.deleteYwMemberApply(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
 //   @Operation(summary = "批量删除组织-志愿者挂靠审核")
                @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:delete')")
    public CommonResult<Boolean> deleteYwMemberApplyList(@RequestParam("ids") List<Long> ids) {
        ywMemberApplyService.deleteYwMemberApplyListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得组织-志愿者挂靠审核")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
 //   @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:query')")
    public CommonResult<YwMemberApplyRespVO> getYwMemberApply(@RequestParam("id") Long id) {
        YwMemberApplyDO ywMemberApply = ywMemberApplyService.getYwMemberApply(id);
        return success(BeanUtils.toBean(ywMemberApply, YwMemberApplyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得组织-志愿者挂靠审核分页")
 //   @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:query')")
    public CommonResult<PageResult<YwMemberApplyRespVO>> getYwMemberApplyPage(@Valid YwMemberApplyPageReqVO pageReqVO) {
        PageResult<YwMemberApplyRespVO> pageResult = ywMemberApplyService.getYwMemberApplyPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwMemberApplyRespVO.class));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核单个挂靠申请")
    public CommonResult<Boolean> auditYwMemberApply(@RequestBody @Valid YwMemberApplyAuditReqVO reqVO) {
        ywMemberApplyService.auditYwMemberApply(reqVO);
        return success(true);
    }

    @PostMapping("/batch-audit")
    @Operation(summary = "批量审核挂靠申请")
    public CommonResult<Boolean> batchAuditYwMemberApply(@RequestBody @Valid YwMemberApplyBatchAuditReqVO reqVO) {
        ywMemberApplyService.batchAuditYwMemberApply(reqVO);
        return success(true);
    }




    /*@GetMapping("/export-excel")
    @Operation(summary = "导出组织-志愿者挂靠审核 Excel")
    @PreAuthorize("@ss.hasPermission('yw:yw-member-apply:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwMemberApplyExcel(@Valid YwMemberApplyPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwMemberApplyDO> list = ywMemberApplyService.getYwMemberApplyPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "组织-志愿者挂靠审核.xls", "数据", YwMemberApplyRespVO.class,
                        BeanUtils.toBean(list, YwMemberApplyRespVO.class));
    }*/



}
