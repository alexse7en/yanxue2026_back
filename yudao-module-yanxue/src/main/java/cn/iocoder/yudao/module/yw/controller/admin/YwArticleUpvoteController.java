package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwArticleUpvotePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwArticleUpvoteRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwArticleUpvoteSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleUpvoteDO;
import cn.iocoder.yudao.module.yw.service.YwArticleUpvoteService;

@Tag(name = "管理后台 - 文章管理")
@RestController
@RequestMapping("/yw.ps/yw-article-upvote")
@Validated
public class YwArticleUpvoteController {

    @Resource
    private YwArticleUpvoteService ywArticleUpvoteService;

    @PostMapping("/create")
    @Operation(summary = "创建文章管理")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:create')")
    public CommonResult<Long> createYwArticleUpvote(@Valid @RequestBody YwArticleUpvoteSaveReqVO createReqVO) {
        return success(ywArticleUpvoteService.createYwArticleUpvote(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新文章管理")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:update')")
    public CommonResult<Boolean> updateYwArticleUpvote(@Valid @RequestBody YwArticleUpvoteSaveReqVO updateReqVO) {
        ywArticleUpvoteService.updateYwArticleUpvote(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文章管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:delete')")
    public CommonResult<Boolean> deleteYwArticleUpvote(@RequestParam("id") Long id) {
        ywArticleUpvoteService.deleteYwArticleUpvote(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除文章管理")
                @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:delete')")
    public CommonResult<Boolean> deleteYwArticleUpvoteList(@RequestParam("ids") List<Long> ids) {
        ywArticleUpvoteService.deleteYwArticleUpvoteListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得文章管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:query')")
    public CommonResult<YwArticleUpvoteRespVO> getYwArticleUpvote(@RequestParam("id") Long id) {
        YwArticleUpvoteDO ywArticleUpvote = ywArticleUpvoteService.getYwArticleUpvote(id);
        return success(BeanUtils.toBean(ywArticleUpvote, YwArticleUpvoteRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得文章管理分页")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:query')")
    public CommonResult<PageResult<YwArticleUpvoteRespVO>> getYwArticleUpvotePage(@Valid YwArticleUpvotePageReqVO pageReqVO) {
        PageResult<YwArticleUpvoteDO> pageResult = ywArticleUpvoteService.getYwArticleUpvotePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwArticleUpvoteRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出文章管理 Excel")
    @PreAuthorize("@ss.hasPermission('yw.ps:yw-article-upvote:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportYwArticleUpvoteExcel(@Valid YwArticleUpvotePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwArticleUpvoteDO> list = ywArticleUpvoteService.getYwArticleUpvotePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "文章管理.xls", "数据", YwArticleUpvoteRespVO.class,
                        BeanUtils.toBean(list, YwArticleUpvoteRespVO.class));
    }

}