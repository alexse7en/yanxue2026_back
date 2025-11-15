package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwQuPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwQuRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwQuSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.module.yw.service.YwQuService;

@Tag(name = "管理后台 - 试题")
@RestController
@RequestMapping("/yw/qu")
@Validated
public class YwQuController {

    @Resource
    private YwQuService quService;

    @PostMapping("/create")
    @Operation(summary = "创建试题")
    @PreAuthorize("@ss.hasPermission('yw:qu:create')")
    public CommonResult<Long> createQu(@Valid @RequestBody YwQuSaveReqVO createReqVO) {
        return success(quService.createQu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新试题")
    @PreAuthorize("@ss.hasPermission('yw:qu:update')")
    public CommonResult<Boolean> updateQu(@Valid @RequestBody YwQuSaveReqVO updateReqVO) {
        quService.updateQu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除试题")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:qu:delete')")
    public CommonResult<Boolean> deleteQu(@RequestParam("id") Long id) {
        quService.deleteQu(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试题")
                @PreAuthorize("@ss.hasPermission('yw:qu:delete')")
    public CommonResult<Boolean> deleteQuList(@RequestParam("ids") List<Long> ids) {
        quService.deleteQuListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得试题")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:qu:query')")
    public CommonResult<YwQuRespVO> getQu(@RequestParam("id") Long id) {
        YwQuDO qu = quService.getQu(id);
        return success(BeanUtils.toBean(qu, YwQuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得试题分页")
    @PreAuthorize("@ss.hasPermission('yw:qu:query')")
    public CommonResult<PageResult<YwQuRespVO>> getQuPage(@Valid YwQuPageReqVO pageReqVO) {
        PageResult<YwQuDO> pageResult = quService.getQuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwQuRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出试题 Excel")
    @PreAuthorize("@ss.hasPermission('yw:qu:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQuExcel(@Valid YwQuPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwQuDO> list = quService.getQuPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "试题.xls", "数据", YwQuRespVO.class,
                        BeanUtils.toBean(list, YwQuRespVO.class));
    }

    // ==================== 子表（试题选项） ====================

    @GetMapping("/qu-option/page")
    @Operation(summary = "获得试题选项分页")
    @Parameter(name = "quId", description = "试题id")
    @PreAuthorize("@ss.hasPermission('yw:qu:query')")
    public CommonResult<PageResult<YwQuOptionDO>> getQuOptionPage(PageParam pageReqVO,
                                                                                        @RequestParam("quId") Long quId) {
        return success(quService.getQuOptionPage(pageReqVO, quId));
    }

    @PostMapping("/qu-option/create")
    @Operation(summary = "创建试题选项")
    @PreAuthorize("@ss.hasPermission('yw:qu:create')")
    public CommonResult<Long> createQuOption(@Valid @RequestBody YwQuOptionDO quOption) {
        return success(quService.createQuOption(quOption));
    }

    @PutMapping("/qu-option/update")
    @Operation(summary = "更新试题选项")
    @PreAuthorize("@ss.hasPermission('yw:qu:update')")
    public CommonResult<Boolean> updateQuOption(@Valid @RequestBody YwQuOptionDO quOption) {
        quService.updateQuOption(quOption);
        return success(true);
    }

    @DeleteMapping("/qu-option/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除试题选项")
    @PreAuthorize("@ss.hasPermission('yw:qu:delete')")
    public CommonResult<Boolean> deleteQuOption(@RequestParam("id") Long id) {
        quService.deleteQuOption(id);
        return success(true);
    }

    @DeleteMapping("/qu-option/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试题选项")
    @PreAuthorize("@ss.hasPermission('yw:qu:delete')")
    public CommonResult<Boolean> deleteQuOptionList(@RequestParam("ids") List<Long> ids) {
        quService.deleteQuOptionListByIds(ids);
        return success(true);
    }

	@GetMapping("/qu-option/get")
	@Operation(summary = "获得试题选项")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:qu:query')")
	public CommonResult<YwQuOptionDO> getQuOption(@RequestParam("id") Long id) {
	    return success(quService.getQuOption(id));
	}

}