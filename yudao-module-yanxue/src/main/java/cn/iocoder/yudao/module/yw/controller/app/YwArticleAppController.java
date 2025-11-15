package cn.iocoder.yudao.module.yw.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleDO;
import cn.iocoder.yudao.module.yw.service.YwArticleService;
import cn.iocoder.yudao.module.yw.service.YwArticleUpvoteService;
import cn.iocoder.yudao.module.yw.vo.YwArticlePageReqLimitVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleUpvoteVO;
import cn.iocoder.yudao.module.yw.vo.YwArticleWithAuthorVO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 文章管理")
@RestController
@RequestMapping("/yw/article")
@Validated
public class YwArticleAppController {

    @Resource
    private YwArticleService articleService;


    @GetMapping("/getArticle")
    @PermitAll
    @Operation(summary = "获得文章管理")
    public CommonResult<YwArticleRespVO> getArticle(@RequestParam("id") Long id) {
        YwArticleDO article = articleService.getArticle(id);
        articleService.addReadCount(id);
        return success(BeanUtils.toBean(article, YwArticleRespVO.class));
    }

    @GetMapping("/getArticleWithUpvote")
    @Operation(summary = "获得文章管理")
    public CommonResult<YwArticleUpvoteVO> getArticleWithUpvote(@RequestParam("id") Long id) {
        YwArticleUpvoteVO article = articleService.getArticleWithUpvote(id);
        articleService.addReadCount(id);
        return success(article);
    }

    @GetMapping("/page")
    @Operation(summary = "获得文章管理分页")
    public CommonResult<PageResult<YwArticleRespVO>> page(@Valid YwArticlePageReqLimitVO pageReqVO) {
        PageResult<YwArticleDO> pageResult = articleService.getArticlePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwArticleRespVO.class));
    }

    @GetMapping("/getArticleList")
    @Operation(summary = "获得文章管理分页")
    @PermitAll
    public CommonResult<List<YwArticleRespVO>> getArticleList(@Valid YwArticlePageReqLimitVO pageReqVO) {
        List<YwArticleDO> pageResult = articleService.selectList(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwArticleRespVO.class));
    }

    @GetMapping("/getArticleListRandom")
    @Operation(summary = "获得文章管理分页")
    @PermitAll
    public CommonResult<List<YwArticleRespVO>> getArticleListRandom(@Valid YwArticlePageReqLimitVO pageReqVO) {
        List<YwArticleDO> pageResult = articleService.selectList(pageReqVO);
        Collections.shuffle(pageResult);
        List<YwArticleDO> randomArticles = pageResult.stream()
                .limit(3)
                .collect(Collectors.toList());
        return success(BeanUtils.toBean(randomArticles, YwArticleRespVO.class));
    }



    @GetMapping("/upvote")
    @Operation(summary = "创建文章管理")
    public CommonResult<Boolean> upvote(@RequestParam("id") Long id) {
        return success(articleService.upvote(id));
    }

    @GetMapping("/downvote")
    @Operation(summary = "创建文章管理")
    public CommonResult<Boolean> downvote(@RequestParam("id") Long id) {
        return success(articleService.downvote(id));
    }

    @PostMapping("/my")
    @Operation(summary = "创建文章管理")
    public CommonResult<List<YwArticleDO>> my() {
        return success(articleService.my());
    }

    @GetMapping("/getArticleListWithAuth")
    @Operation(summary = "获得文章管理分页")
    @PermitAll
    public CommonResult<List<YwArticleWithAuthorVO>> getArticleListWithAuthor(@Valid YwArticlePageReqLimitVO pageReqVO) {
        List<YwArticleWithAuthorVO> pageResult = articleService.getArticleListWithAuthor(pageReqVO);
        return success(pageResult);
    }
}