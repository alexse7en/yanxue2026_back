package cn.iocoder.yudao.module.yw.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import cn.iocoder.yudao.module.yw.service.YwDashboardService;
import cn.iocoder.yudao.module.yw.service.YwMemberApplyService;
import cn.iocoder.yudao.module.yw.service.YwOrganizationInfoService;
import cn.iocoder.yudao.module.yw.service.YwOrgApplyService;
import cn.iocoder.yudao.module.yw.vo.page.YwOrgApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationInfoSubmitReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwOrgApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwOrgProfileRespVO;
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
@RequestMapping("/yw/org")
public class YwOrgAppController {

    @Resource
    private YwMemberApplyService ywMemberApplyService;
    @Resource
    private YwOrganizationInfoService   ywOrganizationInfoService;

    @Resource
    private YwDashboardService   ywDashboardService;   // 会员 & 荣誉
    @Resource
    private AdminUserMapper adminUserMapper;          // 查 avatar（system_users）

    // 取当前登录人（Yudao封装，按你项目里实际类名微调）
    private  Long getLoginUserId() {
        return SecurityFrameworkUtils.getLoginUserId();
    }

    @GetMapping("/list")
    public CommonResult<List<Map<String,Object>>> listActive() {
        List<YwOrganizationInfoDO> list = ywOrganizationInfoService.listActive();
        // 前端显示只要 id + username 即可，再留 intro/materia 也行
        List<Map<String,Object>> resp = list.stream().map(o -> {
            Map<String,Object> m = new HashMap<>();
            m.put("id", o.getId());
            m.put("username", o.getUsername());
            m.put("intro", o.getIntro());
            m.put("material", o.getMaterial());
            return m;
        }).collect(Collectors.toList());
        return CommonResult.success(resp);
    }

    @PostMapping("/apply")
    public CommonResult<Long> apply(@RequestParam("orgId") Long orgId) {
        Long memberId = getLoginUserId();
        Long id = ywOrganizationInfoService.apply(memberId, orgId);
        return CommonResult.success(id);
    }

    @GetMapping("/my-apply")
    public CommonResult<Map<String,Object>> myApply() {
        Long memberId = getLoginUserId();
        YwMemberApplyDO apply = ywOrganizationInfoService.getMyLatestApply(memberId);
        Map<String,Object> m = new HashMap<>();
        if (apply != null) {
            m.put("status", apply.getStatus());
            m.put("orgId", apply.getOrgId());
            YwOrganizationInfoDO org = ywOrganizationInfoService.getById(apply.getOrgId());
            if (org != null) {
                m.put("orgName", org.getUsername());
            }
        }
        return CommonResult.success(m);
    }


    // 通过“名称”拿组织详情（当仅 member_user.orgName 有值时用）
    @GetMapping("/by-name")
    public CommonResult<Map<String,Object>> getByName(@RequestParam("name") String name) {
        YwOrganizationInfoDO org = ywOrganizationInfoService.getByName(name);
        if (org == null) return CommonResult.success(null);
        Map<String,Object> m = new HashMap<>();
        m.put("id", org.getId());
        m.put("username", org.getUsername());
        m.put("intro", org.getIntro());
        m.put("material", org.getMaterial());
        return CommonResult.success(m);
    }


    /** 新增：组织档案（会员 & 荣誉），支持按 orgName 查询 */
    @Operation(summary = "组织档案（会员 & 荣誉）- 按组织名")
    @GetMapping("/profile")
    public CommonResult<YwOrgProfileRespVO> getProfileByName(@RequestParam("orgName") String orgName) {
        // 注意：这里不是“当前登录组织”，而是“指定组织名”
        YwOrgProfileRespVO vo = ywDashboardService.getOrgProfileByName(orgName.trim());
        return success(vo);
    }

    /** 新增：按组织名查询头像（logo），来自 system_users.avatar */
    @Operation(summary = "按组织名查询头像（logo）")
    @GetMapping("/avatar-by-name")
    public CommonResult<Map<String, String>> getAvatarByName(@RequestParam("orgName") String orgName) {
        AdminUserDO user = adminUserMapper.selectByUsername(orgName.trim());
        String avatar = user == null ? "" : (user.getAvatar() == null ? "" : user.getAvatar());
        Map<String, String> data = new HashMap<>();
        data.put("avatar", avatar);
        return success(data);
    }
}

