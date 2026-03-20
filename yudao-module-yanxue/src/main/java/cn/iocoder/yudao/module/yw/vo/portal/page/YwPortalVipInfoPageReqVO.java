package cn.iocoder.yudao.module.yw.vo.portal.page;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 首页会员中心分页 Request VO")
@Data
public class YwPortalVipInfoPageReqVO extends PageParam {

    @Schema(description = "会员单位名称")
    private String companyName;

    @Schema(description = "会员等级")
    private String memberLevel;

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "状态")
    private Integer status;
}
