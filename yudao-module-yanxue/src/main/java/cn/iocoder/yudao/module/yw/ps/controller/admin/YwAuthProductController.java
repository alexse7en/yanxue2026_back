package cn.iocoder.yudao.module.yw.ps.controller.admin;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthProductPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthProductRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthProductSaveReqVO;
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

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthProductDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthProductService;

@Tag(name = "管理后台 - 作品申请")
@RestController
@RequestMapping("/yw/auth-product")
@Validated
public class YwAuthProductController {

    @Resource
    private YwAuthProductService authProductService;

    @PostMapping("/create")
    @Operation(summary = "创建作品申请")
    @PreAuthorize("@ss.hasPermission('yw:auth-product:create')")
    public CommonResult<Long> createAuthProduct(@Valid @RequestBody YwAuthProductSaveReqVO createReqVO) {
        return success(authProductService.createAuthProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新作品申请")
    @PreAuthorize("@ss.hasPermission('yw:auth-product:update')")
    public CommonResult<Boolean> updateAuthProduct(@Valid @RequestBody YwAuthProductSaveReqVO updateReqVO) {
        authProductService.updateAuthProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除作品申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:auth-product:delete')")
    public CommonResult<Boolean> deleteAuthProduct(@RequestParam("id") Long id) {
        authProductService.deleteAuthProduct(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除作品申请")
                @PreAuthorize("@ss.hasPermission('yw:auth-product:delete')")
    public CommonResult<Boolean> deleteAuthProductList(@RequestParam("ids") List<Long> ids) {
        authProductService.deleteAuthProductListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得作品申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:auth-product:query')")
    public CommonResult<YwAuthProductRespVO> getAuthProduct(@RequestParam("id") Long id) {
        YwAuthProductDO authProduct = authProductService.getAuthProduct(id);
        return success(BeanUtils.toBean(authProduct, YwAuthProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得作品申请分页")
    @PreAuthorize("@ss.hasPermission('yw:auth-product:query')")
    public CommonResult<PageResult<YwAuthProductRespVO>> getAuthProductPage(@Valid YwAuthProductPageReqVO pageReqVO) {
        PageResult<YwAuthProductRespVO> pageResult = authProductService.getAuthProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthProductRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出作品申请 Excel")
    @PreAuthorize("@ss.hasPermission('yw:auth-product:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAuthProductExcel(@Valid YwAuthProductPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwAuthProductRespVO> list = authProductService.getAuthProductPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "作品申请.xls", "数据", YwAuthProductRespVO.class,
                        BeanUtils.toBean(list, YwAuthProductRespVO.class));
    }


    @GetMapping("/my")
    @Operation(summary = "获得作品申请分页")
    public CommonResult<PageResult<YwAuthProductRespVO>> my( YwAuthProductPageReqVO pageReqVO) {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        pageReqVO.setMemberId(memberId);
        PageResult<YwAuthProductRespVO> pageResult = authProductService.getAuthProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthProductRespVO.class));
    }

    @GetMapping("/getNeedSpuList")
    @Operation(summary = "获得作品申请分页")
    public CommonResult<List<YwAuthProductRespVO>> getNeedSpuList( ) {

        List<YwAuthProductRespVO> pageResult = authProductService.getNeedSpuList();
        return success(pageResult);
    }
}
