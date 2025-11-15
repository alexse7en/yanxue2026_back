package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组织信息管理分页 Request VO")
@Data
public class YwOrgApplyPageReqVO extends PageParam {

    @Schema(description = "组织名称", example = "赵六")
    private String username;

    @Schema(description = "组织文字介绍")
    private String intro;

    @Schema(description = "组织资格图片地址")
    private String material;

    @Schema(description = "0=待审核，1=通过，2=驳回", example = "1")
    private Integer status;

    @Schema(description = "审核未通过理由", example = "不对")
    private String auditReason;

    @Schema(description = "提交时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] submitTime;

    @Schema(description = "审核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] auditTime;

}
