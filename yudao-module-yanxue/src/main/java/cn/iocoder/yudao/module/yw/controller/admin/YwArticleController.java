package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleDO;
import cn.iocoder.yudao.module.yw.service.YwArticleService;
import cn.iocoder.yudao.module.yw.vo.YwArticleUpvoteVO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleSaveReqVO;
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

@Tag(name = "管理后台 - 文章管理")
@RestController
@RequestMapping("/yw/article")
@Validated
public class YwArticleController {

    @Resource
    private YwArticleService articleService;

    @PostMapping("/create")
    @Operation(summary = "创建文章管理")
    @PreAuthorize("@ss.hasPermission('yw:article:create')")
    public CommonResult<Long> createArticle(@Valid @RequestBody YwArticleSaveReqVO createReqVO) {
        return success(articleService.createArticle(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新文章管理")
    @PreAuthorize("@ss.hasPermission('yw:article:update')")
    public CommonResult<Boolean> updateArticle(@Valid @RequestBody YwArticleSaveReqVO updateReqVO) {
        articleService.updateArticle(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文章管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:article:delete')")
    public CommonResult<Boolean> deleteArticle(@RequestParam("id") Long id) {
        articleService.deleteArticle(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除文章管理")
                @PreAuthorize("@ss.hasPermission('yw:article:delete')")
    public CommonResult<Boolean> deleteArticleList(@RequestParam("ids") List<Long> ids) {
        articleService.deleteArticleListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得文章管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:article:query')")
    public CommonResult<YwArticleRespVO> getArticle(@RequestParam("id") Long id) {
        YwArticleDO article = articleService.getArticle(id);
        return success(BeanUtils.toBean(article, YwArticleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得文章管理分页")
    @PreAuthorize("@ss.hasPermission('yw:article:query')")
    public CommonResult<PageResult<YwArticleRespVO>> getArticlePage(@Valid YwArticlePageReqVO pageReqVO) {
        PageResult<YwArticleDO> pageResult = articleService.getArticlePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwArticleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出文章管理 Excel")
    @PreAuthorize("@ss.hasPermission('yw:article:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportArticleExcel(@Valid YwArticlePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwArticleDO> list = articleService.getArticlePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "文章管理.xls", "数据", YwArticleRespVO.class,
                        BeanUtils.toBean(list, YwArticleRespVO.class));
    }

    @GetMapping("/getArticleWithUpvote")
    @Operation(summary = "获得文章管理")
    public CommonResult<YwArticleUpvoteVO> getArticleWithUpvote(@RequestParam("id") Long id) {
        YwArticleUpvoteVO article = articleService.getArticleWithUpvote(id);
        articleService.addReadCount(id);
        return success(article);
    }

}