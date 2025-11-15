package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class YwDashboardTrendPointRespVO {
    @Schema(description = "日期，yyyy-MM-dd")
    private String date;

    @Schema(description = "当日新增会员")
    private Long newMembers;

    @Schema(description = "当日活跃用户")
    private Long dau;

    @Schema(description = "当日完成认证数（或累计）")
    private Long certs;
}
