package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwTutorCertDO;

public interface YwTutorCertService {

    String resolveTutorCertImage(YwTutorCertDO cert);

    int generateMissingCertImages(Integer limit);
}
