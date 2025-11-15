package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.vo.page.YwCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwCourseRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.module.yw.service.YwCourseService;

@Tag(name = "管理后台 - 课程")
@RestController
@RequestMapping("/yw/course")
@Validated
public class YwCourseController {

    @Resource
    private YwCourseService courseService;

    @PostMapping("/create")
    @Operation(summary = "创建课程")
    @PreAuthorize("@ss.hasPermission('yw:course:create')")
    public CommonResult<Long> createCourse(@Valid @RequestBody YwCourseSaveReqVO createReqVO) {
        return success(courseService.createCourse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新课程")
    @PreAuthorize("@ss.hasPermission('yw:course:update')")
    public CommonResult<Boolean> updateCourse(@Valid @RequestBody YwCourseSaveReqVO updateReqVO) {
        courseService.updateCourse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:course:delete')")
    public CommonResult<Boolean> deleteCourse(@RequestParam("id") Long id) {
        courseService.deleteCourse(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除课程")
                @PreAuthorize("@ss.hasPermission('yw:course:delete')")
    public CommonResult<Boolean> deleteCourseList(@RequestParam("ids") List<Long> ids) {
        courseService.deleteCourseListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:course:query')")
    public CommonResult<YwCourseRespVO> getCourse(@RequestParam("id") Long id) {
        YwCourseDO course = courseService.getCourse(id);
        return success(BeanUtils.toBean(course, YwCourseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课程分页")
    @PreAuthorize("@ss.hasPermission('yw:course:query')")
    public CommonResult<PageResult<YwCourseRespVO>> getCoursePage(@Valid YwCoursePageReqVO pageReqVO) {
        PageResult<YwCourseDO> pageResult = courseService.getCoursePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwCourseRespVO.class));
    }

    @GetMapping("/selectCourse")
    @Operation(summary = "获得课程分页")
    @PreAuthorize("@ss.hasPermission('yw:course:query')")
    public CommonResult<List<YwCourseMemberVO>> selectCourse() {
        List<YwCourseMemberVO> pageResult = courseService.selectCourse();
        return success(BeanUtils.toBean(pageResult, YwCourseMemberVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出课程 Excel")
    @PreAuthorize("@ss.hasPermission('yw:course:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCourseExcel(@Valid YwCoursePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwCourseDO> list = courseService.getCoursePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "课程.xls", "数据", YwCourseRespVO.class,
                        BeanUtils.toBean(list, YwCourseRespVO.class));
    }

    // ==================== 子表（章节） ====================

    @GetMapping("/chapter/page")
    @Operation(summary = "获得章节分页")
    @Parameter(name = "courseId", description = "课程id")
    @PreAuthorize("@ss.hasPermission('yw:course:query')")
    public CommonResult<PageResult<YwChapterDO>> getChapterPage(PageParam pageReqVO,
                                                                                        @RequestParam("courseId") Long courseId) {
        return success(courseService.getChapterPage(pageReqVO, courseId));
    }

    @PostMapping("/chapter/create")
    @Operation(summary = "创建章节")
    @PreAuthorize("@ss.hasPermission('yw:course:create')")
    public CommonResult<Long> createChapter(@Valid @RequestBody YwChapterDO chapter) {
        return success(courseService.createChapter(chapter));
    }

    @PutMapping("/chapter/update")
    @Operation(summary = "更新章节")
    @PreAuthorize("@ss.hasPermission('yw:course:update')")
    public CommonResult<Boolean> updateChapter(@Valid @RequestBody YwChapterDO chapter) {
        courseService.updateChapter(chapter);
        return success(true);
    }

    @DeleteMapping("/chapter/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除章节")
    @PreAuthorize("@ss.hasPermission('yw:course:delete')")
    public CommonResult<Boolean> deleteChapter(@RequestParam("id") Long id) {
        courseService.deleteChapter(id);
        return success(true);
    }

    @DeleteMapping("/chapter/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除章节")
    @PreAuthorize("@ss.hasPermission('yw:course:delete')")
    public CommonResult<Boolean> deleteChapterList(@RequestParam("ids") List<Long> ids) {
        courseService.deleteChapterListByIds(ids);
        return success(true);
    }

	@GetMapping("/chapter/get")
	@Operation(summary = "获得章节")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:course:query')")
	public CommonResult<YwChapterDO> getChapter(@RequestParam("id") Long id) {
	    return success(courseService.getChapter(id));
	}

    @PostMapping("/beginCourseMember")
    @Operation(summary = "开始课程")
    public CommonResult<YwCourseMemberVO> beginCourseMember(@RequestParam("courseId") Long courseId) {
        return success(courseService.beginCourse(courseId));
    }

    @PostMapping("/updateChapterMember")
    @Operation(summary = "答题")
    public CommonResult<Integer> updateChapterMember(@RequestParam("chapterId") Long chapterId) {
        return success(courseService.updateChapterMember(chapterId));
    }

}