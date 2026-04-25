package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 行业研究问卷 DO
 */
@TableName("yw_yanxue_industry_survey")
@KeySequence("yw_yanxue_industry_survey_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwIndustrySurveyDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long vipinfoId;
    private String surveyType;
    private Integer status;
    private String companyName;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private String unitTypeChoice;
    private String unitTypeOther;
    private String employeeScale;
    private String enterpriseType;
    private String operationYears;
    private String customerGroup;
    private String annualRevenueRange;
    private String profitMarginRange;
    private String courseDevelopment;
    private Integer courseCount;
    private Boolean hasFulltimeTutor;
    private Integer fulltimeTutorCount;
    private Boolean hasParttimeTutor;
    private Boolean noTutor;
    private String promotionChannels;
    private String promotionChannelsOther;
    private String challenges;
    private String challengesOther;
    private String confidenceLevel;
    private String suggestions;
    private String destinationName;
    private String baseType;
    private String baseTypeOther;
    private String baseTheme;
    private BigDecimal areaLand;
    private BigDecimal areaBuilding;
    private BigDecimal areaAvailable;
    private Integer maxStudentsPerTime;
    private Boolean hasAccommodation;
    private Integer accommodationCapacity;
    private String baseFeature;
    private String institutionType;
    private String institutionTypeOther;
    private String productFeature;
    private String annualVisitorsRange;
}
