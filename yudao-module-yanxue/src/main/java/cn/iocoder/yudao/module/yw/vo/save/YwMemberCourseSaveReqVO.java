package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 课程进度新增/修改 Request VO")
@Data
public class YwMemberCourseSaveReqVO {

    @Schema(description = "职工课程学习情况id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21863")
    private String id;

    @Schema(description = "职工id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30158")
    @NotEmpty(message = "职工id不能为空")
    private String memberId;

    @Schema(description = "课程id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3465")
    @NotEmpty(message = "课程id不能为空")
    private String courseId;

    @Schema(description = "是否完成 1（true）已完成， 0（false）未完成，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否完成 1（true）已完成， 0（false）未完成，默认0不能为空")
    private Integer accomplishFlag;

    @Schema(description = "已学习小节数目，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "已学习小节数目，默认0不能为空")
    private Integer learningChapterNum;

    @Schema(description = "课程小节总数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "课程小节总数不能为空")
    private Integer allChapterNum;

 
    

    @Schema(description = "上次学习的小节id", example = "9346")
    private String lastChapterId;

}