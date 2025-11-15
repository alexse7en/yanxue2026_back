package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwQuOptionPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwQuOptionRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwQuOptionSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.module.yw.service.YwQuOptionService;

@Tag(name = "管理后台 - 试题选项")
@RestController
@RequestMapping("/yw/qu-option")
@Validated
public class YwQuOptionController {

    @Resource
    private YwQuOptionService quOptionService;

    @PostMapping("/create")
    @Operation(summary = "创建试题选项")
    @PreAuthorize("@ss.hasPermission('yw:qu-option:create')")
    public CommonResult<Long> createQuOption(@Valid @RequestBody YwQuOptionSaveReqVO createReqVO) {
        return success(quOptionService.createQuOption(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新试题选项")
    @PreAuthorize("@ss.hasPermission('yw:qu-option:update')")
    public CommonResult<Boolean> updateQuOption(@Valid @RequestBody YwQuOptionSaveReqVO updateReqVO) {
        quOptionService.updateQuOption(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除试题选项")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:qu-option:delete')")
    public CommonResult<Boolean> deleteQuOption(@RequestParam("id") Long id) {
        quOptionService.deleteQuOption(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除试题选项")
                @PreAuthorize("@ss.hasPermission('yw:qu-option:delete')")
    public CommonResult<Boolean> deleteQuOptionList(@RequestParam("ids") List<Long> ids) {
        quOptionService.deleteQuOptionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得试题选项")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:qu-option:query')")
    public CommonResult<YwQuOptionRespVO> getQuOption(@RequestParam("id") Long id) {
        YwQuOptionDO quOption = quOptionService.getQuOption(id);
        return success(BeanUtils.toBean(quOption, YwQuOptionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得试题选项分页")
    @PreAuthorize("@ss.hasPermission('yw:qu-option:query')")
    public CommonResult<PageResult<YwQuOptionRespVO>> getQuOptionPage(@Valid YwQuOptionPageReqVO pageReqVO) {
        PageResult<YwQuOptionDO> pageResult = quOptionService.getQuOptionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwQuOptionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出试题选项 Excel")
    @PreAuthorize("@ss.hasPermission('yw:qu-option:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQuOptionExcel(@Valid YwQuOptionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwQuOptionDO> list = quOptionService.getQuOptionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "试题选项.xls", "数据", YwQuOptionRespVO.class,
                        BeanUtils.toBean(list, YwQuOptionRespVO.class));
    }

}