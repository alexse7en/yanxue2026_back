package cn.iocoder.yudao.module.yw.ps.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评审细则分页 Request VO")
@Data
public class YwLevelGroupNormPageReqVO extends PageParam {

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "描述")
    private String introduce;

    @Schema(description = "讲师头像")
    private String countRules;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "最高分")
    private Long maxScore;

    @Schema(description = "归属指标", example = "32002")
    private Long groupId;

    @Schema(description = "输入类型", example = "1")
    private String inputType;

}