package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.module.yw.vo.page.YwMemberChapterPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberChapterRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberChapterSaveReqVO;
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

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberChapterDO;
import cn.iocoder.yudao.module.yw.service.YwMemberChapterService;

@Tag(name = "管理后台 - 章节进度")
@RestController
@RequestMapping("/yw/member-chapter")
@Validated
public class YwMemberChapterController {

    @Resource
    private YwMemberChapterService memberChapterService;

    @PostMapping("/create")
    @Operation(summary = "创建章节进度")
    @PreAuthorize("@ss.hasPermission('yw:member-chapter:create')")
    public CommonResult<String> createMemberChapter(@Valid @RequestBody YwMemberChapterSaveReqVO createReqVO) {
        return success(memberChapterService.createMemberChapter(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新章节进度")
    @PreAuthorize("@ss.hasPermission('yw:member-chapter:update')")
    public CommonResult<Boolean> updateMemberChapter(@Valid @RequestBody YwMemberChapterSaveReqVO updateReqVO) {
        memberChapterService.updateMemberChapter(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除章节进度")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('yw:member-chapter:delete')")
    public CommonResult<Boolean> deleteMemberChapter(@RequestParam("id") String id) {
        memberChapterService.deleteMemberChapter(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除章节进度")
                @PreAuthorize("@ss.hasPermission('yw:member-chapter:delete')")
    public CommonResult<Boolean> deleteMemberChapterList(@RequestParam("ids") List<String> ids) {
        memberChapterService.deleteMemberChapterListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得章节进度")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('yw:member-chapter:query')")
    public CommonResult<YwMemberChapterRespVO> getMemberChapter(@RequestParam("id") String id) {
        YwMemberChapterDO memberChapter = memberChapterService.getMemberChapter(id);
        return success(BeanUtils.toBean(memberChapter, YwMemberChapterRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得章节进度分页")
    @PreAuthorize("@ss.hasPermission('yw:member-chapter:query')")
    public CommonResult<PageResult<YwMemberChapterRespVO>> getMemberChapterPage(@Valid YwMemberChapterPageReqVO pageReqVO) {
        PageResult<YwMemberChapterDO> pageResult = memberChapterService.getMemberChapterPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwMemberChapterRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出章节进度 Excel")
    @PreAuthorize("@ss.hasPermission('yw:member-chapter:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMemberChapterExcel(@Valid YwMemberChapterPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwMemberChapterDO> list = memberChapterService.getMemberChapterPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "章节进度.xls", "数据", YwMemberChapterRespVO.class,
                        BeanUtils.toBean(list, YwMemberChapterRespVO.class));
    }

}