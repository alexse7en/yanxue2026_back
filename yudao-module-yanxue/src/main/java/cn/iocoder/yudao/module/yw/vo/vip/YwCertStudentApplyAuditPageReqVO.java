package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(description = "管理后台 - 学生证书申请审核分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwCertStudentApplyAuditPageReqVO extends PageParam {

    @Schema(description = "申请单号")
    private String applyNo;

    @Schema(description = "申请状态")
    private Integer applyStatus;

    @Schema(description = "是否已审核：0=未审核，1=已审核")
    private Integer audited;

    @Schema(description = "创建开始时间")
    private LocalDate beginCreateTime;

    @Schema(description = "创建结束时间")
    private LocalDate endCreateTime;
}
