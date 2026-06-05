package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwTutorCertDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwTutorCertMapper;
import cn.iocoder.yudao.module.yw.service.impl.YwTutorCertImageGenerator;
import cn.iocoder.yudao.module.yw.service.vip.YwTutorCertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

@Service
@Validated
@Slf4j
public class YwTutorCertServiceImpl implements YwTutorCertService {

    private static final int DEFAULT_BATCH_SIZE = 50;

    @Resource
    private YwTutorCertMapper tutorCertMapper;
    @Resource
    private YwTutorCertImageGenerator tutorCertImageGenerator;

    @Override
    public String resolveTutorCertImage(YwTutorCertDO cert) {
        if (StringUtils.hasText(cert.getCertpic())) {
            return cert.getCertpic();
        }
        String certpic = tutorCertImageGenerator.generateAndUpload(cert);
        tutorCertMapper.updateCertpic(cert.getId(), certpic);
        cert.setCertpic(certpic);
        return certpic;
    }

    @Override
    public int generateMissingCertImages(Integer limit) {
        int batchSize = limit != null && limit > 0 ? limit : DEFAULT_BATCH_SIZE;
        List<YwTutorCertDO> list = tutorCertMapper.selectMissingCertpicList(batchSize);
        int successCount = 0;
        for (YwTutorCertDO cert : list) {
            try {
                resolveTutorCertImage(cert);
                successCount++;
            } catch (Exception e) {
                log.warn("[generateMissingCertImages][导师证书图片生成失败，id={}, certificateNo={}, reason={}]",
                        cert.getId(), cert.getCertificateNo(), e.getMessage(), e);
            }
        }
        return successCount;
    }
}
