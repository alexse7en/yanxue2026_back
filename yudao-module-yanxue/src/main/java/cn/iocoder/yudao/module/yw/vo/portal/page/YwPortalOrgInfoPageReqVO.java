package cn.iocoder.yudao.module.yw.vo.portal.page;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 首页资源中心分页 Request VO")
@Data
public class YwPortalOrgInfoPageReqVO extends PageParam {

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "机构类型")
    private String orgType;

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "状态")
    private Integer status;
}
