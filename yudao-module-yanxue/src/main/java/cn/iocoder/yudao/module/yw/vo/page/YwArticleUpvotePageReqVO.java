package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 文章管理分页 Request VO")
@Data
public class YwArticleUpvotePageReqVO extends PageParam {

    @Schema(description = "用户id", example = "515")
    private Long memberId;

    @Schema(description = "文章id", example = "2743")
    private Long articleId;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}