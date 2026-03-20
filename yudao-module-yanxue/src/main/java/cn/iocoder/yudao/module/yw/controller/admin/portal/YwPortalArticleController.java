package cn.iocoder.yudao.module.yw.controller.admin.portal;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.YwPortalService;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalArticlePageReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalArticleRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 首页资讯中心")
@RestController
@RequestMapping("/yw-article")
@Validated
public class YwPortalArticleController {

    @Resource
    private YwPortalService ywPortalService;

    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "首页资讯中心分页")
    public CommonResult<PageResult<YwPortalArticleRespVO>> getArticlePage(@Valid YwPortalArticlePageReqVO pageReqVO) {
        return success(ywPortalService.getPortalArticlePage(pageReqVO));
    }

    @GetMapping("/get")
    @PermitAll
    @Operation(summary = "首页资讯中心详情")
    public CommonResult<YwPortalArticleRespVO> getArticle(@RequestParam("id") Long id) {
        return success(ywPortalService.getPortalArticle(id));
    }
}

