package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwChapterPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwChapterRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwChapterSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.module.yw.service.YwChapterService;

@Tag(name = "管理后台 - 章节")
@RestController
@RequestMapping("/yw/chapter")
@Validated
public class YwChapterController {

    @Resource
    private YwChapterService chapterService;

    @PostMapping("/create")
    @Operation(summary = "创建章节")
    @PreAuthorize("@ss.hasPermission('yw:chapter:create')")
    public CommonResult<Long> createChapter(@Valid @RequestBody YwChapterSaveReqVO createReqVO) {
        return success(chapterService.createChapter(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新章节")
    @PreAuthorize("@ss.hasPermission('yw:chapter:update')")
    public CommonResult<Boolean> updateChapter(@Valid @RequestBody YwChapterSaveReqVO updateReqVO) {
        chapterService.updateChapter(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除章节")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:chapter:delete')")
    public CommonResult<Boolean> deleteChapter(@RequestParam("id") Long id) {
        chapterService.deleteChapter(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除章节")
                @PreAuthorize("@ss.hasPermission('yw:chapter:delete')")
    public CommonResult<Boolean> deleteChapterList(@RequestParam("ids") List<String> ids) {
        chapterService.deleteChapterListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得章节")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:chapter:query')")
    public CommonResult<YwChapterRespVO> getChapter(@RequestParam("id") String id) {
        YwChapterDO chapter = chapterService.getChapter(id);
        return success(BeanUtils.toBean(chapter, YwChapterRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得章节分页")
    @PreAuthorize("@ss.hasPermission('yw:chapter:query')")
    public CommonResult<PageResult<YwChapterRespVO>> getChapterPage(@Valid YwChapterPageReqVO pageReqVO) {
        PageResult<YwChapterDO> pageResult = chapterService.getChapterPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwChapterRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出章节 Excel")
    @PreAuthorize("@ss.hasPermission('yw:chapter:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportChapterExcel(@Valid YwChapterPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwChapterDO> list = chapterService.getChapterPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "章节.xls", "数据", YwChapterRespVO.class,
                        BeanUtils.toBean(list, YwChapterRespVO.class));
    }

}