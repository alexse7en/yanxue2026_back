package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;

@Schema(description = "管理后台 - 考卷设计新增/修改 Request VO")
@Data
public class YwExamSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18335")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标题不能为空")
    private String title;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "描述不能为空")
    private String content;

    @Schema(description = "课程id", example = "27486")
    private Long courseId;

    

    @Schema(description = "是否限时")
    private String timeLimit;

    @Schema(description = "考试时间")
    private Integer totalTime;

    @Schema(description = "总分值")
    private Integer 
totalScore;

    @Schema(description = "及格分值")
    private Integer qualifyScore;

}