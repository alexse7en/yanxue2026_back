package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 组织信息管理新增/修改 Request VO")
@Data
public class YwOrgApplySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3687")
    private Long id;

    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "组织名称不能为空")
    private String username;

    @Schema(description = "组织文字介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "组织文字介绍不能为空")
    private String intro;

    @Schema(description = "组织资格图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "组织资格图片地址不能为空")
    private String material;

    @Schema(description = "0=待审核，1=通过，2=驳回", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "0=待审核，1=通过，2=驳回不能为空")
    private Integer status;

    @Schema(description = "审核未通过理由", example = "不对")
    private String auditReason;

    @Schema(description = "提交时间")
    private LocalDateTime submitTime;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

}
