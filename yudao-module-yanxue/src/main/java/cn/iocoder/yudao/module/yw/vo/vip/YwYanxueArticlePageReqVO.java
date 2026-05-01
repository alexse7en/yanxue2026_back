package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 研学首页文章分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwYanxueArticlePageReqVO extends PageParam {

    @Schema(description = "文章种类/栏目")
    private String category;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "状态：0-草稿 1-发布 2-下架")
    private Integer status;

    @Schema(description = "是否置顶：0-否 1-是")
    private Boolean isTop;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "发布时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] publishTime;
}
