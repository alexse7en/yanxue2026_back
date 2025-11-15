// src/main/java/cn/iocoder/yudao/module/yw/controller/admin/dashboard/vo/AuditOverviewRespVO.java
package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class YwAuditOverviewRespVO {
    @Schema(description = "组织待审核数量")
    private Long orgPending;

    @Schema(description = "志愿者作品待审核数量")
    private Long workPending;

    @Schema(description = "志愿者挂靠未指派数量")
    private Long volunteerUnassigned;
}
