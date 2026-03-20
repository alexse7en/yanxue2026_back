package cn.iocoder.yudao.module.yw.vo.portal.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 首页资源中心 Response VO")
@Data
public class YwPortalOrgInfoRespVO {

    private Long id;
    private Long userId;
    private Long vipinfoId;
    private Long applyId;
    private String certNo;
    private String orgType;
    private Integer status;
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
    private Integer certTutorCount;
    private Integer parttimeTutorCount;
    private String institutionType;
    private String institutionTypeOther;
    private String productFeature;
    private String annualVisitorsRange;
    private String coverImage;
    private String logo;
    private String gallery;
    private LocalDateTime certTime;
    private LocalDate expireTime;
    private String unitProfile;
}
