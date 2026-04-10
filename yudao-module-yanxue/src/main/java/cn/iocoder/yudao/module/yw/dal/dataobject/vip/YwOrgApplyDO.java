package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 机构认证申请 DO（对应 yw_yanxue_org_apply）
 */
@TableName("yw_yanxue_org_apply")
@KeySequence("yw_yanxue_org_apply_seq")
@Data
public class YwOrgApplyDO {

    @TableId
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

    // 以下两列在表中存在，如需参与业务查询/过滤可再补充对应使用
    private Integer deleted;
    private Long tenantId;
}
