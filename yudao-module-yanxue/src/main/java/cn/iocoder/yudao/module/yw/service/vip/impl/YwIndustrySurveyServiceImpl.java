package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwIndustrySurveyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwIndustrySurveyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwIndustrySurveyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwIndustrySurveyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveySaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_INDUSTRY_SURVEY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_INDUSTRY_SURVEY_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_INDUSTRY_SURVEY_STATUS_SUBMITTED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_INDUSTRY_SURVEY_TYPE_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_INDUSTRY_SURVEY_TYPE_REQUIRED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

@Service
@Validated
public class YwIndustrySurveyServiceImpl implements YwIndustrySurveyService {

    private static final String SURVEY_TYPE_BASE = "base";
    private static final String SURVEY_TYPE_ORGANIZATION = "organization";
    private static final Integer STATUS_DRAFT = 0;
    private static final Integer STATUS_SUBMITTED = 1;

    @Resource
    private YwIndustrySurveyMapper industrySurveyMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;

    @Override
    public YwIndustrySurveyRespVO getMyIndustrySurvey(String surveyType) {
        Long userId = getLoginUserId();
        YwIndustrySurveyDO survey;
        if (StringUtils.hasText(surveyType)) {
            validateSurveyType(surveyType);
            survey = industrySurveyMapper.selectByUserIdAndSurveyType(userId, surveyType);
        } else {
            survey = industrySurveyMapper.selectLatestByUserId(userId);
        }
        return YwIndustrySurveyConvert.INSTANCE.convert(survey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createIndustrySurvey(YwIndustrySurveySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        String surveyType = requireSurveyType(reqVO.getSurveyType());
        YwIndustrySurveyDO exists = industrySurveyMapper.selectByUserIdAndSurveyType(userId, surveyType);
        if (exists != null && Objects.equals(exists.getStatus(), STATUS_SUBMITTED)) {
            throw exception(YW_INDUSTRY_SURVEY_STATUS_SUBMITTED);
        }

        YwVipInfoDO vipInfo = requireVipInfo(userId);
        YwIndustrySurveyDO target = exists != null ? exists : new YwIndustrySurveyDO();
        fillOwnerFields(target, userId, vipInfo, surveyType);
        mergeSurveyFields(target, reqVO);
        if (target.getStatus() == null) {
            target.setStatus(STATUS_DRAFT);
        }
        if (target.getId() == null) {
            industrySurveyMapper.insert(target);
        } else {
            industrySurveyMapper.updateById(target);
        }
        return target.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIndustrySurvey(YwIndustrySurveySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwIndustrySurveyDO target = reqVO.getId() != null ? industrySurveyMapper.selectById(reqVO.getId()) : null;
        if (target == null || !Objects.equals(target.getUserId(), userId)) {
            throw exception(YW_INDUSTRY_SURVEY_NOT_EXISTS);
        }
        if (Objects.equals(target.getStatus(), STATUS_SUBMITTED)) {
            throw exception(YW_INDUSTRY_SURVEY_STATUS_SUBMITTED);
        }
        String surveyType = StringUtils.hasText(reqVO.getSurveyType()) ? reqVO.getSurveyType() : target.getSurveyType();
        validateSurveyType(surveyType);
        YwIndustrySurveyDO submitted = industrySurveyMapper.selectSubmittedByUserIdAndSurveyType(userId, surveyType);
        if (submitted != null && !Objects.equals(submitted.getId(), target.getId())) {
            throw exception(YW_INDUSTRY_SURVEY_STATUS_SUBMITTED);
        }

        YwVipInfoDO vipInfo = requireVipInfo(userId);
        fillOwnerFields(target, userId, vipInfo, surveyType);
        mergeSurveyFields(target, reqVO);
        if (target.getStatus() == null) {
            target.setStatus(STATUS_DRAFT);
        }
        industrySurveyMapper.updateById(target);
    }

    private void fillOwnerFields(YwIndustrySurveyDO target, Long userId, YwVipInfoDO vipInfo, String surveyType) {
        target.setUserId(userId);
        target.setVipinfoId(vipInfo.getId());
        target.setSurveyType(surveyType);
    }

    private YwVipInfoDO requireVipInfo(Long userId) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(userId);
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        return vipInfo;
    }

    private String requireSurveyType(String surveyType) {
        if (!StringUtils.hasText(surveyType)) {
            throw exception(YW_INDUSTRY_SURVEY_TYPE_REQUIRED);
        }
        validateSurveyType(surveyType);
        return surveyType;
    }

    private void validateSurveyType(String surveyType) {
        if (!SURVEY_TYPE_BASE.equals(surveyType) && !SURVEY_TYPE_ORGANIZATION.equals(surveyType)) {
            throw exception(YW_INDUSTRY_SURVEY_TYPE_INVALID);
        }
    }

    private void mergeSurveyFields(YwIndustrySurveyDO target, YwIndustrySurveySaveReqVO reqVO) {
        if (reqVO.getStatus() != null) {
            validateStatus(reqVO.getStatus());
            target.setStatus(reqVO.getStatus());
        }
        if (reqVO.getCompanyName() != null) {
            target.setCompanyName(reqVO.getCompanyName());
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
        if (reqVO.getUnitTypeChoice() != null) {
            target.setUnitTypeChoice(reqVO.getUnitTypeChoice());
        }
        if (reqVO.getUnitTypeOther() != null) {
            target.setUnitTypeOther(reqVO.getUnitTypeOther());
        }
        if (reqVO.getEmployeeScale() != null) {
            target.setEmployeeScale(reqVO.getEmployeeScale());
        }
        if (reqVO.getEnterpriseType() != null) {
            target.setEnterpriseType(reqVO.getEnterpriseType());
        }
        if (reqVO.getOperationYears() != null) {
            target.setOperationYears(reqVO.getOperationYears());
        }
        if (reqVO.getCustomerGroup() != null) {
            target.setCustomerGroup(reqVO.getCustomerGroup());
        }
        if (reqVO.getAnnualRevenueRange() != null) {
            target.setAnnualRevenueRange(reqVO.getAnnualRevenueRange());
        }
        if (reqVO.getProfitMarginRange() != null) {
            target.setProfitMarginRange(reqVO.getProfitMarginRange());
        }
        if (reqVO.getCourseDevelopment() != null) {
            target.setCourseDevelopment(reqVO.getCourseDevelopment());
        }
        if (reqVO.getCourseCount() != null) {
            target.setCourseCount(reqVO.getCourseCount());
        }
        if (reqVO.getHasFulltimeTutor() != null) {
            target.setHasFulltimeTutor(reqVO.getHasFulltimeTutor());
        }
        if (reqVO.getFulltimeTutorCount() != null) {
            target.setFulltimeTutorCount(reqVO.getFulltimeTutorCount());
        }
        if (reqVO.getHasParttimeTutor() != null) {
            target.setHasParttimeTutor(reqVO.getHasParttimeTutor());
        }
        if (reqVO.getNoTutor() != null) {
            target.setNoTutor(reqVO.getNoTutor());
        }
        if (reqVO.getPromotionChannels() != null) {
            target.setPromotionChannels(reqVO.getPromotionChannels());
        }
        if (reqVO.getPromotionChannelsOther() != null) {
            target.setPromotionChannelsOther(reqVO.getPromotionChannelsOther());
        }
        if (reqVO.getChallenges() != null) {
            target.setChallenges(reqVO.getChallenges());
        }
        if (reqVO.getChallengesOther() != null) {
            target.setChallengesOther(reqVO.getChallengesOther());
        }
        if (reqVO.getConfidenceLevel() != null) {
            target.setConfidenceLevel(reqVO.getConfidenceLevel());
        }
        if (reqVO.getSuggestions() != null) {
            target.setSuggestions(reqVO.getSuggestions());
        }
        if (reqVO.getDestinationName() != null) {
            target.setDestinationName(reqVO.getDestinationName());
        }
        if (reqVO.getBaseType() != null) {
            target.setBaseType(reqVO.getBaseType());
        }
        if (reqVO.getBaseTypeOther() != null) {
            target.setBaseTypeOther(reqVO.getBaseTypeOther());
        }
        if (reqVO.getBaseTheme() != null) {
            target.setBaseTheme(reqVO.getBaseTheme());
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
        if (reqVO.getMaxStudentsPerTime() != null) {
            target.setMaxStudentsPerTime(reqVO.getMaxStudentsPerTime());
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
    }

    private void validateStatus(Integer status) {
        if (!STATUS_DRAFT.equals(status) && !STATUS_SUBMITTED.equals(status)) {
            throw exception(YW_INDUSTRY_SURVEY_STATUS_INVALID);
        }
    }

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }
}
