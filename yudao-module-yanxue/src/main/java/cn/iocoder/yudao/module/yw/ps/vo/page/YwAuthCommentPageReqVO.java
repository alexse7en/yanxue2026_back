package cn.iocoder.yudao.module.yw.ps.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评审结果分页 Request VO")
@Data
public class YwAuthCommentPageReqVO extends PageParam {

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "分数")
    private Integer score;

    @Schema(description = "审核类型", example = "2")
    private String commentType;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "认证id", example = "13090")
    private Long authId;

    @Schema(description = "条件id", example = "11035")
    private Long detailId;

    @Schema(description = "条件id", example = "25767")
    private Long teacherId;

    @Schema(description = "备注")
    private String bz;

}