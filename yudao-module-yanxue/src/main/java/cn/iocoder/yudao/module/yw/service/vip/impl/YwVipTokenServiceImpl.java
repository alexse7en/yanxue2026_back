package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwVipTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_TOKEN_RULE_NOT_EXISTS;

@Service
@Validated
public class YwVipTokenServiceImpl implements YwVipTokenService {

    private static final String MEMBER_LEVEL_VICE_PRESIDENT = "\u526F\u4F1A\u957F\u5355\u4F4D";
    private static final String MEMBER_LEVEL_EXECUTIVE_DIRECTOR = "\u5E38\u52A1\u7406\u4E8B\u5355\u4F4D";
    private static final String MEMBER_LEVEL_DIRECTOR = "\u7406\u4E8B\u5355\u4F4D";
    private static final String MEMBER_LEVEL_MEMBER = "\u4F1A\u5458\u5355\u4F4D";

    @Resource
    private YwVipInfoMapper vipInfoMapper;

    @Override
    public void initializeApprovedTokenBalance(YwVipInfoDO vipInfo) {
        BigDecimal quota = resolveAnnualQuota(vipInfo.getMemberLevel());
        vipInfo.setTokenBalance(quota);
        vipInfo.setLastTokenRefreshYear(LocalDate.now().getYear());
    }

    @Override
    public void validateGenerateToken(YwVipInfoDO vipInfo, int generateCount) {
        if (generateCount <= 0) {
            return;
        }
        resolveAnnualQuota(vipInfo.getMemberLevel());
        BigDecimal balance = vipInfo.getTokenBalance() == null ? BigDecimal.ZERO : vipInfo.getTokenBalance();
        BigDecimal required = BigDecimal.valueOf(generateCount);
        if (balance.compareTo(required) < 0) {
            throw exception(YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int refreshAnnualTokenBalance() {
        int currentYear = LocalDate.now().getYear();
        List<YwVipInfoDO> vipInfos = vipInfoMapper.selectEnabledForAnnualRefresh();
        int refreshedCount = 0;
        for (YwVipInfoDO vipInfo : vipInfos) {
            if (vipInfo.getLastTokenRefreshYear() != null && vipInfo.getLastTokenRefreshYear() >= currentYear) {
                continue;
            }
            BigDecimal quota = resolveAnnualQuota(vipInfo.getMemberLevel());
            BigDecimal balance = vipInfo.getTokenBalance() == null ? BigDecimal.ZERO : vipInfo.getTokenBalance();
            vipInfo.setTokenBalance(balance.add(quota));
            vipInfo.setLastTokenRefreshYear(currentYear);
            vipInfoMapper.updateById(vipInfo);
            refreshedCount++;
        }
        return refreshedCount;
    }

    private BigDecimal resolveAnnualQuota(String memberLevel) {
        if (!StringUtils.hasText(memberLevel)) {
            throw exception(YW_VIP_TOKEN_RULE_NOT_EXISTS);
        }
        switch (memberLevel.trim()) {
            case MEMBER_LEVEL_VICE_PRESIDENT:
                return BigDecimal.valueOf(2000L);
            case MEMBER_LEVEL_EXECUTIVE_DIRECTOR:
                return BigDecimal.valueOf(1000L);
            case MEMBER_LEVEL_DIRECTOR:
                return BigDecimal.valueOf(500L);
            case MEMBER_LEVEL_MEMBER:
                return BigDecimal.valueOf(200L);
            default:
                throw exception(YW_VIP_TOKEN_RULE_NOT_EXISTS);
        }
    }
}
