package cn.iocoder.yudao.module.yw.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.yw.service.vip.YwTutorCertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class YwTutorCertImageGenerateJob implements JobHandler {

    @Resource
    private YwTutorCertService tutorCertService;

    @Value("${yw.cert.tutor.generate-batch-size:50}")
    private Integer generateBatchSize;

    @Scheduled(cron = "${yw.cert.tutor.generate-cron:0 */10 2-5 * * ?}")
    @TenantIgnore
    public void scheduledGenerate() {
        doGenerate(generateBatchSize);
    }

    @Override
    @TenantIgnore
    public String execute(String param) {
        int limit = parseLimit(param);
        int count = doGenerate(limit);
        return String.format("导师证书图片补生成完成 %s 张", count);
    }

    private int doGenerate(Integer limit) {
        int count = tutorCertService.generateMissingCertImages(limit);
        log.info("[doGenerate][导师证书图片补生成完成，数量({})]", count);
        return count;
    }

    private int parseLimit(String param) {
        try {
            return param == null || param.trim().isEmpty() ? generateBatchSize : Integer.parseInt(param.trim());
        } catch (NumberFormatException ignored) {
            return generateBatchSize;
        }
    }
}
