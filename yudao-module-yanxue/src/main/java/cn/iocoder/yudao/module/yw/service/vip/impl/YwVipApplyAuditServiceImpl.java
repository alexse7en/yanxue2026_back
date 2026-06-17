package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.api.permission.PermissionApi;
import cn.iocoder.yudao.module.yw.convert.vip.YwVipApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwVipApplyAuditService;
import cn.iocoder.yudao.module.yw.service.vip.YwVipTokenService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditRespVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_APPLY_AUDIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_APPLY_MEMBER_NO_DUPLICATE;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_APPLY_MEMBER_NO_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_APPLY_MEMBER_NO_REQUIRED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIP_APPLY_STATUS_NOT_SUBMITTED;

@Service
@Validated
public class YwVipApplyAuditServiceImpl implements YwVipApplyAuditService {

    private static final Integer APPLY_STATUS_SUBMITTED = 1;
    private static final Integer APPLY_STATUS_APPROVED = 10;
    private static final Integer APPLY_STATUS_REJECTED = 11;
    private static final Integer VIP_STATUS_ENABLED = 1;
    private static final String VIP_USER_ROLE_CODE = "4";
    private static final String MEMBER_LEVEL_VICE_PRESIDENT = "\u526F\u4F1A\u957F\u5355\u4F4D";
    private static final String MEMBER_LEVEL_VICE_PRESIDENT_SHORT = "\u526F\u4F1A\u957F";
    private static final String MEMBER_LEVEL_EXECUTIVE_DIRECTOR = "\u5E38\u52A1\u7406\u4E8B\u5355\u4F4D";
    private static final String MEMBER_LEVEL_EXECUTIVE_DIRECTOR_SHORT = "\u5E38\u52A1\u7406\u4E8B";
    private static final String MEMBER_LEVEL_DIRECTOR = "\u7406\u4E8B\u5355\u4F4D";
    private static final String MEMBER_LEVEL_DIRECTOR_SHORT = "\u7406\u4E8B";
    private static final String MEMBER_LEVEL_MEMBER = "\u4F1A\u5458\u5355\u4F4D";
    private static final String MEMBER_LEVEL_NORMAL_MEMBER = "\u666E\u901A\u4F1A\u5458";
    private static final String MEMBER_LEVEL_NORMAL = "\u666E\u901A";
    private static final Pattern MEMBER_NO_PATTERN = Pattern.compile("^GDSTA-\\d{4}([FCLP])\\d{3}$");
    private static final Pattern MEMBER_NO_SUFFIX_PATTERN = Pattern.compile("^\\d{3}$");

    @Resource
    private YwVipApplyMapper vipApplyMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private PermissionApi permissionApi;
    @Resource
    private YwVipTokenService vipTokenService;

    @Override
    public PageResult<YwVipApplyAuditRespVO> getVipApplyPage(YwVipApplyAuditPageReqVO reqVO) {
        return YwVipApplyConvert.INSTANCE.convertAuditPage(vipApplyMapper.selectAuditPage(reqVO));
    }

    @Override
    public YwVipApplyAuditRespVO getVipApply(Long id) {
        YwVipApplyDO apply = vipApplyMapper.selectById(id);
        if (apply == null) {
            throw exception(YW_VIP_APPLY_NOT_EXISTS);
        }
        return YwVipApplyConvert.INSTANCE.convertAudit(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditVipApply(YwVipApplyAuditReqVO reqVO) {
        YwVipApplyDO apply = vipApplyMapper.selectById(reqVO.getId());
        if (apply == null) {
            throw exception(YW_VIP_APPLY_NOT_EXISTS);
        }
        if (!Objects.equals(apply.getApplyStatus(), APPLY_STATUS_SUBMITTED)) {
            throw exception(YW_VIP_APPLY_STATUS_NOT_SUBMITTED);
        }
        if (!Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)
                && !Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_REJECTED)) {
            throw exception(YW_VIP_APPLY_AUDIT_STATUS_INVALID);
        }

        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            validateMemberNo(apply.getApplyLevel(), reqVO);
            validateMemberNoDuplicate(apply.getUserId(), reqVO.getMemberNo());
            upsertVipInfo(apply, reqVO.getMemberNo());
            permissionApi.appendUserRoleByCode(apply.getUserId(), Collections.singleton(VIP_USER_ROLE_CODE));
            apply.setMemberNo(reqVO.getMemberNo());
        }

