package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuOptionPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwPaperQuOptionRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperQuOptionSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.module.yw.service.YwPaperQuOptionService;

@Tag(name = "管理后台 - 试卷选项")
@RestController
@RequestMapping("/yw/paper-qu-option")
@Validated
public class YwPaperQuOptionController {

    @Resource
    private YwPaperQuOptionService paperQuOptionService;

    @PostMapping("/create")
    @Operation(summary = "创建试卷选项")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:create')")
    public CommonResult<Long> createPaperQuOption(@Valid @RequestBody YwPaperQuOptionSaveReqVO createReqVO) {
        return success(paperQuOptionService.createPaperQuOption(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新试卷选项")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:update')")
    public CommonResult<Boolean> updatePaperQuOption(@Valid @RequestBody YwPaperQuOptionSaveReqVO updateReqVO) {
        paperQuOptionService.updatePaperQuOption(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除试卷选项")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:delete')")
    public CommonResult<Boolean> deletePaperQuOption(@RequestParam("id") Long id) {
        paperQuOptionService.deletePaperQuOption(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试卷选项")
                @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:delete')")
    public CommonResult<Boolean> deletePaperQuOptionList(@RequestParam("ids") List<Long> ids) {
        paperQuOptionService.deletePaperQuOptionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得试卷选项")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:query')")
    public CommonResult<YwPaperQuOptionRespVO> getPaperQuOption(@RequestParam("id") Long id) {
        YwPaperQuOptionDO paperQuOption = paperQuOptionService.getPaperQuOption(id);
        return success(BeanUtils.toBean(paperQuOption, YwPaperQuOptionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得试卷选项分页")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:query')")
    public CommonResult<PageResult<YwPaperQuOptionRespVO>> getPaperQuOptionPage(@Valid YwPaperQuOptionPageReqVO pageReqVO) {
        PageResult<YwPaperQuOptionDO> pageResult = paperQuOptionService.getPaperQuOptionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwPaperQuOptionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出试卷选项 Excel")
    @PreAuthorize("@ss.hasPermission('yw:paper-qu-option:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPaperQuOptionExcel(@Valid YwPaperQuOptionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwPaperQuOptionDO> list = paperQuOptionService.getPaperQuOptionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "试卷选项.xls", "数据", YwPaperQuOptionRespVO.class,
                        BeanUtils.toBean(list, YwPaperQuOptionRespVO.class));
    }

}