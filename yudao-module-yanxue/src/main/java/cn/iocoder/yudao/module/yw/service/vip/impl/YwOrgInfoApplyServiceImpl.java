package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwOrgInfoApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwOrgInfoApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwOrgInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgInfoApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplySaveReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORGINFO_APPLY_AUDIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORGINFO_APPLY_DUPLICATE_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORGINFO_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORGINFO_APPLY_STATUS_NOT_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORGINFO_NOT_EXISTS;

@Service
@Validated
public class YwOrgInfoApplyServiceImpl implements YwOrgInfoApplyService {

    private static final Integer APPLY_STATUS_PENDING = 0;
    private static final Integer APPLY_STATUS_APPROVED = 1;
    private static final Integer APPLY_STATUS_REJECTED = 2;

    @Resource
    private YwOrgInfoMapper orgInfoMapper;
    @Resource
    private YwOrgInfoApplyMapper orgInfoApplyMapper;

    @Override
    public List<YwOrgInfoRespVO> listMyOrgInfo() {
        List<YwOrgInfoDO> orgInfos = orgInfoMapper.selectListByUserId(getLoginUserId());
        return YwOrgInfoApplyConvert.INSTANCE.convertOrgInfoList(orgInfos);
    }

    @Override
    public YwOrgInfoApplyRespVO getLatestMyOrgInfoApply(Long orginfoId) {
        YwOrgInfoApplyDO apply = orgInfoApplyMapper.selectLatestByUserAndOrginfoId(getLoginUserId(), orginfoId);
        if (apply == null) {
            return null;
        }
        return enrichApplyResp(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwOrgInfoDO orgInfo = requireOwnedOrgInfo(reqVO.getOrginfoId(), userId);
        YwOrgInfoApplyDO exists = orgInfoApplyMapper.selectLatestByUserAndOrginfoId(userId, orgInfo.getId());
        if (exists != null && Objects.equals(exists.getApplyStatus(), APPLY_STATUS_PENDING)) {
            throw exception(YW_ORGINFO_APPLY_DUPLICATE_PENDING);
        }
        YwOrgInfoApplyDO target = exists != null ? exists : new YwOrgInfoApplyDO();
        fillApplySnapshotFromOrgInfo(target, orgInfo);
        target.setOrginfoId(orgInfo.getId());
        target.setUserId(userId);
        target.setApplyStatus(APPLY_STATUS_PENDING);
        target.setAuditRemark(null);
        target.setAuditTime(null);
        target.setAuditorId(null);
        mergeApplyFields(target, reqVO);
        if (target.getId() == null) {
            orgInfoApplyMapper.insert(target);
        } else {
            orgInfoApplyMapper.updateById(target);
        }
        return target.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwOrgInfoApplyDO target = reqVO.getId() != null ? orgInfoApplyMapper.selectById(reqVO.getId())
                : orgInfoApplyMapper.selectLatestByUserAndOrginfoId(userId, reqVO.getOrginfoId());
        if (target == null || !Objects.equals(target.getUserId(), userId)) {
            throw exception(YW_ORGINFO_APPLY_NOT_EXISTS);
        }
        YwOrgInfoDO orgInfo = requireOwnedOrgInfo(reqVO.getOrginfoId() != null ? reqVO.getOrginfoId() : target.getOrginfoId(), userId);
        YwOrgInfoApplyDO pending = orgInfoApplyMapper.selectPendingByUserAndOrginfoId(userId, orgInfo.getId());
        if (pending != null && !Objects.equals(pending.getId(), target.getId())) {
            throw exception(YW_ORGINFO_APPLY_DUPLICATE_PENDING);
        }
        if (!Objects.equals(target.getApplyStatus(), APPLY_STATUS_PENDING)) {
            fillApplySnapshotFromOrgInfo(target, orgInfo);
        }
        target.setOrginfoId(orgInfo.getId());
        target.setApplyStatus(APPLY_STATUS_PENDING);
        target.setAuditRemark(null);
        target.setAuditTime(null);
        target.setAuditorId(null);
        mergeApplyFields(target, reqVO);
        orgInfoApplyMapper.updateById(target);
    }

    @Override
    public PageResult<YwOrgInfoApplyRespVO> getOrgInfoApplyPage(YwOrgInfoApplyPageReqVO reqVO) {
        PageResult<YwOrgInfoApplyDO> pageResult = orgInfoApplyMapper.selectPage(reqVO);
        List<YwOrgInfoApplyRespVO> list = new ArrayList<>();
        for (YwOrgInfoApplyDO item : pageResult.getList()) {
            list.add(enrichApplyResp(item));
        }
        return new PageResult<>(list, pageResult.getTotal());
    }

    @Override
    public YwOrgInfoApplyRespVO getOrgInfoApply(Long id) {
        YwOrgInfoApplyDO apply = orgInfoApplyMapper.selectById(id);
        if (apply == null) {
            throw exception(YW_ORGINFO_APPLY_NOT_EXISTS);
        }
        return enrichApplyResp(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditOrgInfoApply(YwOrgInfoApplyAuditReqVO reqVO) {
        YwOrgInfoApplyDO apply = orgInfoApplyMapper.selectById(reqVO.getId());
        if (apply == null) {
            throw exception(YW_ORGINFO_APPLY_NOT_EXISTS);
        }
        if (!Objects.equals(apply.getApplyStatus(), APPLY_STATUS_PENDING)) {
            throw exception(YW_ORGINFO_APPLY_STATUS_NOT_PENDING);
        }
        if (!Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)
                && !Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_REJECTED)) {
            throw exception(YW_ORGINFO_APPLY_AUDIT_STATUS_INVALID);
        }

        apply.setApplyStatus(reqVO.getApplyStatus());
        apply.setAuditRemark(reqVO.getAuditRemark());
        apply.setAuditTime(LocalDateTime.now());
        apply.setAuditorId(SecurityFrameworkUtils.getLoginUserId());
        orgInfoApplyMapper.updateById(apply);

        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            YwOrgInfoDO orgInfo = orgInfoMapper.selectById(apply.getOrginfoId());
            if (orgInfo == null) {
                throw exception(YW_ORGINFO_NOT_EXISTS);
            }
            mergeApplyToOrgInfo(orgInfo, apply);
            orgInfo.setApplyId(apply.getId());
            orgInfoMapper.updateById(orgInfo);
        }
    }

