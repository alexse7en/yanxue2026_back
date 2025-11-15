package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 文章管理分页 Request VO")
@Data
public class YwArticlePageReqVO extends PageParam {

    @Schema(description = "分类编号")
    private String category;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章作者")
    private String author;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章简介")
    private String introduction;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "是否热门(小程序)")
    private String recommendHot;

    @Schema(description = "是否轮播图(小程序)")
    private String recommendBanner;

    @Schema(description = "关联编号", example = "21838")
    private Long spuId;

}