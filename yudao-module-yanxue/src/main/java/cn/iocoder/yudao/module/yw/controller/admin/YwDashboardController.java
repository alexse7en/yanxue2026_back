// src/main/java/cn/iocoder/yudao/module/yw/controller/admin/dashboard/YwDashboardController.java
package cn.iocoder.yudao.module.yw.controller.admin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.vo.resp.*;
import cn.iocoder.yudao.module.yw.service.YwDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 仪表盘(YW)")
@RestController
@RequestMapping("/yw/dashboard") // 对齐前端：/admin-api/yw/dashboard/**
public class YwDashboardController {

    @Resource
    private YwDashboardService dashboardService;

    /** 待办监控汇总 */
    @GetMapping("/audit-overview")
    public CommonResult<YwAuditOverviewRespVO> auditOverview() {
        return success(dashboardService.getAuditOverview());
    }

    /** KPI 汇总 */
    @GetMapping("/kpis")
    public CommonResult<YwDashboardKpiRespVO> kpis() {
        return success(dashboardService.getKpis());
    }

    /** 趋势数据（默认近 7 天） */
    @GetMapping("/trend")
    public CommonResult<List<YwDashboardTrendPointRespVO>> trend(
            @RequestParam(value = "days", defaultValue = "7") Integer days) {
        return success(dashboardService.getTrend(days));
    }

    @GetMapping("/org-profile")
    @Operation(summary = "获取当前组织的 VIP 与荣誉信息")
    public CommonResult<YwOrgProfileRespVO> getOrgProfile() {
        return success(dashboardService.getCurrentOrgProfile());
    }

    @GetMapping("/org-stats")
    @Operation(summary = "获取当前组织的志愿者统计")
    public CommonResult<YwOrgVolunteerStatsRespVO> getOrgVolunteerStats() {
        return success(dashboardService.getCurrentOrgVolunteerStats());
    }
}
