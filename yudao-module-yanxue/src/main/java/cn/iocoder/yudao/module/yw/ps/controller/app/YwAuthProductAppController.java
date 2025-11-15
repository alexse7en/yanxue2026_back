package cn.iocoder.yudao.module.yw.ps.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthProductDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthProductService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthProductPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthProductRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthProductSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 作品申请")
@RestController
@RequestMapping("/yw/auth-product")
@Validated
public class YwAuthProductAppController {

    @Resource
    private YwAuthProductService authProductService;

    @PostMapping("/create")
    @Operation(summary = "创建作品申请")
    public CommonResult<Long> createAuthProduct(@Valid @RequestBody YwAuthProductSaveReqVO createReqVO) {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        createReqVO.setMemberId(memberId);
        createReqVO.setStatus("0");
        return success(authProductService.createAuthProduct(createReqVO));
    }

    @PutMapping("/submit")
    @Operation(summary = "更新作品申请")
    public CommonResult<Boolean> submitProduct(@Valid @RequestBody YwAuthProductDO updateReqVO) {
        updateReqVO.setStatus("1");
        authProductService.submitProduct(updateReqVO);
        return success(true);
    }
    @PutMapping("/updateStatus")
    @Operation(summary = "更新作品申请")
    @PreAuthorize("@ss.hasPermission('yw:auth-product:update')")
    public CommonResult<Boolean> updateAuthProductStatus(@Valid @RequestBody YwAuthProductDO updateReqVO) {
        authProductService.updateAuthProductStatus(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得作品申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<YwAuthProductRespVO> getAuthProduct(@RequestParam("id") Long id) {
        YwAuthProductDO authProduct = authProductService.getAuthProduct(id);
        return success(BeanUtils.toBean(authProduct, YwAuthProductRespVO.class));
    }

    @GetMapping("/my")
    @Operation(summary = "获得作品申请分页")
    public CommonResult<PageResult<YwAuthProductDO>> my( YwAuthProductPageReqVO pageReqVO) {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        pageReqVO.setMemberId(memberId);
        PageResult<YwAuthProductDO> pageResult = authProductService.my(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwAuthProductDO.class));
    }

    @PutMapping("/update")
    @Operation(summary = "更新作品申请")
    public CommonResult<Boolean> updateAuthProduct(@Valid @RequestBody YwAuthProductSaveReqVO updateReqVO) {
        authProductService.updateAuthProduct(updateReqVO);
        return success(true);
    }
}
