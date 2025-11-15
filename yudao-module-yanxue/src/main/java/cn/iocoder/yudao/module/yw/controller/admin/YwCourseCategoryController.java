package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwCourseCategoryPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwCourseCategoryRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseCategorySaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseCategoryDO;
import cn.iocoder.yudao.module.yw.service.YwCourseCategoryService;

@Tag(name = "管理后台 - 课程分类")
@RestController
@RequestMapping("/yw/course-category")
@Validated
public class YwCourseCategoryController {

    @Resource
    private YwCourseCategoryService courseCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建课程分类")
    @PreAuthorize("@ss.hasPermission('yw:course-category:create')")
    public CommonResult<String> createCourseCategory(@Valid @RequestBody YwCourseCategorySaveReqVO createReqVO) {
        return success(courseCategoryService.createCourseCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新课程分类")
    @PreAuthorize("@ss.hasPermission('yw:course-category:update')")
    public CommonResult<Boolean> updateCourseCategory(@Valid @RequestBody YwCourseCategorySaveReqVO updateReqVO) {
        courseCategoryService.updateCourseCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:course-category:delete')")
    public CommonResult<Boolean> deleteCourseCategory(@RequestParam("id") String id) {
        courseCategoryService.deleteCourseCategory(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除课程分类")
                @PreAuthorize("@ss.hasPermission('yw:course-category:delete')")
    public CommonResult<Boolean> deleteCourseCategoryList(@RequestParam("ids") List<String> ids) {
        courseCategoryService.deleteCourseCategoryListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课程分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:course-category:query')")
    public CommonResult<YwCourseCategoryRespVO> getCourseCategory(@RequestParam("id") String id) {
        YwCourseCategoryDO courseCategory = courseCategoryService.getCourseCategory(id);
        return success(BeanUtils.toBean(courseCategory, YwCourseCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课程分类分页")
    @PreAuthorize("@ss.hasPermission('yw:course-category:query')")
    public CommonResult<PageResult<YwCourseCategoryRespVO>> getCourseCategoryPage(@Valid YwCourseCategoryPageReqVO pageReqVO) {
        PageResult<YwCourseCategoryDO> pageResult = courseCategoryService.getCourseCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwCourseCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出课程分类 Excel")
    @PreAuthorize("@ss.hasPermission('yw:course-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCourseCategoryExcel(@Valid YwCourseCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwCourseCategoryDO> list = courseCategoryService.getCourseCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "课程分类.xls", "数据", YwCourseCategoryRespVO.class,
                        BeanUtils.toBean(list, YwCourseCategoryRespVO.class));
    }

}