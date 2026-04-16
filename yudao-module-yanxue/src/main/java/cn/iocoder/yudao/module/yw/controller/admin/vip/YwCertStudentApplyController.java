package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.vip.YwCertStudentApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplySubmitReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 学生证书生成申请")
@RestController
@RequestMapping("/yw/yw-cert-student-apply")
@Validated
public class YwCertStudentApplyController {

    @Resource
    private YwCertStudentApplyService certStudentApplyService;

    @GetMapping("/page-my")
    @Operation(summary = "分页查询当前用户的证书申请记录")
    public CommonResult<PageResult<YwCertStudentApplyRespVO>> pageMy(@Valid YwCertStudentApplyPageReqVO reqVO) {
        return success(certStudentApplyService.getApplyPageMy(reqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "管理员分页查询学生证书申请")
    public CommonResult<PageResult<YwCertStudentApplyRespVO>> page(@Valid YwCertStudentApplyAuditPageReqVO reqVO) {
        return success(certStudentApplyService.getApplyAuditPage(reqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "查询单条证书申请详情")
    @Parameter(name = "id", description = "申请批次 ID", required = true)
    public CommonResult<YwCertStudentApplyRespVO> get(@RequestParam("id") Long id) {
        return success(certStudentApplyService.getApply(id));
    }

    @PostMapping("/parse")
    @Operation(summary = "解析上传的学生证书 Excel")
    public CommonResult<YwCertStudentApplyRespVO> parse(@Valid @RequestBody YwCertStudentApplyParseReqVO reqVO) {
        return success(certStudentApplyService.parseApply(reqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交学生证书生成申请")
    public CommonResult<Long> submit(@Valid @RequestBody YwCertStudentApplySubmitReqVO reqVO) {
        return success(certStudentApplyService.submitApply(reqVO));
    }

    @PostMapping("/audit")
    @Operation(summary = "审核学生证书生成申请")
    public CommonResult<Boolean> audit(@Valid @RequestBody YwCertStudentApplyAuditReqVO reqVO) {
        certStudentApplyService.auditApply(reqVO);
        return success(true);
    }
}
