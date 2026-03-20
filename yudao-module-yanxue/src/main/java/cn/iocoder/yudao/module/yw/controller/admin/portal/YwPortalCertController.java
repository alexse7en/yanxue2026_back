package cn.iocoder.yudao.module.yw.controller.admin.portal;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.service.YwPortalService;
import cn.iocoder.yudao.module.yw.vo.portal.query.YwPortalCertQueryReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalCertRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 首页证书中心")
@RestController
@Validated
public class YwPortalCertController {

    @Resource
    private YwPortalService ywPortalService;

    @GetMapping("/yw-cert/query-student")
    @PermitAll
    @Operation(summary = "首页学生证书查询")
    public CommonResult<PageResult<YwPortalCertRespVO>> queryStudentCert(@Valid YwPortalCertQueryReqVO reqVO) {
        return success(ywPortalService.queryStudentCert(reqVO));
    }

    @GetMapping("/yw-tutor-cert/query")
    @PermitAll
    @Operation(summary = "首页导师证书查询")
    public CommonResult<PageResult<YwPortalCertRespVO>> queryTutorCert(@Valid YwPortalCertQueryReqVO reqVO) {
        return success(ywPortalService.queryTutorCert(reqVO));
    }
}

