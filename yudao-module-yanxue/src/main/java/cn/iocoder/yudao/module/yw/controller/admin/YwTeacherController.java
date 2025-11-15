package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwTeacherPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwTeacherRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwTeacherSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwTeacherDO;
import cn.iocoder.yudao.module.yw.service.YwTeacherService;

@Tag(name = "管理后台 - 教师")
@RestController
@RequestMapping("/yw/teacher")
@Validated
public class YwTeacherController {

    @Resource
    private YwTeacherService teacherService;

    @PostMapping("/create")
    @Operation(summary = "创建教师")
    @PreAuthorize("@ss.hasPermission('yw:teacher:create')")
    public CommonResult<String> createTeacher(@Valid @RequestBody YwTeacherSaveReqVO createReqVO) {
        return success(teacherService.createTeacher(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新教师")
    @PreAuthorize("@ss.hasPermission('yw:teacher:update')")
    public CommonResult<Boolean> updateTeacher(@Valid @RequestBody YwTeacherSaveReqVO updateReqVO) {
        teacherService.updateTeacher(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除教师")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:teacher:delete')")
    public CommonResult<Boolean> deleteTeacher(@RequestParam("id") String id) {
        teacherService.deleteTeacher(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除教师")
                @PreAuthorize("@ss.hasPermission('yw:teacher:delete')")
    public CommonResult<Boolean> deleteTeacherList(@RequestParam("ids") List<String> ids) {
        teacherService.deleteTeacherListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得教师")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:teacher:query')")
    public CommonResult<YwTeacherRespVO> getTeacher(@RequestParam("id") String id) {
        YwTeacherDO teacher = teacherService.getTeacher(id);
        return success(BeanUtils.toBean(teacher, YwTeacherRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得教师分页")
    @PreAuthorize("@ss.hasPermission('yw:teacher:query')")
    public CommonResult<PageResult<YwTeacherRespVO>> getTeacherPage(@Valid YwTeacherPageReqVO pageReqVO) {
        PageResult<YwTeacherDO> pageResult = teacherService.getTeacherPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwTeacherRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出教师 Excel")
    @PreAuthorize("@ss.hasPermission('yw:teacher:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTeacherExcel(@Valid YwTeacherPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwTeacherDO> list = teacherService.getTeacherPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "教师.xls", "数据", YwTeacherRespVO.class,
                        BeanUtils.toBean(list, YwTeacherRespVO.class));
    }

}