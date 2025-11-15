package cn.iocoder.yudao.module.yw.ps.controller.admin;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwMemberLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwMemberLevelRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwMemberLevelSaveReqVO;
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

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwMemberLevelDO;
import cn.iocoder.yudao.module.yw.ps.service.YwMemberLevelService;

@Tag(name = "管理后台 - 用户等级")
@RestController
@RequestMapping("/yw/member-level")
@Validated
public class YwMemberLevelController {

    @Resource
    private YwMemberLevelService memberLevelService;

    @PostMapping("/create")
    @Operation(summary = "创建用户等级")
    //@PreAuthorize("@ss.hasPermission('yw:member-level:create')")
    public CommonResult<Long> createMemberLevel(@Valid @RequestBody YwMemberLevelSaveReqVO createReqVO) {
        return success(memberLevelService.createMemberLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户等级")
    //@PreAuthorize("@ss.hasPermission('yw:member-level:update')")
    public CommonResult<Boolean> updateMemberLevel(@Valid @RequestBody YwMemberLevelSaveReqVO updateReqVO) {
        memberLevelService.updateMemberLevel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户等级")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('yw:member-level:delete')")
    public CommonResult<Boolean> deleteMemberLevel(@RequestParam("id") Long id) {
        memberLevelService.deleteMemberLevel(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除用户等级")
            //    @PreAuthorize("@ss.hasPermission('yw:member-level:delete')")
    public CommonResult<Boolean> deleteMemberLevelList(@RequestParam("ids") List<Long> ids) {
        memberLevelService.deleteMemberLevelListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户等级")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('yw:member-level:query')")
    public CommonResult<YwMemberLevelRespVO> getMemberLevel(@RequestParam("id") Long id) {
        YwMemberLevelDO memberLevel = memberLevelService.getMemberLevel(id);
        return success(BeanUtils.toBean(memberLevel, YwMemberLevelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户等级分页")
    //@PreAuthorize("@ss.hasPermission('yw:member-level:query')")
    public CommonResult<PageResult<YwMemberLevelRespVO>> getMemberLevelPage(@Valid YwMemberLevelPageReqVO pageReqVO) {
        PageResult<YwMemberLevelDO> pageResult = memberLevelService.getMemberLevelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, YwMemberLevelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户等级 Excel")
    //@PreAuthorize("@ss.hasPermission('yw:member-level:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMemberLevelExcel(@Valid YwMemberLevelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<YwMemberLevelDO> list = memberLevelService.getMemberLevelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户等级.xls", "数据", YwMemberLevelRespVO.class,
                        BeanUtils.toBean(list, YwMemberLevelRespVO.class));
    }


    @GetMapping("/selectCanAuthMemberLevel")
    @Operation(summary = "导出用户等级 Excel")
    //@PreAuthorize("@ss.hasPermission('yw:member-level:export')")
    @ApiAccessLog(operateType = EXPORT)
    public CommonResult<List<YwMemberLevelDO>>  selectCanAuthMemberLevel()  {
        return success(memberLevelService.selectCanAuthMemberLevel());
    }


}
