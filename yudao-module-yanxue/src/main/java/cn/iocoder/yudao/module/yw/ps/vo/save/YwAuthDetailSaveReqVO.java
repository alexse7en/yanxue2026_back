package cn.iocoder.yudao.module.yw.ps.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 志愿者等级新增/修改 Request VO")
@Data
public class YwAuthDetailSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31661")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "分数")
    private Integer score;

    @Schema(description = "审核导师")
    private String teacher;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "认证id", example = "14194")
    private Long authId;

    @Schema(description = "条件id", example = "20285")
    private Long detailId;

}