package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwOrgApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwOrgApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwOrgInfoMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgApplyAuditService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplySaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORG_APPLY_AUDIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORG_APPLY_DUPLICATE_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORG_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORG_APPLY_STATUS_NOT_SUBMITTED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORG_APPLY_UNIT_NAME_DUPLICATE;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

@Service
@Validated
public class YwOrgApplyAuditServiceImpl implements YwOrgApplyAuditService {

    private static final Integer APPLY_STATUS_DRAFT = 0;
    private static final Integer APPLY_STATUS_SUBMITTED = 1;
    private static final Integer APPLY_STATUS_APPROVED = 2;
    private static final Integer APPLY_STATUS_REJECTED = 3;

    @Resource
    private YwOrgApplyMapper orgApplyMapper;
    @Resource
    private YwOrgInfoMapper orgInfoMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private YwOrgApplyFileParser orgApplyFileParser;

    @Override
    public YwOrgApplyRespVO getMyOrgApply(String applyType) {
        return YwOrgApplyConvert.INSTANCE.convert(orgApplyMapper.selectLatestByUserAndType(getLoginUserId(), applyType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrgApplyDraft(YwOrgApplySaveReqVO reqVO) {
        YwOrgApplyDO data = saveOrUpdate(reqVO, APPLY_STATUS_DRAFT, false);
        return data.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitOrgApply(YwOrgApplySaveReqVO reqVO) {
        if (!StringUtils.hasText(reqVO.getUnitName())) {
            throw exception(YW_ORG_APPLY_AUDIT_STATUS_INVALID);
        }
        saveOrUpdate(reqVO, APPLY_STATUS_SUBMITTED, true);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YwOrgApplyRespVO parseOrgApply(YwOrgApplyParseReqVO reqVO) {
        Long userId = getLoginUserId();
        YwOrgApplyDO record = orgApplyMapper.selectLatestByUserAndType(userId, reqVO.getApplyType());
        if (record == null) {
            record = new YwOrgApplyDO();
            record.setUserId(userId);
            record.setVipinfoId(resolveVipinfoId(null));
            record.setApplyType(reqVO.getApplyType());
            record.setApplyStatus(APPLY_STATUS_DRAFT);
        }
        record.setFilePath(reqVO.getFilePath());
        record.setFileType(resolveFileType(reqVO));
        try {
            YwOrgApplySaveReqVO parsed = orgApplyFileParser.parse(reqVO);
            mergeParsedFields(record, parsed);
            validateUnitNameUniqueness(record.getUnitName(), record.getId());
            record.setParseStatus(1);
            record.setParseError(null);
        } catch (Exception e) {
            record.setParseStatus(2);
            record.setParseError(limitMsg(e.getMessage()));
        }
        if (record.getId() == null) {
            orgApplyMapper.insert(record);
        } else {
            orgApplyMapper.updateById(record);
        }
        return YwOrgApplyConvert.INSTANCE.convert(orgApplyMapper.selectById(record.getId()));
    }

    @Override
    public PageResult<YwOrgApplyRespVO> getOrgApplyPage(YwOrgApplyAuditPageReqVO reqVO) {
        return YwOrgApplyConvert.INSTANCE.convertPage(orgApplyMapper.selectAuditPage(reqVO));
    }

    @Override
    public YwOrgApplyRespVO getOrgApply(Long id) {
        YwOrgApplyDO apply = orgApplyMapper.selectById(id);
        if (apply == null) {
            throw exception(YW_ORG_APPLY_NOT_EXISTS);
        }
        return YwOrgApplyConvert.INSTANCE.convert(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditOrgApply(YwOrgApplyAuditReqVO reqVO) {
        YwOrgApplyDO apply = orgApplyMapper.selectById(reqVO.getId());
        if (apply == null) {
            throw exception(YW_ORG_APPLY_NOT_EXISTS);
        }
        if (!Objects.equals(apply.getApplyStatus(), APPLY_STATUS_SUBMITTED)) {
            throw exception(YW_ORG_APPLY_STATUS_NOT_SUBMITTED);
        }
        if (!Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)
                && !Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_REJECTED)) {
            throw exception(YW_ORG_APPLY_AUDIT_STATUS_INVALID);
        }

        apply.setApplyStatus(reqVO.getApplyStatus());
        apply.setAuditRemark(reqVO.getAuditRemark());
        apply.setAuditTime(LocalDateTime.now());
        apply.setAuditorId(SecurityFrameworkUtils.getLoginUserId());
        orgApplyMapper.updateById(apply);

        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            upsertOrgInfo(apply);
        }
    }

    private YwOrgApplyDO saveOrUpdate(YwOrgApplySaveReqVO reqVO, Integer applyStatus, boolean checkPending) {
        Long userId = getLoginUserId();
        Long vipinfoId = resolveVipinfoId(reqVO.getVipinfoId());

        if (checkPending) {
            YwOrgApplyDO pending = orgApplyMapper.selectLatestPendingByUserAndType(userId, reqVO.getApplyType());
            if (pending != null && (reqVO.getId() == null || !Objects.equals(pending.getId(), reqVO.getId()))) {
                throw exception(YW_ORG_APPLY_DUPLICATE_PENDING);
            }
        }

        if (reqVO.getId() == null) {
            YwOrgApplyDO data = BeanUtils.toBean(reqVO, YwOrgApplyDO.class);
            data.setUserId(userId);
            data.setVipinfoId(vipinfoId);
            data.setApplyStatus(applyStatus);
            validateUnitNameUniqueness(data.getUnitName(), null);
            orgApplyMapper.insert(data);
            return data;
        }

        YwOrgApplyDO exists = orgApplyMapper.selectById(reqVO.getId());
        if (exists == null || !Objects.equals(exists.getUserId(), userId)) {
            throw exception(YW_ORG_APPLY_NOT_EXISTS);
        }
        YwOrgApplyDO update = BeanUtils.toBean(reqVO, YwOrgApplyDO.class);
        update.setId(exists.getId());
        update.setUserId(exists.getUserId());
        update.setVipinfoId(vipinfoId);
        update.setApplyStatus(applyStatus);
        update.setAuditRemark(null);
        update.setAuditTime(null);
        update.setAuditorId(null);
        validateUnitNameUniqueness(update.getUnitName(), update.getId());
        orgApplyMapper.updateById(update);
        return orgApplyMapper.selectById(exists.getId());
    }

    private void validateUnitNameUniqueness(String unitName, Long selfId) {
        if (!StringUtils.hasText(unitName)) {
            return;
        }
        YwOrgApplyDO exists = orgApplyMapper.selectLatestNotApprovedByUnitName(unitName);
        if (exists != null && (selfId == null || !Objects.equals(exists.getId(), selfId))) {
            throw exception(YW_ORG_APPLY_UNIT_NAME_DUPLICATE);
        }
    }

    private void mergeParsedFields(YwOrgApplyDO target, YwOrgApplySaveReqVO source) {
        if (source.getUnitName() != null) target.setUnitName(source.getUnitName());
        if (source.getDestinationName() != null) target.setDestinationName(source.getDestinationName());
        if (source.getBaseTheme() != null) target.setBaseTheme(source.getBaseTheme());
        if (source.getUnitType() != null) target.setUnitType(source.getUnitType());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
        if (source.getContactPerson() != null) target.setContactPerson(source.getContactPerson());
        if (source.getContactPhone() != null) target.setContactPhone(source.getContactPhone());
        if (source.getContactEmail() != null) target.setContactEmail(source.getContactEmail());
        if (source.getAreaCover() != null) target.setAreaCover(source.getAreaCover());
        if (source.getAreaBuild() != null) target.setAreaBuild(source.getAreaBuild());
        if (source.getAreaOpen() != null) target.setAreaOpen(source.getAreaOpen());
        if (source.getOpenDaysPerYear() != null) target.setOpenDaysPerYear(source.getOpenDaysPerYear());
        if (source.getEstimatedStudentsPerYear() != null) target.setEstimatedStudentsPerYear(source.getEstimatedStudentsPerYear());
        if (source.getMaxStudentsPerTime() != null) target.setMaxStudentsPerTime(source.getMaxStudentsPerTime());
        if (source.getFundInvestment() != null) target.setFundInvestment(source.getFundInvestment());
        if (source.getEstablishmentDate() != null) target.setEstablishmentDate(source.getEstablishmentDate());
        if (source.getLegalPerson() != null) target.setLegalPerson(source.getLegalPerson());
        if (source.getLegalPhone() != null) target.setLegalPhone(source.getLegalPhone());
        if (source.getBusinessLicenseNo() != null) target.setBusinessLicenseNo(source.getBusinessLicenseNo());
        if (source.getTravelLicenseNo() != null) target.setTravelLicenseNo(source.getTravelLicenseNo());
        if (source.getHasStudyDepartment() != null) target.setHasStudyDepartment(source.getHasStudyDepartment());
        if (source.getStudyDeptStaffCount() != null) target.setStudyDeptStaffCount(source.getStudyDeptStaffCount());
        if (source.getFulltimeTutorCount() != null) target.setFulltimeTutorCount(source.getFulltimeTutorCount());
        if (source.getParttimeTutorCount() != null) target.setParttimeTutorCount(source.getParttimeTutorCount());
        if (source.getUnitProfile() != null) target.setUnitProfile(source.getUnitProfile());
    }

    private String limitMsg(String msg) {
        if (!StringUtils.hasText(msg)) {
            return "文件解析失败";
        }
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
    }

    private String resolveFileType(YwOrgApplyParseReqVO reqVO) {
        if (StringUtils.hasText(reqVO.getFileType())) {
            return reqVO.getFileType().trim().toLowerCase();
        }
        String filePath = reqVO.getFilePath();
        int index = filePath.lastIndexOf('.');
        if (index < 0 || index == filePath.length() - 1) {
            return "";
        }
        return filePath.substring(index + 1).toLowerCase();
    }

    private void upsertOrgInfo(YwOrgApplyDO apply) {
        YwOrgInfoDO orgInfo = orgInfoMapper.selectByApplyId(apply.getId());
        if (orgInfo == null) {
            orgInfo = orgInfoMapper.selectByUserIdAndOrgType(apply.getUserId(), apply.getApplyType());
        }
        if (orgInfo == null) {
            orgInfo = new YwOrgInfoDO();
            orgInfo.setUserId(apply.getUserId());
            orgInfo.setVipinfoId(apply.getVipinfoId());
        }
        orgInfo.setApplyId(apply.getId());
        mergeApplyToOrgInfo(orgInfo, apply);
        if (orgInfo.getId() == null) {
            orgInfoMapper.insert(orgInfo);
        } else {
            orgInfoMapper.updateById(orgInfo);
        }
    }

    private void mergeApplyToOrgInfo(YwOrgInfoDO target, YwOrgApplyDO source) {
        if (source.getApplyType() != null) target.setOrgType(source.getApplyType());
        if (source.getUnitName() != null) target.setUnitName(source.getUnitName());
        if (source.getDestinationName() != null) target.setDestinationName(source.getDestinationName());
        if (source.getBaseTheme() != null) target.setBaseTheme(source.getBaseTheme());
        if (source.getUnitType() != null) target.setUnitType(source.getUnitType());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
        if (source.getContactPerson() != null) target.setContactPerson(source.getContactPerson());
        if (source.getContactPhone() != null) target.setContactPhone(source.getContactPhone());
        if (source.getContactEmail() != null) target.setContactEmail(source.getContactEmail());
        if (source.getAreaCover() != null) target.setAreaCover(source.getAreaCover());
        if (source.getAreaBuild() != null) target.setAreaBuild(source.getAreaBuild());
        if (source.getAreaOpen() != null) target.setAreaOpen(source.getAreaOpen());
        if (source.getOpenDaysPerYear() != null) target.setOpenDaysPerYear(source.getOpenDaysPerYear());
        if (source.getEstimatedStudentsPerYear() != null) target.setEstimatedStudentsPerYear(source.getEstimatedStudentsPerYear());
        if (source.getMaxStudentsPerTime() != null) target.setMaxStudentsPerTime(source.getMaxStudentsPerTime());
        if (source.getFundInvestment() != null) target.setFundInvestment(source.getFundInvestment());
        if (source.getEstablishmentDate() != null) target.setEstablishmentDate(source.getEstablishmentDate());
        if (source.getLegalPerson() != null) target.setLegalPerson(source.getLegalPerson());
        if (source.getLegalPhone() != null) target.setLegalPhone(source.getLegalPhone());
        if (source.getBusinessLicenseNo() != null) target.setBusinessLicenseNo(source.getBusinessLicenseNo());
        if (source.getTravelLicenseNo() != null) target.setTravelLicenseNo(source.getTravelLicenseNo());
        if (source.getHasStudyDepartment() != null) target.setHasStudyDepartment(source.getHasStudyDepartment());
        if (source.getStudyDeptStaffCount() != null) target.setStudyDeptStaffCount(source.getStudyDeptStaffCount());
        if (source.getFulltimeTutorCount() != null) target.setCertTutorCount(source.getFulltimeTutorCount());
        if (source.getParttimeTutorCount() != null) target.setParttimeTutorCount(source.getParttimeTutorCount());
        if (source.getUnitProfile() != null) target.setUnitProfile(source.getUnitProfile());
    }

    private Long resolveVipinfoId(Long vipinfoId) {
        Long userId = getLoginUserId();
        if (vipinfoId != null) {
            YwVipInfoDO vipInfo = vipInfoMapper.selectById(vipinfoId);
            if (vipInfo == null || (vipInfo.getUserId() != null && !Objects.equals(vipInfo.getUserId(), userId))) {
                throw exception(YW_VIPINFO_NOT_EXISTS);
            }
            return vipinfoId;
        }
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(userId);
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        return vipInfo.getId();
    }

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }
}


