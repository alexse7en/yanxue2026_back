package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 学生证书申请明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwStudentApplyDetailPageReqVO extends PageParam {

    @Schema(description = "申请批次 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请批次 ID 不能为空")
    private Long batchId;

    @Schema(description = "是否仅查询存在错误的明细")
    private Boolean invalidOnly;
}
