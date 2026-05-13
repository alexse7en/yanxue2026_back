package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.yw.service.vip.YwYanxueArticleService;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleExcelVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleImportRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleImportTemplateVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 研学首页文章")
@RestController
@RequestMapping("/yw/yw-yanxue-article")
@Validated
public class YwYanxueArticleController {

    @Resource
    private YwYanxueArticleService yanxueArticleService;

    @PostMapping("/create")
    @Operation(summary = "创建研学首页文章")
    public CommonResult<Long> create(@Valid @RequestBody YwYanxueArticleSaveReqVO reqVO) {
        return success(yanxueArticleService.createArticle(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新研学首页文章")
    public CommonResult<Boolean> update(@Valid @RequestBody YwYanxueArticleSaveReqVO reqVO) {
        yanxueArticleService.updateArticle(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除研学首页文章")
    @Parameter(name = "id", description = "主键 ID", required = true)
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        yanxueArticleService.deleteArticle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得研学首页文章")
    @Parameter(name = "id", description = "主键 ID", required = true)
    public CommonResult<YwYanxueArticleRespVO> get(@RequestParam("id") Long id) {
        return success(yanxueArticleService.getArticle(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得研学首页文章分页")
    public CommonResult<PageResult<YwYanxueArticleRespVO>> getPage(@Valid YwYanxueArticlePageReqVO pageReqVO) {
        return success(yanxueArticleService.getArticlePage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出研学首页文章 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid YwYanxueArticlePageReqVO pageReqVO,
                            @RequestParam(value = "includeContent", required = false, defaultValue = "false") Boolean includeContent,
                            HttpServletResponse response) throws IOException {
        if (Boolean.TRUE.equals(includeContent)) {
            List<YwYanxueArticleExcelVO> list = yanxueArticleService.getArticleExcelList(pageReqVO);
            ExcelUtils.write(response, "研学首页文章.xls", "数据", YwYanxueArticleExcelVO.class, list);
            return;
        }
        List<YwYanxueArticleRespVO> list = yanxueArticleService.getArticleList(pageReqVO);
        ExcelUtils.write(response, "研学首页文章.xls", "数据", YwYanxueArticleRespVO.class, list);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得研学首页文章导入模板")
    public void getImportTemplate(HttpServletResponse response) throws IOException {
        ExcelUtils.write(response, "研学首页文章导入模板.xls", "数据",
                YwYanxueArticleImportTemplateVO.class, Collections.emptyList());
    }

    @PostMapping("/import")
    @Operation(summary = "导入研学首页文章")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    public CommonResult<YwYanxueArticleImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                                 @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<YwYanxueArticleExcelVO> list = ExcelUtils.read(file, YwYanxueArticleExcelVO.class);
        return success(yanxueArticleService.importArticleList(list, updateSupport));
    }
}
