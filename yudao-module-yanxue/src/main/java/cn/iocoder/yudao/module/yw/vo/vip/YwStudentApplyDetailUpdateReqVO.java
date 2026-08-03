package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "管理后台 - 学生证书申请明细修改 Request VO")
@Data
public class YwStudentApplyDetailUpdateReqVO {

    @Schema(description = "申请批次 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请批次 ID 不能为空")
    private Long batchId;

    @Schema(description = "本次修改的学生明细", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotEmpty(message = "学生明细不能为空")
    @Size(max = 20, message = "单次最多修改 20 条学生明细")
    private List<YwStudentApplyDetailSaveReqVO> details;
}
