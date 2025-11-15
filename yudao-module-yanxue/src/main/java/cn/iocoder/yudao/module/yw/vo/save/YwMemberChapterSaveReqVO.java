package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 章节进度新增/修改 Request VO")
@Data
public class YwMemberChapterSaveReqVO {

    @Schema(description = "职工小节表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31464")
    private String id;

    @Schema(description = "职工id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12178")
    @NotEmpty(message = "职工id不能为空")
    private String memberId;

    @Schema(description = "小节id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20645")
    @NotEmpty(message = "小节id不能为空")
    private String chapterId;

    @Schema(description = "已学习时长（s）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "已学习时长（s）不能为空")
    private Double learningTime;

    @Schema(description = "小节总时长（s）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "小节总时长（s）不能为空")
    private Double duration;

    @Schema(description = "小节是否学习完成，1（true）已完成， 0（false）未完成，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "小节是否学习完成，1（true）已完成， 0（false）未完成，默认0不能为空")
    private Integer accomplishFlag;

 
    

    @Schema(description = "上次观看视频时长记录（s）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "上次观看视频时长记录（s）不能为空")
    private Double lastTime;

}