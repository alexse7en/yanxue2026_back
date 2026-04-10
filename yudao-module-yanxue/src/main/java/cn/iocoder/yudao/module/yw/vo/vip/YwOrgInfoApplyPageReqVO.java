package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(description = "管理后台 - 二级认证展示资料编辑申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgInfoApplyPageReqVO extends PageParam {

    @Schema(description = "机构类型")
    private String orgType;

    @Schema(description = "是否已审核：0=未审核，1=已审核")
    private Integer audited;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "认证编号")
    private String certNo;

    @Schema(description = "申请开始时间")
    private LocalDate beginApplyTime;

    @Schema(description = "申请结束时间")
    private LocalDate endApplyTime;
}

