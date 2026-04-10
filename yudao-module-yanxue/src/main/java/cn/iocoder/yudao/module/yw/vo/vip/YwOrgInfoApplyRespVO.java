package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 二级认证展示资料编辑申请 Response VO")
@Data
public class YwOrgInfoApplyRespVO {

    private Long id;
    private Long orginfoId;
    private Long userId;
    private String unitName;
    private String destinationName;
    private String baseTheme;
    private String unitType;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private BigDecimal areaCover;
    private BigDecimal areaBuild;
    private BigDecimal areaOpen;
    private Integer openDaysPerYear;
    private Integer estimatedStudentsPerYear;
    private Integer maxStudentsPerTime;
    private BigDecimal fundInvestment;
    private String baseType;
    private String baseTypeOther;
    private BigDecimal areaLand;
    private BigDecimal areaBuilding;
    private BigDecimal areaAvailable;
    private Boolean hasAccommodation;
    private Integer accommodationCapacity;
    private String baseFeature;
    private LocalDate establishmentDate;
    private String legalPerson;
    private String legalPhone;
    private String businessLicenseNo;
    private String travelLicenseNo;
    private Boolean hasStudyDepartment;
    private Integer studyDeptStaffCount;
    private Integer fulltimeTutorCount;
    private Integer parttimeTutorCount;
    private String institutionType;
    private String institutionTypeOther;
    private String productFeature;
    private String annualVisitorsRange;
    private String coverImage;
    private String logo;
    private String gallery;
    private String unitProfile;
    private Integer applyStatus;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
