package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(description = "管理后台 - 二级认证申请审核分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgApplyAuditPageReqVO extends PageParam {

    @Schema(description = "申请类型：base/organization/support")
    private String applyType;

    @Schema(description = "是否已审核：0=未审核，1=已审核")
    private Integer audited;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "申请编号")
    private String applyNo;

    @Schema(description = "申请开始日期")
    private LocalDate beginApplyDate;

    @Schema(description = "申请结束日期")
    private LocalDate endApplyDate;
}

