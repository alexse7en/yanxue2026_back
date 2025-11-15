package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;

@Schema(description = "管理后台 - 试卷新增/修改 Request VO")
@Data
public class YwPaperSaveReqVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17534")
    private Long id;

    @Schema(description = "试卷id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18594")
    @NotNull(message = "试卷id不能为空")
    private Long examId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26757")
    @NotNull(message = "用户id不能为空")
    private Long memberId;

    @Schema(description = "考试时长")
    private Integer userTime;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "逻辑删除，1（true）已删除，0（false）未删除不能为空")
    private Boolean delFlag;

    @Schema(description = "得分")
    private Integer
            userScore;

    @Schema(description = "试卷状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "试卷状态不能为空")
    private String status;

    @Schema(description = "是否通过")
    private String izPass;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标题不能为空")
    private String title;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "描述不能为空")
    private String content;

    @Schema(description = "课程id", example = "14900")
    private Long courseId;

    @Schema(description = "总分值")
    private Integer totalScore;

    @Schema(description = "及格分值")
    private Integer qualifyScore;

    @Schema(description = "是否限时")
    private String timeLimit;

    @Schema(description = "考试时间")
    private Integer totalTime;

}