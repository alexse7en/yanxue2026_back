package cn.iocoder.yudao.module.yw.service;


import cn.iocoder.yudao.module.yw.vo.resp.*;

import java.util.List;

public interface YwDashboardService {

    YwAuditOverviewRespVO getAuditOverview();

    YwDashboardKpiRespVO getKpis();

    List<YwDashboardTrendPointRespVO> getTrend(Integer days);

    /** 当前登录组织的 VIP/荣誉信息 */
    YwOrgProfileRespVO getCurrentOrgProfile();

    /** 当前组织志愿者统计 */
    YwOrgVolunteerStatsRespVO getCurrentOrgVolunteerStats();

    // 新增：按组织名查询
    YwOrgProfileRespVO getOrgProfileByName(String orgName);
}
