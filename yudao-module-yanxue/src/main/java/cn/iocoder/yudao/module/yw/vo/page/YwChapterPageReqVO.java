package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 章节分页 Request VO")
@Data
public class YwChapterPageReqVO extends PageParam {
    private String cover;
    @Schema(description = "课程id", example = "11924")
    private Long courseId;

    @Schema(description = "章节标题")
    private String title;
    private String describe;

    @Schema(description = "小节视频id（阿里云视频id）", example = "9783")
    private String videoSourceId;

    @Schema(description = "原始文件名称（用户上传文件时的视频名称）", example = "芋艿")
    private String videoOriginalName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}