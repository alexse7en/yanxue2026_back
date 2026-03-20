package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(description = "管理后台 - 会员展示信息编辑申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwVipInfoApplyPageReqVO extends PageParam {

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "审核状态")
    private Integer applyStatus;

    @Schema(description = "申请开始时间")
    private LocalDate beginApplyTime;

    @Schema(description = "申请结束时间")
    private LocalDate endApplyTime;
}
