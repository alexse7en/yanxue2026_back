package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;

public interface YwVipTokenService {

    void initializeApprovedTokenBalance(YwVipInfoDO vipInfo);

    void validateGenerateToken(YwVipInfoDO vipInfo, int generateCount);

    int refreshAnnualTokenBalance();
}