        apply.setApplyStatus(reqVO.getApplyStatus());
        apply.setAuditRemark(reqVO.getAuditRemark());
        apply.setAuditTime(LocalDateTime.now());
        apply.setAuditorId(SecurityFrameworkUtils.getLoginUserId());
        vipApplyMapper.updateById(apply);
    }

    private void validateMemberNo(String applyLevel, YwVipApplyAuditReqVO reqVO) {
        if (!StringUtils.hasText(reqVO.getMemberNo())) {
            throw exception(YW_VIP_APPLY_MEMBER_NO_REQUIRED);
        }
        Matcher matcher = MEMBER_NO_PATTERN.matcher(reqVO.getMemberNo());
        if (!matcher.matches()) {
            throw exception(YW_VIP_APPLY_MEMBER_NO_INVALID);
        }
        String expectedLevelCode = resolveMemberNoLevelCode(applyLevel);
        if (!Objects.equals(expectedLevelCode, matcher.group(1))) {
            throw exception(YW_VIP_APPLY_MEMBER_NO_INVALID);
        }
        if (StringUtils.hasText(reqVO.getMemberNoSuffix())
                && !MEMBER_NO_SUFFIX_PATTERN.matcher(reqVO.getMemberNoSuffix()).matches()) {
            throw exception(YW_VIP_APPLY_MEMBER_NO_INVALID);
        }
    }

    private String resolveMemberNoLevelCode(String applyLevel) {
        if (!StringUtils.hasText(applyLevel)) {
            throw exception(YW_VIP_APPLY_MEMBER_NO_INVALID);
        }
        switch (applyLevel.trim()) {
            case MEMBER_LEVEL_VICE_PRESIDENT:
            case MEMBER_LEVEL_VICE_PRESIDENT_SHORT:
                return "F";
            case MEMBER_LEVEL_EXECUTIVE_DIRECTOR:
            case MEMBER_LEVEL_EXECUTIVE_DIRECTOR_SHORT:
                return "C";
            case MEMBER_LEVEL_DIRECTOR:
            case MEMBER_LEVEL_DIRECTOR_SHORT:
                return "L";
            case MEMBER_LEVEL_MEMBER:
            case MEMBER_LEVEL_NORMAL_MEMBER:
            case MEMBER_LEVEL_NORMAL:
                return "P";
            default:
                throw exception(YW_VIP_APPLY_MEMBER_NO_INVALID);
        }
    }

    private void validateMemberNoDuplicate(Long userId, String memberNo) {
        YwVipInfoDO exists = vipInfoMapper.selectByMemberNo(memberNo);
        if (exists != null && !Objects.equals(exists.getUserId(), userId)) {
            throw exception(YW_VIP_APPLY_MEMBER_NO_DUPLICATE);
        }
    }

    private void upsertVipInfo(YwVipApplyDO apply, String memberNo) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(apply.getUserId());
        if (vipInfo == null) {
            vipInfo = new YwVipInfoDO();
            vipInfo.setUserId(apply.getUserId());
        }
        vipInfo.setApplyId(apply.getId());
        vipInfo.setMemberNo(memberNo);
        vipInfo.setCompanyName(apply.getCompanyName());
        vipInfo.setCompanyAddress(apply.getCompanyAddress());
        vipInfo.setCompanyPhone(apply.getCompanyPhone());
        vipInfo.setWebsite(apply.getWebsite());
        vipInfo.setEstablishedDate(apply.getEstablishedDate());
        vipInfo.setBusinessScope(apply.getBusinessScope());
        vipInfo.setCompanyIntro(apply.getCompanyIntro());
        vipInfo.setCompanyType(apply.getCompanyType());
        vipInfo.setMemberLevel(apply.getApplyLevel());
        vipInfo.setContactName(apply.getContactName());
        vipInfo.setContactPhone(apply.getContactPhone());
        vipInfo.setRepName(apply.getRepName());
        vipInfo.setRepPosition(apply.getRepPosition());
        vipInfo.setRepPhone(apply.getRepPhone());
        vipInfo.setRepEmail(apply.getRepEmail());
        vipTokenService.initializeApprovedTokenBalance(vipInfo);
        vipInfo.setStatus(VIP_STATUS_ENABLED);
        if (vipInfo.getId() == null) {
            vipInfoMapper.insert(vipInfo);
            return;
        }
        vipInfoMapper.updateById(vipInfo);
    }
}
