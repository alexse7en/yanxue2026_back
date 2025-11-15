package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 文章管理新增/修改 Request VO")
@Data
public class YwArticleSaveReqVO {

    @Schema(description = "文章管理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22062")
    private Long id;

    @Schema(description = "分类编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分类编号不能为空")
    private String category;

    @Schema(description = "文章标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "文章标题不能为空")
    private String title;

    @Schema(description = "文章作者")
    private String author;

    @Schema(description = "文章内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "文章内容不能为空")
    private String content;

    @Schema(description = "文章封面图片地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "文章封面图片地址不能为空")
    private String picUrl;
    private String picUrl2;

    @Schema(description = "文章简介")
    private String introduction;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "是否热门(小程序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否热门(小程序)不能为空")
    private String recommendHot;

    @Schema(description = "是否轮播图(小程序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否轮播图(小程序)不能为空")
    private String recommendBanner;

    @Schema(description = "关联编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21838")
    @NotNull(message = "关联编号不能为空")
    private Long spuId;

}