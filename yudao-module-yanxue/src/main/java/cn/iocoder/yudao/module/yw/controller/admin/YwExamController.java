package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwExamPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwExamRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwExamSaveReqVO;
import cn.iocoder.yudao.module.yw.vo.YwQuTotalVo;
import io.swagger.v3.oas.annotations.Parameters;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.service.YwExamService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 考卷设计")
@RestController
@RequestMapping("/yw/exam")
@Validated
public class YwExamController {

    @Resource
    private YwExamService examService;

    @PostMapping("/create")
    @Operation(summary = "创建考卷设计")
    @PreAuthorize("@ss.hasPermission('yw:exam:create')")
    public CommonResult<Long> createExam(@Valid @RequestBody YwExamSaveReqVO createReqVO) {
        return success(examService.createExam(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新考卷设计")
    @PreAuthorize("@ss.hasPermission('yw:exam:update')")
    public CommonResult<Boolean> updateExam(@Valid @RequestBody YwExamSaveReqVO updateReqVO) {
        examService.updateExam(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除考卷设计")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:exam:delete')")
    public CommonResult<Boolean> deleteExam(@RequestParam("id") Long id) {
        examService.deleteExam(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除考卷设计")
                @PreAuthorize("@ss.hasPermission('yw:exam:delete')")
    public CommonResult<Boolean> deleteExamList(@RequestParam("ids") List<Long> ids) {
        examService.deleteExamListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得考卷设计")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:exam:query')")
    public CommonResult<YwExamRespVO> getExam(@RequestParam("id") Long id) {
        YwExamDO exam = examService.getExam(id);
        return success(BeanUtils.toBean(exam, YwExamRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得考卷设计分页")
    @PreAuthorize("@ss.hasPermission('yw:exam:query')")
    public CommonResult<PageResult<YwExamRespVO>> getExamPage(@Valid YwExamPageReqVO pageReqVO) {
        PageResult<YwExamDO> pageResult = examService.getExamPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwExamRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出考卷设计 Excel")
    @PreAuthorize("@ss.hasPermission('yw:exam:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExamExcel(@Valid YwExamPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwExamDO> list = examService.getExamPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "考卷设计.xls", "数据", YwExamRespVO.class,
                        BeanUtils.toBean(list, YwExamRespVO.class));
    }

    // ==================== 子表（试题） ====================

    @GetMapping("/qu/page")
    @Operation(summary = "获得试题分页")
    @Parameter(name = "quType", description = "题目类型")
    @PreAuthorize("@ss.hasPermission('yw:exam:query')")
    public CommonResult<PageResult<YwQuDO>> getQuPage(PageParam pageReqVO,
                                                                                        @RequestParam("examId") Long examId) {
        return success(examService.getQuPage(pageReqVO, examId));
    }

    @PostMapping("/qu/create")
    @Operation(summary = "创建试题")
    @PreAuthorize("@ss.hasPermission('yw:exam:create')")
    public CommonResult<Long> createQu(@Valid @RequestBody YwQuDO qu) {
        return success(examService.createQu(qu));
    }

    @PutMapping("/qu/update")
    @Operation(summary = "更新试题")
    @PreAuthorize("@ss.hasPermission('yw:exam:update')")
    public CommonResult<Boolean> updateQu(@Valid @RequestBody YwQuDO qu) {
        examService.updateQu(qu);
        return success(true);
    }

    @DeleteMapping("/qu/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除试题")
    @PreAuthorize("@ss.hasPermission('yw:exam:delete')")
    public CommonResult<Boolean> deleteQu(@RequestParam("id") Long id) {
        examService.deleteQu(id);
        return success(true);
    }

    @DeleteMapping("/qu/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试题")
    @PreAuthorize("@ss.hasPermission('yw:exam:delete')")
    public CommonResult<Boolean> deleteQuList(@RequestParam("ids") List<Long> ids) {
        examService.deleteQuListByIds(ids);
        return success(true);
    }

	@GetMapping("/qu/get")
	@Operation(summary = "获得试题")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:exam:query')")
	public CommonResult<YwQuDO> getQu(@RequestParam("id") Long id) {
	    return success(examService.getQu(id));
	}


    @PostMapping("/import")
    @Operation(summary = "导入试卷")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "exam", description = "试卷", example = "true")
    })
    public CommonResult<Boolean> importExcel(@RequestPart("file") MultipartFile file,
                                                      @RequestPart("exam") YwExamDO examDO) throws Exception {
        List<YwQuTotalVo> list = ExcelUtils.read(file, YwQuTotalVo.class, 2);// 自动关闭流

        return success(examService.importExam(list, examDO));
    }
}