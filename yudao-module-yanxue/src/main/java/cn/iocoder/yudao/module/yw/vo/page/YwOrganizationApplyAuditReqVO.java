package cn.iocoder.yudao.module.yw.vo.page;

import lombok.Data;

@Data
public class YwOrganizationApplyAuditReqVO {
    private Long id;
    private Integer status; // 1通过 2不通过
    private String auditReason;
}
