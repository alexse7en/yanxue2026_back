package cn.iocoder.yudao.module.yw.ps.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthLevelDO;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthLevelService;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelForTeacherVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelMemberVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelRequestVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelTotalVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthLevelRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthLevelSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 评审申请")
@RestController
@RequestMapping("/yw/ps/ywAuthLevel")
@Validated
public class YwAuthLevelAppController {

    @Resource
    private YwAuthLevelService ywAuthLevelService;

    @PostMapping("/beginAuth")
    @Operation(summary = "开始认证")
    public CommonResult<YwAuthLevelTotalVo> beginAuth(@RequestParam("authId") Long authId) {
        return success(ywAuthLevelService.beginAuth(authId));
    }

    @PostMapping("/getAuthTotal")
    @Operation(summary = "获取认证")
    public CommonResult<YwAuthLevelTotalVo> getAuthTotal(@RequestBody YwAuthLevelRequestVo authLevelRequestVo) {
        return success(ywAuthLevelService.getAuthTotal( authLevelRequestVo));
    }

    @PostMapping("/updateAuth")
    @Operation(summary = "更新认证")
    public CommonResult<YwAuthLevelMemberVo> updateAuth(@RequestBody YwAuthLevelMemberVo authLevelMemberVo) {
        return success(ywAuthLevelService.updateAuth(authLevelMemberVo));
    }

    @PostMapping("/updateAuthTotal")
    @Operation(summary = "更新认证")
    public CommonResult<YwAuthLevelTotalVo> updateAuthTotal(@RequestBody YwAuthLevelTotalVo totalVo) {
        return success(ywAuthLevelService.updateAuthTotal(totalVo));
    }

    @PostMapping("/updateAuthTotalAndSubmit")
    @Operation(summary = "更新认证")
    public CommonResult<Boolean> updateAuthTotalAndSubmit(@RequestBody YwAuthLevelTotalVo totalVo) {
        YwAuthLevelTotalVo vo=ywAuthLevelService.updateAuthTotal(totalVo);
        if(vo==null || StringUtils.isEmpty(vo.getId())){
            return error(500,"更新失败");

        }
        return success(ywAuthLevelService.submitAuth(totalVo.getId()));
    }

    @PostMapping("/submitAuth")
    @Operation(summary = "提交认证")
    public CommonResult<Boolean> submitAuth(@RequestParam("authId") Long authId) {
        return success(ywAuthLevelService.submitAuth(authId));
    }



    @GetMapping("/getTeacherJob")
    @Operation(summary = "获取老师批改作业")
    public CommonResult<List<YwAuthLevelForTeacherVo>> getTeacherJob(@RequestParam("teacherStatus") String teacherStatus) {
        return success(ywAuthLevelService.getTeacherJob(teacherStatus));
    }

    @PostMapping("/getAuthForTeacherComment")
    @Operation(summary = "老师批改")
    public CommonResult<YwAuthLevelMemberVo> getAuthForTeacherComment(@RequestBody YwAuthLevelMemberVo authLevelMemberVo) {
        return success(ywAuthLevelService.getAuthForTeacherComment(authLevelMemberVo));
    }

    @PostMapping("/updateAuthComment")
    @Operation(summary = "老师批改")
    public CommonResult<YwAuthLevelTotalVo> updateAuthComment(@RequestBody YwAuthLevelMemberVo authLevelMemberVo) {
        return success(ywAuthLevelService.updateAuthComment(authLevelMemberVo));
    }

    @GetMapping("/my")
    @Operation(summary = "老师批改")
    public CommonResult<List<YwAuthLevelDO>> my() {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();

        return success(ywAuthLevelService.my(memberId));
    }

    @GetMapping("/get")
    @Operation(summary = "获得评审申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<YwAuthLevelTotalVo> getYwAuthLevel(@RequestParam("id") Long id) {
        YwAuthLevelRequestVo authLevelRequestVo=new YwAuthLevelRequestVo();
        authLevelRequestVo.setAuthId(id);
        YwAuthLevelTotalVo ywAuthLevel = ywAuthLevelService.getAuthTotal(authLevelRequestVo);
        return success(ywAuthLevel);
    }

}