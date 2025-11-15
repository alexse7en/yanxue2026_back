package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwPaperRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.service.YwPaperService;

@Tag(name = "管理后台 - 试卷")
@RestController
@RequestMapping("/yw/paper")
@Validated
public class YwPaperController {

    @Resource
    private YwPaperService paperService;

    @PostMapping("/create")
    @Operation(summary = "创建试卷")
    @PreAuthorize("@ss.hasPermission('yw:paper:create')")
    public CommonResult<Long> createPaper(@Valid @RequestBody YwPaperSaveReqVO createReqVO) {
        return success(paperService.createPaper(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新试卷")
    @PreAuthorize("@ss.hasPermission('yw:paper:update')")
    public CommonResult<Boolean> updatePaper(@Valid @RequestBody YwPaperSaveReqVO updateReqVO) {
        paperService.updatePaper(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除试卷")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:paper:delete')")
    public CommonResult<Boolean> deletePaper(@RequestParam("id") Long id) {
        paperService.deletePaper(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试卷")
                @PreAuthorize("@ss.hasPermission('yw:paper:delete')")
    public CommonResult<Boolean> deletePaperList(@RequestParam("ids") List<Long> ids) {
        paperService.deletePaperListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得试卷")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:paper:query')")
    public CommonResult<YwPaperRespVO> getPaper(@RequestParam("id") Long id) {
        YwPaperDO paper = paperService.getPaper(id);
        return success(BeanUtils.toBean(paper, YwPaperRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得试卷分页")
    @PreAuthorize("@ss.hasPermission('yw:paper:query')")
    public CommonResult<PageResult<YwPaperRespVO>> getPaperPage(@Valid YwPaperPageReqVO pageReqVO) {
        PageResult<YwPaperDO> pageResult = paperService.getPaperPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwPaperRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出试卷 Excel")
    @PreAuthorize("@ss.hasPermission('yw:paper:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPaperExcel(@Valid YwPaperPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwPaperDO> list = paperService.getPaperPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "试卷.xls", "数据", YwPaperRespVO.class,
                        BeanUtils.toBean(list, YwPaperRespVO.class));
    }

    // ==================== 子表（试卷题目） ====================

    @GetMapping("/paper-qu/page")
    @Operation(summary = "获得试卷题目分页")
    @Parameter(name = "paperId", description = "试卷id")
    @PreAuthorize("@ss.hasPermission('yw:paper:query')")
    public CommonResult<PageResult<YwPaperQuDO>> getPaperQuPage(PageParam pageReqVO,
                                                                                        @RequestParam("paperId") Long paperId) {
        return success(paperService.getPaperQuPage(pageReqVO, paperId));
    }

    @PostMapping("/paper-qu/create")
    @Operation(summary = "创建试卷题目")
    @PreAuthorize("@ss.hasPermission('yw:paper:create')")
    public CommonResult<Long> createPaperQu(@Valid @RequestBody YwPaperQuDO paperQu) {
        return success(paperService.createPaperQu(paperQu));
    }

    @PutMapping("/paper-qu/update")
    @Operation(summary = "更新试卷题目")
    @PreAuthorize("@ss.hasPermission('yw:paper:update')")
    public CommonResult<Boolean> updatePaperQu(@Valid @RequestBody YwPaperQuDO paperQu) {
        paperService.updatePaperQu(paperQu);
        return success(true);
    }

    @DeleteMapping("/paper-qu/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除试卷题目")
    @PreAuthorize("@ss.hasPermission('yw:paper:delete')")
    public CommonResult<Boolean> deletePaperQu(@RequestParam("id") Long id) {
        paperService.deletePaperQu(id);
        return success(true);
    }

    @DeleteMapping("/paper-qu/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试卷题目")
    @PreAuthorize("@ss.hasPermission('yw:paper:delete')")
    public CommonResult<Boolean> deletePaperQuList(@RequestParam("ids") List<Long> ids) {
        paperService.deletePaperQuListByIds(ids);
        return success(true);
    }

	@GetMapping("/paper-qu/get")
	@Operation(summary = "获得试卷题目")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:paper:query')")
	public CommonResult<YwPaperQuDO> getPaperQu(@RequestParam("id") Long id) {
	    return success(paperService.getPaperQu(id));
	}


    @PostMapping("/beginPaper")
    @Operation(summary = "开始考试")
    public CommonResult<YwPaperEntity> beginPaper(@RequestParam("courseId") Long courseId, @RequestParam("examId") Long examId) {
        return success(paperService.beginPaper(courseId, examId));
    }

    @PostMapping("/updatePaperQu")
    @Operation(summary = "答题")
    public CommonResult<Boolean> updatePaperQu(@RequestBody YwPaperQuEntity entity) {
        return success(paperService.submitPaperQu(entity));
    }

    @PostMapping("/submit")
    @Operation(summary = "交卷")
    public CommonResult<YwPaperDO> submit(@RequestParam("paperId") Long paperId) {
        return success(paperService.submit(paperId));
    }
}