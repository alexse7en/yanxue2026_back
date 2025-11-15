package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户收件地址分页 Request VO")
@Data
public class YwMemberLevelAddressPageReqVO extends PageParam {

    @Schema(description = "收件地址编号", example = "19454")
    private Long id;

    @Schema(description = "用户编号", example = "3702")
    private Long memberId;

    @Schema(description = "收件人名称", example = "赵六")
    private String name;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "地区编码", example = "17338")
    private Long areaId;

    @Schema(description = "收件详细地址")
    private String detailAddress;

    @Schema(description = "是否默认", example = "1")
    private Boolean defaultStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "证书编号", example = "18647")
    private Long levelId;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "快递公司")
    private String company;

    @Schema(description = "快递单号")
    private String deliveryNo;

}