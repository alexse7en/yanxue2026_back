// src/main/java/cn/iocoder/yudao/module/yw/service/dashboard/impl/YwDashboardServiceImpl.java
package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.logger.LoginLogMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2GrantTypeEnum;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthLevelMapper;
import cn.iocoder.yudao.module.yw.vo.resp.*;
import cn.iocoder.yudao.module.yw.dal.mysql.YwMemberApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwOrganizationInfoMapper;
import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthProductMapper;
import cn.iocoder.yudao.module.yw.service.YwDashboardService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class YwDashboardServiceImpl implements YwDashboardService {

    @Resource private YwOrganizationInfoMapper OrganizationInfoMapper;
    @Resource private YwAuthProductMapper  AuthProductMapper;          // 如无作品模块，可为 null 并判空
    @Resource private YwMemberApplyMapper memberApplyMapper;
    @Resource private YwAuthLevelMapper AuthLevelMapper;
    @Resource private AdminUserMapper AdminUserMapper;
    @Resource private MemberUserMapper memberUserMapper;
    @Resource private LoginLogMapper loginLogMapper; // 如未引入系统登录日志模块，可判空
    @Resource private DictDataService dictDataService;

    @Override
    public YwAuditOverviewRespVO getAuditOverview() {
        YwAuditOverviewRespVO vo = new YwAuditOverviewRespVO();
        vo.setOrgPending(OrganizationInfoMapper.selectCountByStatus(0)); // 0=待审核
        vo.setWorkPending(AuthProductMapper != null ? AuthProductMapper.selectCountByStatus(0) : 0L);
        vo.setVolunteerUnassigned(AuthLevelMapper.selectCountByStatusAndAuditUserIsNull(4));
        return vo;
    }

    @Override
    public YwDashboardKpiRespVO getKpis() {
        LocalDate today = LocalDate.now();
        LocalDateTime begin = today.atStartOfDay();
        LocalDateTime end   = today.plusDays(1).atStartOfDay().minusNanos(1);

        YwDashboardKpiRespVO vo = new YwDashboardKpiRespVO();
        vo.setNewMembersToday(memberUserMapper.selectCountBetweenCreateTime(begin, end));
        vo.setVolunteerTotal(memberUserMapper.selectCountAll());
        // 认证口径：以 name 非空为“已实名”（若你有 realname_status=1，用那个更准）
        vo.setVolunteerCertified(memberUserMapper.selectCountVerifiedByNameNotBlank());
        // DAU：以 system_login_log 会员端成功登录去重（如你们另有活跃口径，改这里）
        if (memberUserMapper != null) {
            vo.setDauToday(memberUserMapper.countActiveBetween(begin,end));
        } else {
            vo.setDauToday(0L);
        }
        return vo;
    }

    @Override
    public List<YwDashboardTrendPointRespVO> getTrend(Integer days) {
        int d = (days == null || days <= 0) ? 7 : days;
        LocalDate startDate = LocalDate.now().minusDays(d - 1);
        LocalDateTime begin = startDate.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay(); // 明天 00:00，闭区间右边界

        // 预置 X 轴（yyyy-MM-dd）
        List<String> x = new ArrayList<>(d);
        for (int i = 0; i < d; i++) {
            x.add(startDate.plusDays(i).toString());
        }

        // 批量按天聚合（全部限定在 [begin, end)）
        Map<String, Long> newMembersMap = toDailyMap(memberUserMapper.selectDailyNewMembers(begin, end));   // create_time
        Map<String, Long> dauMap        = toDailyMap(memberUserMapper.selectDailyActiveByLoginDate(begin, end)); // login_date
        Map<String, Long> certsMap      = toDailyMap(memberUserMapper.selectDailyNewVerified(begin, end));  // 见下方说明

        // 组装结果
        return x.stream().map(date -> {
            YwDashboardTrendPointRespVO p = new YwDashboardTrendPointRespVO();
            p.setDate(date);
            p.setNewMembers(newMembersMap.getOrDefault(date, 0L));
            p.setDau(dauMap.getOrDefault(date, 0L));
            p.setCerts(certsMap.getOrDefault(date, 0L));
            return p;
        }).collect(Collectors.toList());
    }

    /** 期望 rows 形如：[{d: '2025-09-01', c: 123}, ...] */
    private Map<String, Long> toDailyMap(List<Map<String, Object>> rows) {
        if (rows == null) return Collections.emptyMap();
        Map<String, Long> map = new HashMap<>(rows.size());
        for (Map<String, Object> r : rows) {
            String day = Objects.toString(r.get("d"), null);
            Long cnt = r.get("c") == null ? 0L : ((Number) r.get("c")).longValue();
            if (day != null) map.put(day, cnt);
        }
        return map;
    }
    @Override
    public YwOrgProfileRespVO getCurrentOrgProfile() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        AdminUserDO user = AdminUserMapper.selectById(userId);

        Integer izvip = user != null && user.getIzvip() != null ? user.getIzvip() : 0;
        String honorCodes = user != null && user.getHonor() != null ? user.getHonor().trim() : "";
        List<YwOrgProfileRespVO.HonorItem> honors = new ArrayList<>();
        if (!honorCodes.isEmpty()) {
            // 1) 解析 codes
            Set<String> codes = Arrays.stream(honorCodes.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toCollection(LinkedHashSet::new)); // 保序 + 去重

            if (!codes.isEmpty()) {
                // 2) 拉 honor_category 字典（仅启用）
                List<DictDataDO> dictList = dictDataService.getDictDataList(
                        CommonStatusEnum.ENABLE.getStatus(), "honor_category");

                // 3) 按 value 建 map
                Map<String, DictDataDO> byValue = dictList.stream()
                        .collect(Collectors.toMap(d -> String.valueOf(d.getValue()), Function.identity(), (a, b)->a));

                // 4) 组装 honors
                for (String code : codes) {
                    DictDataDO dd = byValue.get(code);
                    if (dd == null) continue; // 容错：code 不存在
                    String remark = dd.getRemark();
                    String imageUrl = (remark != null && remark.startsWith("http")) ? remark : null;
                    honors.add(new YwOrgProfileRespVO.HonorItem(
                            dd.getId(),
                            String.valueOf(dd.getValue()),
                            dd.getLabel(),
                            imageUrl
                    ));
                }
            }
        }
        return new YwOrgProfileRespVO(izvip, honorCodes, honors);
    }

    @Override
    public YwOrgProfileRespVO getOrgProfileByName(String orgName) {

        AdminUserDO user = AdminUserMapper.selectByUsername(orgName);

        Integer izvip = user != null && user.getIzvip() != null ? user.getIzvip() : 0;
        String honorCodes = user != null && user.getHonor() != null ? user.getHonor().trim() : "";
        List<YwOrgProfileRespVO.HonorItem> honors = new ArrayList<>();
        if (!honorCodes.isEmpty()) {
            // 1) 解析 codes
            Set<String> codes = Arrays.stream(honorCodes.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toCollection(LinkedHashSet::new)); // 保序 + 去重

            if (!codes.isEmpty()) {
                // 2) 拉 honor_category 字典（仅启用）
                List<DictDataDO> dictList = dictDataService.getDictDataList(
                        CommonStatusEnum.ENABLE.getStatus(), "honor_category");

                // 3) 按 value 建 map
                Map<String, DictDataDO> byValue = dictList.stream()
                        .collect(Collectors.toMap(d -> String.valueOf(d.getValue()), Function.identity(), (a, b)->a));

                // 4) 组装 honors
                for (String code : codes) {
                    DictDataDO dd = byValue.get(code);
                    if (dd == null) continue; // 容错：code 不存在
                    String remark = dd.getRemark();
                    String imageUrl = (remark != null && remark.startsWith("http")) ? remark : null;
                    honors.add(new YwOrgProfileRespVO.HonorItem(
                            dd.getId(),
                            String.valueOf(dd.getValue()),
                            dd.getLabel(),
                            imageUrl
                    ));
                }
            }
        }
        return new YwOrgProfileRespVO(izvip, honorCodes, honors);
    }

    @Override
    public YwOrgVolunteerStatsRespVO getCurrentOrgVolunteerStats() {
        // 组织识别：按“当前登录用户名 = member_user.org_name”
        String orgName = SecurityFrameworkUtils.getLoginUserUsername();

        // 总数
        Integer total = Math.toIntExact(
                memberUserMapper.selectCount(
                        Wrappers.<MemberUserDO>lambdaQuery().eq(MemberUserDO::getOrgName, orgName)
                )
        );

        // 已认证数（⚠️按你们的实际字段改：authStatus=1 或 status=1 或 isCert=1）
        Integer certified = Math.toIntExact(
                memberUserMapper.selectCount(
                        Wrappers.<MemberUserDO>lambdaQuery()
                                .eq(MemberUserDO::getOrgName, orgName)
                                .eq(MemberUserDO::getStatus, 1) // ← 如果不是这个字段，请替换
                )
        );

        return new YwOrgVolunteerStatsRespVO(total, certified);
    }


}
