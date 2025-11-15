package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 章节新增/修改 Request VO")
@Data
public class YwChapterSaveReqVO {

    @Schema(description = "章节id",  example = "17050")
    private Long id;
    private String cover;
    @Schema(description = "课程id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11924")
    @NotEmpty(message = "课程id不能为空")
    private Long courseId;

    @Schema(description = "章节标题")
    private String title;
    private String describe;

    @Schema(description = "小节视频id（阿里云视频id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "9783")
    @NotEmpty(message = "小节视频id（阿里云视频id）不能为空")
    private String videoSourceId;

    @Schema(description = "原始文件名称（用户上传文件时的视频名称）", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "原始文件名称（用户上传文件时的视频名称）不能为空")
    private String videoOriginalName;

}