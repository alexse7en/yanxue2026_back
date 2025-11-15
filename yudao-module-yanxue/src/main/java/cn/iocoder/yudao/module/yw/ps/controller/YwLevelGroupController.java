package cn.iocoder.yudao.module.yw.ps.controller;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwLevelGroupRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDO;
import cn.iocoder.yudao.module.yw.ps.service.YwLevelGroupService;

@Tag(name = "管理后台 - 评审种类")
@RestController
@RequestMapping("/yw/ps/yw-level-group")
@Validated
public class YwLevelGroupController {

    @Resource
    private YwLevelGroupService ywLevelGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建评审种类")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:create')")
    public CommonResult<Long> createYwLevelGroup(@Valid @RequestBody YwLevelGroupSaveReqVO createReqVO) {
        return success(ywLevelGroupService.createYwLevelGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评审种类")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:update')")
    public CommonResult<Boolean> updateYwLevelGroup(@Valid @RequestBody YwLevelGroupSaveReqVO updateReqVO) {
        ywLevelGroupService.updateYwLevelGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评审种类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:delete')")
    public CommonResult<Boolean> deleteYwLevelGroup(@RequestParam("id") Long id) {
        ywLevelGroupService.deleteYwLevelGroup(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除评审种类")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:delete')")
    public CommonResult<Boolean> deleteYwLevelGroupList(@RequestParam("ids") List<Long> ids) {
        ywLevelGroupService.deleteYwLevelGroupListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评审种类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:query')")
    public CommonResult<YwLevelGroupRespVO> getYwLevelGroup(@RequestParam("id") Long id) {
        YwLevelGroupDO ywLevelGroup = ywLevelGroupService.getYwLevelGroup(id);
        return success(BeanUtils.toBean(ywLevelGroup, YwLevelGroupRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评审种类分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:query')")
    public CommonResult<PageResult<YwLevelGroupRespVO>> getYwLevelGroupPage(@Valid YwLevelGroupPageReqVO pageReqVO) {
        PageResult<YwLevelGroupDO> pageResult = ywLevelGroupService.getYwLevelGroupPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwLevelGroupRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评审种类 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-level-group:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwLevelGroupExcel(@Valid YwLevelGroupPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwLevelGroupDO> list = ywLevelGroupService.getYwLevelGroupPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评审种类.xls", "数据", YwLevelGroupRespVO.class,
                        BeanUtils.toBean(list, YwLevelGroupRespVO.class));
    }

}