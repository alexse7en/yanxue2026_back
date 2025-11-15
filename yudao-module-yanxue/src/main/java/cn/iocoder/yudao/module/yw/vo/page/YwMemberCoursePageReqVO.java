package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 课程进度分页 Request VO")
@Data
public class YwMemberCoursePageReqVO extends PageParam {

    @Schema(description = "职工id", example = "30158")
    private String memberId;

    @Schema(description = "课程id", example = "3465")
    private String courseId;

    @Schema(description = "是否完成 1（true）已完成， 0（false）未完成，默认0")
    private Integer accomplishFlag;

    @Schema(description = "已学习小节数目，默认0")
    private Integer learningChapterNum;

    @Schema(description = "课程小节总数")
    private Integer allChapterNum;

    @Schema(description = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "上次学习的小节id", example = "9346")
    private String lastChapterId;

}