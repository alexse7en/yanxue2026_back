package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 行业研究问卷 Response VO")
@Data
public class YwIndustrySurveyRespVO {

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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String userName;
    private String vipinfoName;
}
