package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class YwDashboardKpiRespVO {
    @Schema(description = "日新增志愿者")
    private Long newMembersToday;

    @Schema(description = "今日 DAU（日活）")
    private Long dauToday;

    @Schema(description = "已认证志愿者")
    private Long volunteerCertified;

    @Schema(description = "志愿者总数")
    private Long volunteerTotal;
}
