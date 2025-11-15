package cn.iocoder.yudao.module.yw.ps.controller.admin;

import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelMemberVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelTotalVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthLevelRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthLevelSaveReqVO;
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


import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthLevelDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthLevelService;

@Tag(name = "管理后台 - 评审申请")
@RestController
@RequestMapping("/yw/ps/ywAuthLevel")
@Validated
public class YwAuthLevelController {

    @Resource
    private YwAuthLevelService ywAuthLevelService;

    @PostMapping("/create")
    @Operation(summary = "创建评审申请")
    //@PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:create')")
    public CommonResult<Long> createYwAuthLevel(@Valid @RequestBody YwAuthLevelSaveReqVO createReqVO) {
        return success(ywAuthLevelService.createYwAuthLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评审申请")
    //@PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:update')")
    public CommonResult<Boolean> updateYwAuthLevel(@Valid @RequestBody YwAuthLevelSaveReqVO updateReqVO) {
        ywAuthLevelService.updateYwAuthLevel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评审申请")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:delete')")
    public CommonResult<Boolean> deleteYwAuthLevel(@RequestParam("id") Long id) {
        ywAuthLevelService.deleteYwAuthLevel(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    //@Operation(summary = "批量删除评审申请")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:delete')")
    public CommonResult<Boolean> deleteYwAuthLevelList(@RequestParam("ids") List<Long> ids) {
        ywAuthLevelService.deleteYwAuthLevelListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评审申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:query')")
    public CommonResult<YwAuthLevelRespVO> getYwAuthLevel(@RequestParam("id") Long id) {
        YwAuthLevelDO ywAuthLevel = ywAuthLevelService.getYwAuthLevel(id);
        return success(BeanUtils.toBean(ywAuthLevel, YwAuthLevelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评审申请分页")
    //@PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:query')")
    public CommonResult<PageResult<YwAuthLevelRespVO>> getYwAuthLevelPage(@Valid YwAuthLevelPageReqVO pageReqVO) {
        PageResult<YwAuthLevelRespVO> pageResult = ywAuthLevelService.getYwAuthLevelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthLevelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评审申请 Excel")
    //@PreAuthorize("@ss.hasPermission('yw.ps:yw-auth-level:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwAuthLevelExcel(@Valid YwAuthLevelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwAuthLevelRespVO> list = ywAuthLevelService.getYwAuthLevelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评审申请.xls", "数据", YwAuthLevelRespVO.class,
                        BeanUtils.toBean(list, YwAuthLevelRespVO.class));
    }

    @PostMapping("/updateAuthTeacher")
    @Operation(summary = "更新老师")
    public CommonResult<Boolean> updateAuthTeacher(@RequestBody YwAuthLevelMemberVo authLevelMemberVo) {
        return success(ywAuthLevelService.updateAuthTeacher(authLevelMemberVo));
    }

    @PostMapping("/beginAuth")
    @Operation(summary = "开始认证")
    public CommonResult<YwAuthLevelTotalVo> beginAuth(@RequestParam("authId") Long authId) {
        return success(ywAuthLevelService.beginAuth(authId));
    }



}