    private YwOrgInfoDO requireOwnedOrgInfo(Long orginfoId, Long userId) {
        YwOrgInfoDO orgInfo = orginfoId != null ? orgInfoMapper.selectById(orginfoId) : null;
        if (orgInfo == null) {
            throw exception(YW_ORGINFO_NOT_EXISTS);
        }
        if (orgInfo.getUserId() != null && !Objects.equals(orgInfo.getUserId(), userId)) {
            throw exception(YW_ORGINFO_NOT_EXISTS);
        }
        return orgInfo;
    }

    private YwOrgInfoApplyRespVO enrichApplyResp(YwOrgInfoApplyDO apply) {
        YwOrgInfoApplyRespVO respVO = YwOrgInfoApplyConvert.INSTANCE.convertApply(apply);
        if (respVO == null) {
            return null;
        }
        return respVO;
    }

    private void fillApplySnapshotFromOrgInfo(YwOrgInfoApplyDO target, YwOrgInfoDO orgInfo) {
        target.setUnitName(orgInfo.getUnitName());
        target.setDestinationName(orgInfo.getDestinationName());
        target.setBaseTheme(orgInfo.getBaseTheme());
        target.setUnitType(orgInfo.getUnitType());
        target.setAddress(orgInfo.getAddress());
        target.setContactPerson(orgInfo.getContactPerson());
        target.setContactPhone(orgInfo.getContactPhone());
        target.setContactEmail(orgInfo.getContactEmail());
        target.setAreaCover(orgInfo.getAreaCover());
        target.setAreaBuild(orgInfo.getAreaBuild());
        target.setAreaOpen(orgInfo.getAreaOpen());
        target.setOpenDaysPerYear(orgInfo.getOpenDaysPerYear());
        target.setEstimatedStudentsPerYear(orgInfo.getEstimatedStudentsPerYear());
        target.setMaxStudentsPerTime(orgInfo.getMaxStudentsPerTime());
        target.setFundInvestment(orgInfo.getFundInvestment());
        target.setBaseType(orgInfo.getBaseType());
        target.setBaseTypeOther(orgInfo.getBaseTypeOther());
        target.setAreaLand(orgInfo.getAreaLand());
        target.setAreaBuilding(orgInfo.getAreaBuilding());
        target.setAreaAvailable(orgInfo.getAreaAvailable());
        target.setHasAccommodation(orgInfo.getHasAccommodation());
        target.setAccommodationCapacity(orgInfo.getAccommodationCapacity());
        target.setBaseFeature(orgInfo.getBaseFeature());
        target.setEstablishmentDate(orgInfo.getEstablishmentDate());
        target.setLegalPerson(orgInfo.getLegalPerson());
        target.setLegalPhone(orgInfo.getLegalPhone());
        target.setBusinessLicenseNo(orgInfo.getBusinessLicenseNo());
        target.setTravelLicenseNo(orgInfo.getTravelLicenseNo());
        target.setHasStudyDepartment(orgInfo.getHasStudyDepartment());
        target.setStudyDeptStaffCount(orgInfo.getStudyDeptStaffCount());
        target.setFulltimeTutorCount(orgInfo.getCertTutorCount());
        target.setParttimeTutorCount(orgInfo.getParttimeTutorCount());
        target.setInstitutionType(orgInfo.getInstitutionType());
        target.setInstitutionTypeOther(orgInfo.getInstitutionTypeOther());
        target.setProductFeature(orgInfo.getProductFeature());
        target.setAnnualVisitorsRange(orgInfo.getAnnualVisitorsRange());
        target.setCoverImage(orgInfo.getCoverImage());
        target.setLogo(orgInfo.getLogo());
        target.setGallery(orgInfo.getGallery());
        target.setUnitProfile(orgInfo.getUnitProfile());
    }

