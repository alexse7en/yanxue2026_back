package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 章节进度分页 Request VO")
@Data
public class YwMemberChapterPageReqVO extends PageParam {

    @Schema(description = "职工id", example = "12178")
    private String memberId;

    @Schema(description = "小节id", example = "20645")
    private String chapterId;

    @Schema(description = "已学习时长（s）")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Double[] learningTime;

    @Schema(description = "小节总时长（s）")
    private Double duration;

    @Schema(description = "小节是否学习完成，1（true）已完成， 0（false）未完成，默认0")
    private Integer accomplishFlag;

    @Schema(description = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "上次观看视频时长记录（s）")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Double[] lastTime;

}