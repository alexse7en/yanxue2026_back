package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwStudyBasePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwStudyBaseRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwStudyBaseSaveReqVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
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

import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwStudyBaseDO;
import cn.iocoder.yudao.module.yw.service.YwStudyBaseService;

@Tag(name = "管理后台 - 研学基地/营地")
@RestController
@RequestMapping("/yw/study-base")
@Validated
public class YwStudyBaseController {

    @Resource
    private YwStudyBaseService studyBaseService;

    @PostMapping("/create")
    @Operation(summary = "创建研学基地/营地")
    @PreAuthorize("@ss.hasPermission('yw:study-base:create')")
    public CommonResult<Long> createStudyBase(@Valid @RequestBody YwStudyBaseSaveReqVO createReqVO) {
        return success(studyBaseService.createStudyBase(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新研学基地/营地")
    @PreAuthorize("@ss.hasPermission('yw:study-base:update')")
    public CommonResult<Boolean> updateStudyBase(@Valid @RequestBody YwStudyBaseSaveReqVO updateReqVO) {
        studyBaseService.updateStudyBase(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除研学基地/营地")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:study-base:delete')")
    public CommonResult<Boolean> deleteStudyBase(@RequestParam("id") Long id) {
        studyBaseService.deleteStudyBase(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除研学基地/营地")
                @PreAuthorize("@ss.hasPermission('yw:study-base:delete')")
    public CommonResult<Boolean> deleteStudyBaseList(@RequestParam("ids") List<Long> ids) {
        studyBaseService.deleteStudyBaseListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得研学基地/营地")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:study-base:query')")
    public CommonResult<YwStudyBaseRespVO> getStudyBase(@RequestParam("id") Long id) {
        YwStudyBaseDO studyBase = studyBaseService.getStudyBase(id);
        return success(BeanUtils.toBean(studyBase, YwStudyBaseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得研学基地/营地分页")
    @PreAuthorize("@ss.hasPermission('yw:study-base:query')")
    public CommonResult<PageResult<YwStudyBaseRespVO>> getStudyBasePage(@Valid YwStudyBasePageReqVO pageReqVO) {
        PageResult<YwStudyBaseDO> pageResult = studyBaseService.getStudyBasePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwStudyBaseRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出研学基地/营地 Excel")
    @PreAuthorize("@ss.hasPermission('yw:study-base:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStudyBaseExcel(@Valid YwStudyBasePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwStudyBaseDO> list = studyBaseService.getStudyBasePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "研学基地/营地.xls", "数据", YwStudyBaseRespVO.class,
                        BeanUtils.toBean(list, YwStudyBaseRespVO.class));
    }

}