    private void mergeApplyFields(YwOrgInfoApplyDO target, YwOrgInfoApplySaveReqVO reqVO) {
        if (reqVO.getUnitName() != null) {
            target.setUnitName(reqVO.getUnitName());
        }
        if (reqVO.getDestinationName() != null) {
            target.setDestinationName(reqVO.getDestinationName());
        }
        if (reqVO.getBaseTheme() != null) {
            target.setBaseTheme(reqVO.getBaseTheme());
        }
        if (reqVO.getUnitType() != null) {
            target.setUnitType(reqVO.getUnitType());
        }
        if (reqVO.getAddress() != null) {
            target.setAddress(reqVO.getAddress());
        }
        if (reqVO.getContactPerson() != null) {
            target.setContactPerson(reqVO.getContactPerson());
        }
        if (reqVO.getContactPhone() != null) {
            target.setContactPhone(reqVO.getContactPhone());
        }
        if (reqVO.getContactEmail() != null) {
            target.setContactEmail(reqVO.getContactEmail());
        }
        if (reqVO.getAreaCover() != null) {
            target.setAreaCover(reqVO.getAreaCover());
        }
        if (reqVO.getAreaBuild() != null) {
            target.setAreaBuild(reqVO.getAreaBuild());
        }
        if (reqVO.getAreaOpen() != null) {
            target.setAreaOpen(reqVO.getAreaOpen());
        }
        if (reqVO.getOpenDaysPerYear() != null) {
            target.setOpenDaysPerYear(reqVO.getOpenDaysPerYear());
        }
        if (reqVO.getEstimatedStudentsPerYear() != null) {
            target.setEstimatedStudentsPerYear(reqVO.getEstimatedStudentsPerYear());
        }
        if (reqVO.getMaxStudentsPerTime() != null) {
            target.setMaxStudentsPerTime(reqVO.getMaxStudentsPerTime());
        }
        if (reqVO.getFundInvestment() != null) {
            target.setFundInvestment(reqVO.getFundInvestment());
        }
        if (reqVO.getBaseType() != null) {
            target.setBaseType(reqVO.getBaseType());
        }
        if (reqVO.getBaseTypeOther() != null) {
            target.setBaseTypeOther(reqVO.getBaseTypeOther());
        }
        if (reqVO.getAreaLand() != null) {
            target.setAreaLand(reqVO.getAreaLand());
        }
        if (reqVO.getAreaBuilding() != null) {
            target.setAreaBuilding(reqVO.getAreaBuilding());
        }
        if (reqVO.getAreaAvailable() != null) {
            target.setAreaAvailable(reqVO.getAreaAvailable());
        }
        if (reqVO.getHasAccommodation() != null) {
            target.setHasAccommodation(reqVO.getHasAccommodation());
        }
        if (reqVO.getAccommodationCapacity() != null) {
            target.setAccommodationCapacity(reqVO.getAccommodationCapacity());
        }
        if (reqVO.getBaseFeature() != null) {
            target.setBaseFeature(reqVO.getBaseFeature());
        }
        if (reqVO.getEstablishmentDate() != null) {
            target.setEstablishmentDate(reqVO.getEstablishmentDate());
        }
        if (reqVO.getLegalPerson() != null) {
            target.setLegalPerson(reqVO.getLegalPerson());
        }
        if (reqVO.getLegalPhone() != null) {
            target.setLegalPhone(reqVO.getLegalPhone());
        }
        if (reqVO.getBusinessLicenseNo() != null) {
            target.setBusinessLicenseNo(reqVO.getBusinessLicenseNo());
        }
        if (reqVO.getTravelLicenseNo() != null) {
            target.setTravelLicenseNo(reqVO.getTravelLicenseNo());
        }
        if (reqVO.getHasStudyDepartment() != null) {
            target.setHasStudyDepartment(reqVO.getHasStudyDepartment());
        }
        if (reqVO.getStudyDeptStaffCount() != null) {
            target.setStudyDeptStaffCount(reqVO.getStudyDeptStaffCount());
        }
        if (reqVO.getFulltimeTutorCount() != null) {
            target.setFulltimeTutorCount(reqVO.getFulltimeTutorCount());
        }
        if (reqVO.getParttimeTutorCount() != null) {
            target.setParttimeTutorCount(reqVO.getParttimeTutorCount());
        }
        if (reqVO.getInstitutionType() != null) {
            target.setInstitutionType(reqVO.getInstitutionType());
        }
        if (reqVO.getInstitutionTypeOther() != null) {
            target.setInstitutionTypeOther(reqVO.getInstitutionTypeOther());
        }
        if (reqVO.getProductFeature() != null) {
            target.setProductFeature(reqVO.getProductFeature());
        }
        if (reqVO.getAnnualVisitorsRange() != null) {
            target.setAnnualVisitorsRange(reqVO.getAnnualVisitorsRange());
        }
        if (reqVO.getCoverImage() != null) {
            target.setCoverImage(reqVO.getCoverImage());
        }
        if (reqVO.getLogo() != null) {
            target.setLogo(reqVO.getLogo());
        }
        if (reqVO.getGallery() != null) {
            target.setGallery(reqVO.getGallery());
        }
        if (reqVO.getUnitProfile() != null) {
            target.setUnitProfile(reqVO.getUnitProfile());
        }
    }

