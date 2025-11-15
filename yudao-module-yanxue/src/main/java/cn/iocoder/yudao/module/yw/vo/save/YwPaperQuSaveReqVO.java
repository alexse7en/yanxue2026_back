package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 试卷题目新增/修改 Request VO")
@Data
public class YwPaperQuSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28803")
    private Long id;

    @Schema(description = "是否回答", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否回答不能为空")
    private String izAnswered;

    @Schema(description = "答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "答案不能为空")
    private String answer;

    @Schema(description = "讲师头像")
    private String avatar;

    

    @Schema(description = "试卷id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15961")
    @NotNull(message = "试卷id不能为空")
    private Long paperId;

    @Schema(description = "试卷id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30706")
    @NotNull(message = "试卷id不能为空")
    private Long quId;

    @Schema(description = "答案序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "答案序号不能为空")
    private String answerTitle;

    @Schema(description = "实际答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "实际答案不能为空")
    private String realAnswer;

    @Schema(description = "实际答案序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "实际答案序号不能为空")
    private String realAnswerTitle;

}