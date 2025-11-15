package cn.iocoder.yudao.module.yw.ps.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 志愿者评审条件分页 Request VO")
@Data
public class YwAuthConditionPageReqVO extends PageParam {

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "会员号", example = "6701")
    private Long memberId;

    @Schema(description = "认证等级", example = "4955")
    private Long levelId;

    @Schema(description = "图片路径")
    private String urls;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "认证id", example = "32717")
    private Long authId;

}