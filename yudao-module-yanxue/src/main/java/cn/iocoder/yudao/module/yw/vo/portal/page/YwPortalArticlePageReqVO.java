package cn.iocoder.yudao.module.yw.vo.portal.page;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 首页资讯中心分页 Request VO")
@Data
public class YwPortalArticlePageReqVO extends PageParam {

    @Schema(description = "分类")
    private String category;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "状态")
    private Integer status;
}
