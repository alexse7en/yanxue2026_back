package cn.iocoder.yudao.module.yw.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.yw.service.vip.YwVipTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class YwVipTokenRefreshJob implements JobHandler {

    @Resource
    private YwVipTokenService vipTokenService;

    @Scheduled(cron = "0 0 0 1 1 ?")
    @TenantIgnore
    public void scheduledRefresh() {
        doRefresh();
    }

    @Override
    @TenantIgnore
    public String execute(String param) {
        int count = doRefresh();
        return String.format("年度补充会员证书生成次数完成 %s 条", count);
    }

    private int doRefresh() {
        int count = vipTokenService.refreshAnnualTokenBalance();
        log.info("[doRefresh][年度补充会员证书生成次数完成，数量 ({})]", count);
        return count;
    }
}
