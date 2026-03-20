package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(description = "管理后台 - 会员申请审核分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwVipApplyAuditPageReqVO extends PageParam {

    @Schema(description = "是否已审核：0=未审核，1=已审核")
    private Integer audited;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "申请等级")
    private String applyLevel;

    @Schema(description = "会员编号")
    private String memberNo;

    @Schema(description = "申请开始日期")
    private LocalDate beginApplyDate;

    @Schema(description = "申请结束日期")
    private LocalDate endApplyDate;
}
