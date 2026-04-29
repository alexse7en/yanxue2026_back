package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 机构数据中心分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgInfoPageReqVO extends PageParam {

    @Schema(description = "主键 ID")
    private Long id;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "会员信息 ID")
    private Long vipinfoId;

    @Schema(description = "会员名称")
    private String vipinfoName;

    @Schema(description = "机构类型")
    private String orgType;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "基地主题")
    private String baseTheme;
}
