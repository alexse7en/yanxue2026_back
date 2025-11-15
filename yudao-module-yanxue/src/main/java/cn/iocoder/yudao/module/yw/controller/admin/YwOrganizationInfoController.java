package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import cn.iocoder.yudao.module.yw.service.YwOrganizationInfoService;
import cn.iocoder.yudao.module.yw.service.YwOrgApplyService;
import cn.iocoder.yudao.module.yw.vo.page.YwOrgApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationInfoSubmitReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwOrgApplyRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@RestController
@Tag(name = "管理后台 - 组织信息")
@RequestMapping("/yw/organization")
public class YwOrganizationInfoController {

    @Resource
    private YwOrganizationInfoService organizationInfoService;
    @Resource
    private YwOrgApplyService applyService;
    @PostMapping("/submit")
    @Operation(summary = "提交组织审核信息")
    public CommonResult<Boolean> submitOrganizationInfo(@RequestBody @Valid YwOrganizationInfoSubmitReqVO reqVO) {
        String username = SecurityFrameworkUtils.getLoginUserUsername();
        organizationInfoService.submitOrganizationInfo(reqVO, username);
        return success(true);
    }

    @GetMapping("/check")
    public Map<String, Boolean> checkOrgExist(@RequestParam String username) {

        boolean exist = organizationInfoService.checkExistByUsernameAndStatus(username, 1);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exist", exist);
        return result;
    }

    @GetMapping("/list")
    public CommonResult<PageResult<YwOrgApplyRespVO>> getPage(@Validated YwOrgApplyPageReqVO reqVO) {
        return success(applyService.getPage(reqVO));
    }

    @GetMapping("/detail/{id}")
    public CommonResult<YwOrgApplyRespVO> detail(@PathVariable Long id) {
        return CommonResult.success(applyService.getDetail(id));
    }

    @PostMapping("/audit")
    public CommonResult<Boolean> audit(@RequestBody YwOrganizationApplyAuditReqVO reqVO) {
        applyService.audit(reqVO);
        return CommonResult.success(true);
    }

    @GetMapping("/my")
    public CommonResult<List<Long>> getMyOrgs() {
        String username = SecurityFrameworkUtils.getLoginUserUsername(); // 获取当前登录用户ID
        List<YwOrganizationInfoDO> myOrgs = organizationInfoService.getPassedOrgsByUserId(username);
        List<Long> orgIds = myOrgs.stream()
                .map(YwOrganizationInfoDO::getId)
                .collect(Collectors.toList());
        return CommonResult.success(orgIds);
    }
}