    private void mergeApplyToOrgInfo(YwOrgInfoDO target, YwOrgInfoApplyDO source) {
        target.setUnitName(source.getUnitName());
        target.setDestinationName(source.getDestinationName());
        target.setBaseTheme(source.getBaseTheme());
        target.setUnitType(source.getUnitType());
        target.setAddress(source.getAddress());
        target.setContactPerson(source.getContactPerson());
        target.setContactPhone(source.getContactPhone());
        target.setContactEmail(source.getContactEmail());
        target.setAreaCover(source.getAreaCover());
        target.setAreaBuild(source.getAreaBuild());
        target.setAreaOpen(source.getAreaOpen());
        target.setOpenDaysPerYear(source.getOpenDaysPerYear());
        target.setEstimatedStudentsPerYear(source.getEstimatedStudentsPerYear());
        target.setMaxStudentsPerTime(source.getMaxStudentsPerTime());
        target.setFundInvestment(source.getFundInvestment());
        target.setBaseType(source.getBaseType());
        target.setBaseTypeOther(source.getBaseTypeOther());
        target.setAreaLand(source.getAreaLand());
        target.setAreaBuilding(source.getAreaBuilding());
        target.setAreaAvailable(source.getAreaAvailable());
        target.setHasAccommodation(source.getHasAccommodation());
        target.setAccommodationCapacity(source.getAccommodationCapacity());
        target.setBaseFeature(source.getBaseFeature());
        target.setEstablishmentDate(source.getEstablishmentDate());
        target.setLegalPerson(source.getLegalPerson());
        target.setLegalPhone(source.getLegalPhone());
        target.setBusinessLicenseNo(source.getBusinessLicenseNo());
        target.setTravelLicenseNo(source.getTravelLicenseNo());
        target.setHasStudyDepartment(source.getHasStudyDepartment());
        target.setStudyDeptStaffCount(source.getStudyDeptStaffCount());
        target.setCertTutorCount(source.getFulltimeTutorCount());
        target.setParttimeTutorCount(source.getParttimeTutorCount());
        target.setInstitutionType(source.getInstitutionType());
        target.setInstitutionTypeOther(source.getInstitutionTypeOther());
        target.setProductFeature(source.getProductFeature());
        target.setAnnualVisitorsRange(source.getAnnualVisitorsRange());
        target.setCoverImage(source.getCoverImage());
        target.setLogo(source.getLogo());
        target.setGallery(source.getGallery());
        target.setUnitProfile(source.getUnitProfile());
    }

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }
}



