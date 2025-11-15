package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 试卷选项分页 Request VO")
@Data
public class YwPaperQuOptionPageReqVO extends PageParam {

    @Schema(description = "是否选中")
    private String izAnswered;

    @Schema(description = "标签")
    private String abcd;

    @Schema(description = "讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "选项id", example = "17423")
    private Long optionId;

    @Schema(description = "考题id", example = "19535")
    private Long quId;

    @Schema(description = "是否正确")
    private String izRight;
    @Schema(description = "实际正确")
    private String realRight;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "题干")
    private String content;

    @Schema(description = "解析")
    private String analysis;
}