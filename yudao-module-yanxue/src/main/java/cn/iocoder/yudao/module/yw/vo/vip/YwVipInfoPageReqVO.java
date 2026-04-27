package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 会员数据中心分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwVipInfoPageReqVO extends PageParam {

    @Schema(description = "会员等级")
    private String memberLevel;

    @Schema(description = "会员名称")
    private String companyName;

    @Schema(description = "会员编号")
    private String memberNo;

    @Schema(description = "会员开始日期起")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate beginMembershipStartDate;

    @Schema(description = "会员开始日期止")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate endMembershipStartDate;

    @Schema(description = "会员结束日期起")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate beginMembershipEndDate;

    @Schema(description = "会员结束日期止")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate endMembershipEndDate;
}
