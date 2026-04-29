package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwOrgInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgInfoService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_ORGINFO_NOT_EXISTS;

@Service
@Validated
public class YwOrgInfoServiceImpl implements YwOrgInfoService {

    @Resource
    private YwOrgInfoMapper orgInfoMapper;

    @Override
    public PageResult<YwOrgInfoRespVO> getOrgInfoPage(YwOrgInfoPageReqVO pageReqVO) {
        return orgInfoMapper.selectDataCenterPage(pageReqVO);
    }

    @Override
    public YwOrgInfoRespVO getOrgInfo(Long id) {
        YwOrgInfoRespVO orgInfo = orgInfoMapper.selectDataCenterById(id);
        if (orgInfo == null) {
            throw exception(YW_ORGINFO_NOT_EXISTS);
        }
        return orgInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrgInfo(YwOrgInfoSaveReqVO reqVO) {
        YwOrgInfoDO orgInfo = orgInfoMapper.selectById(reqVO.getId());
        if (orgInfo == null) {
            throw exception(YW_ORGINFO_NOT_EXISTS);
        }
        mergeEditableFields(orgInfo, reqVO);
        orgInfoMapper.updateById(orgInfo);
    }

    private void mergeEditableFields(YwOrgInfoDO orgInfo, YwOrgInfoSaveReqVO reqVO) {
        orgInfo.setCertNo(reqVO.getCertNo());
        orgInfo.setOrgType(reqVO.getOrgType());
        orgInfo.setStatus(reqVO.getStatus());
        orgInfo.setUnitName(reqVO.getUnitName());
        orgInfo.setDestinationName(reqVO.getDestinationName());
        orgInfo.setBaseTheme(reqVO.getBaseTheme());
        orgInfo.setUnitType(reqVO.getUnitType());
        orgInfo.setAddress(reqVO.getAddress());
        orgInfo.setContactPerson(reqVO.getContactPerson());
        orgInfo.setContactPhone(reqVO.getContactPhone());
        orgInfo.setContactEmail(reqVO.getContactEmail());
        orgInfo.setAreaCover(reqVO.getAreaCover());
        orgInfo.setAreaBuild(reqVO.getAreaBuild());
        orgInfo.setAreaOpen(reqVO.getAreaOpen());
        orgInfo.setOpenDaysPerYear(reqVO.getOpenDaysPerYear());
        orgInfo.setEstimatedStudentsPerYear(reqVO.getEstimatedStudentsPerYear());
        orgInfo.setMaxStudentsPerTime(reqVO.getMaxStudentsPerTime());
        orgInfo.setFundInvestment(reqVO.getFundInvestment());
        orgInfo.setBaseType(reqVO.getBaseType());
        orgInfo.setBaseTypeOther(reqVO.getBaseTypeOther());
        orgInfo.setAreaLand(reqVO.getAreaLand());
        orgInfo.setAreaBuilding(reqVO.getAreaBuilding());
        orgInfo.setAreaAvailable(reqVO.getAreaAvailable());
        orgInfo.setHasAccommodation(reqVO.getHasAccommodation());
        orgInfo.setAccommodationCapacity(reqVO.getAccommodationCapacity());
        orgInfo.setBaseFeature(reqVO.getBaseFeature());
        orgInfo.setEstablishmentDate(reqVO.getEstablishmentDate());
        orgInfo.setLegalPerson(reqVO.getLegalPerson());
        orgInfo.setLegalPhone(reqVO.getLegalPhone());
        orgInfo.setBusinessLicenseNo(reqVO.getBusinessLicenseNo());
        orgInfo.setTravelLicenseNo(reqVO.getTravelLicenseNo());
        orgInfo.setHasStudyDepartment(reqVO.getHasStudyDepartment());
        orgInfo.setStudyDeptStaffCount(reqVO.getStudyDeptStaffCount());
        orgInfo.setCertTutorCount(reqVO.getCertTutorCount() != null
                ? reqVO.getCertTutorCount() : reqVO.getFulltimeTutorCount());
        orgInfo.setParttimeTutorCount(reqVO.getParttimeTutorCount());
        orgInfo.setInstitutionType(reqVO.getInstitutionType());
        orgInfo.setInstitutionTypeOther(reqVO.getInstitutionTypeOther());
        orgInfo.setProductFeature(reqVO.getProductFeature());
        orgInfo.setAnnualVisitorsRange(reqVO.getAnnualVisitorsRange());
        orgInfo.setCoverImage(reqVO.getCoverImage());
        orgInfo.setLogo(reqVO.getLogo());
        orgInfo.setGallery(reqVO.getGallery());
        orgInfo.setCertTime(reqVO.getCertTime());
        orgInfo.setExpireTime(reqVO.getExpireTime());
        orgInfo.setUnitProfile(reqVO.getUnitProfile());
    }
}
