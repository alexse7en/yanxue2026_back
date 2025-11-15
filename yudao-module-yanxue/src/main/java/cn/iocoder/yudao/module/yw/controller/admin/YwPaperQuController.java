package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwPaperQuRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperQuSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.module.yw.service.YwPaperQuService;

@Tag(name = "管理后台 - 试卷题目")
@RestController
@RequestMapping("/yw/paper-qu")
@Validated
public class YwPaperQuController {

    @Resource
    private YwPaperQuService paperQuService;

    @PostMapping("/create")
    @Operation(summary = "创建试卷题目")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:create')")
    public CommonResult<Long> createPaperQu(@Valid @RequestBody YwPaperQuSaveReqVO createReqVO) {
        return success(paperQuService.createPaperQu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新试卷题目")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:update')")
    public CommonResult<Boolean> updatePaperQu(@Valid @RequestBody YwPaperQuSaveReqVO updateReqVO) {
        paperQuService.updatePaperQu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除试卷题目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:delete')")
    public CommonResult<Boolean> deletePaperQu(@RequestParam("id") Long id) {
        paperQuService.deletePaperQu(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试卷题目")
                @PreAuthorize("@ss.hasPermission('yw:paper-qu:delete')")
    public CommonResult<Boolean> deletePaperQuList(@RequestParam("ids") List<Long> ids) {
        paperQuService.deletePaperQuListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得试卷题目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:query')")
    public CommonResult<YwPaperQuRespVO> getPaperQu(@RequestParam("id") Long id) {
        YwPaperQuDO paperQu = paperQuService.getPaperQu(id);
        return success(BeanUtils.toBean(paperQu, YwPaperQuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得试卷题目分页")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:query')")
    public CommonResult<PageResult<YwPaperQuRespVO>> getPaperQuPage(@Valid YwPaperQuPageReqVO pageReqVO) {
        PageResult<YwPaperQuDO> pageResult = paperQuService.getPaperQuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwPaperQuRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出试卷题目 Excel")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPaperQuExcel(@Valid YwPaperQuPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwPaperQuDO> list = paperQuService.getPaperQuPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "试卷题目.xls", "数据", YwPaperQuRespVO.class,
                        BeanUtils.toBean(list, YwPaperQuRespVO.class));
    }

    // ==================== 子表（试卷选项） ====================

    @GetMapping("/paper-qu-option/page")
    @Operation(summary = "获得试卷选项分页")
    @Parameter(name = "quId", description = "考题id")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:query')")
    public CommonResult<PageResult<YwPaperQuOptionDO>> getPaperQuOptionPage(PageParam pageReqVO,
                                                                                        @RequestParam("quId") Long quId) {
        return success(paperQuService.getPaperQuOptionPage(pageReqVO, quId));
    }

    @PostMapping("/paper-qu-option/create")
    @Operation(summary = "创建试卷选项")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:create')")
    public CommonResult<Long> createPaperQuOption(@Valid @RequestBody YwPaperQuOptionDO paperQuOption) {
        return success(paperQuService.createPaperQuOption(paperQuOption));
    }

    @PutMapping("/paper-qu-option/update")
    @Operation(summary = "更新试卷选项")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:update')")
    public CommonResult<Boolean> updatePaperQuOption(@Valid @RequestBody YwPaperQuOptionDO paperQuOption) {
        paperQuService.updatePaperQuOption(paperQuOption);
        return success(true);
    }

    @DeleteMapping("/paper-qu-option/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除试卷选项")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:delete')")
    public CommonResult<Boolean> deletePaperQuOption(@RequestParam("id") Long id) {
        paperQuService.deletePaperQuOption(id);
        return success(true);
    }

    @DeleteMapping("/paper-qu-option/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试卷选项")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:delete')")
    public CommonResult<Boolean> deletePaperQuOptionList(@RequestParam("ids") List<Long> ids) {
        paperQuService.deletePaperQuOptionListByIds(ids);
        return success(true);
    }

	@GetMapping("/paper-qu-option/get")
	@Operation(summary = "获得试卷选项")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:paper-qu:query')")
	public CommonResult<YwPaperQuOptionDO> getPaperQuOption(@RequestParam("id") Long id) {
	    return success(paperQuService.getPaperQuOption(id));
	}

}