package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwMemberCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberCourseRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberCourseSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberCourseDO;
import cn.iocoder.yudao.module.yw.service.YwMemberCourseService;

@Tag(name = "管理后台 - 课程进度")
@RestController
@RequestMapping("/yw/member-course")
@Validated
public class YwMemberCourseController {

    @Resource
    private YwMemberCourseService memberCourseService;

    @PostMapping("/create")
    @Operation(summary = "创建课程进度")
    @PreAuthorize("@ss.hasPermission('yw:member-course:create')")
    public CommonResult<String> createMemberCourse(@Valid @RequestBody YwMemberCourseSaveReqVO createReqVO) {
        return success(memberCourseService.createMemberCourse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新课程进度")
    @PreAuthorize("@ss.hasPermission('yw:member-course:update')")
    public CommonResult<Boolean> updateMemberCourse(@Valid @RequestBody YwMemberCourseSaveReqVO updateReqVO) {
        memberCourseService.updateMemberCourse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程进度")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:member-course:delete')")
    public CommonResult<Boolean> deleteMemberCourse(@RequestParam("id") String id) {
        memberCourseService.deleteMemberCourse(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除课程进度")
                @PreAuthorize("@ss.hasPermission('yw:member-course:delete')")
    public CommonResult<Boolean> deleteMemberCourseList(@RequestParam("ids") List<String> ids) {
        memberCourseService.deleteMemberCourseListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课程进度")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:member-course:query')")
    public CommonResult<YwMemberCourseRespVO> getMemberCourse(@RequestParam("id") String id) {
        YwMemberCourseDO memberCourse = memberCourseService.getMemberCourse(id);
        return success(BeanUtils.toBean(memberCourse, YwMemberCourseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课程进度分页")
    @PreAuthorize("@ss.hasPermission('yw:member-course:query')")
    public CommonResult<PageResult<YwMemberCourseRespVO>> getMemberCoursePage(@Valid YwMemberCoursePageReqVO pageReqVO) {
        PageResult<YwMemberCourseDO> pageResult = memberCourseService.getMemberCoursePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwMemberCourseRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出课程进度 Excel")
    @PreAuthorize("@ss.hasPermission('yw:member-course:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMemberCourseExcel(@Valid YwMemberCoursePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwMemberCourseDO> list = memberCourseService.getMemberCoursePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "课程进度.xls", "数据", YwMemberCourseRespVO.class,
                        BeanUtils.toBean(list, YwMemberCourseRespVO.class));
    }

}