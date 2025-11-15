package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 教师分页 Request VO")
@Data
public class YwTeacherPageReqVO extends PageParam {

    @Schema(description = "讲师姓名", example = "李四")
    private String name;

    @Schema(description = "讲师简介")
    private String introduce;

    @Schema(description = "讲师头像")
    private String avatar;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}