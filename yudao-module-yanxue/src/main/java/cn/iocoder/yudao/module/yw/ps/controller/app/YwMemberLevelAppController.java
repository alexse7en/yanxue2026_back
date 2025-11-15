package cn.iocoder.yudao.module.yw.ps.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwMemberLevelDO;
import cn.iocoder.yudao.module.yw.ps.service.YwMemberLevelService;
import cn.iocoder.yudao.module.yw.ps.vo.YwMemberLevelWithAddressVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户等级")
@RestController
@RequestMapping("/yw/member-level")
@Validated
public class YwMemberLevelAppController {

    @Resource
    private YwMemberLevelService memberLevelService;



    @GetMapping("/selectCanAuthMemberLevel")
    @Operation(summary = "导出用户等级 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public CommonResult<List<YwMemberLevelDO>>  selectCanAuthMemberLevel()  {
        return success(memberLevelService.selectCanAuthMemberLevel());
    }

    @GetMapping("/selectMemberLevel")
    @Operation(summary = "导出用户等级 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public CommonResult<YwMemberLevelWithAddressVo>  selectMemberLevel()  {
        return success(memberLevelService.selectMemberLevel());
    }

    @GetMapping("/selectMemberLevelById")
    @Operation(summary = "导出用户等级 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public CommonResult<YwMemberLevelWithAddressVo>  selectMemberLevelById(@RequestParam("id") Long id)  {
        return success(memberLevelService.selectMemberLevelById(id));
    }
}