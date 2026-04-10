package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 二级认证申请 Response VO")
@Data
public class YwOrgApplyRespVO {

    private Long id;
    private Long userId;
    private Long vipinfoId;
    private String applyType;
    private Integer applyStatus;
    private String applyNo;
    private String filePath;
    private String fileType;
    private Integer parseStatus;
    private String parseError;

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
    private LocalDate establishmentDate;
    private String legalPerson;
    private String legalPhone;
    private String businessLicenseNo;
    private String travelLicenseNo;
    private Boolean hasStudyDepartment;
    private Integer studyDeptStaffCount;
    private Integer fulltimeTutorCount;
    private Integer parttimeTutorCount;
    private String unitProfile;

    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
