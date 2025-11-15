package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 章节 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwChapterRespVO {
    private String cover;
    @Schema(description = "章节id",  example = "17050")
    @ExcelProperty("章节id")
    private Long id;

    @Schema(description = "课程id", example = "11924")
    @ExcelProperty("课程id")
    private Long courseId;

    @Schema(description = "章节标题")
    @ExcelProperty("章节标题")
    private String title;
    private String describe;
    @Schema(description = "小节视频id（阿里云视频id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "9783")
    @ExcelProperty("小节视频id（阿里云视频id）")
    private String videoSourceId;

    @Schema(description = "原始文件名称（用户上传文件时的视频名称）", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("原始文件名称（用户上传文件时的视频名称）")
    private String videoOriginalName;

    @Schema(description = "播放次数", example = "28906")
    @ExcelProperty("播放次数")
    private Integer playCount;

    @Schema(description = "视频时长（秒）")
    @ExcelProperty("视频时长（秒）")
    private Double duration;

    @Schema(description = "视频大小（字节）")
    @ExcelProperty("视频大小（字节）")
    private Long size;

    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}