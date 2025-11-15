package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 文章管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwArticleRespVO {

    @Schema(description = "文章管理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22062")
    @ExcelProperty("文章管理编号")
    private Long id;

    @Schema(description = "分类编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "分类编号", converter = DictConvert.class)
    @DictFormat("article_category") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String category;

    @Schema(description = "文章标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("文章标题")
    private String title;

    @Schema(description = "文章作者")
    @ExcelProperty("文章作者")
    private String author;

    @Schema(description = "文章内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("文章内容")
    private String content;

    @Schema(description = "文章封面图片地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("文章封面图片地址")
    private String picUrl;
    private String picUrl2;

    @Schema(description = "文章简介")
    @ExcelProperty("文章简介")
    private String introduction;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("article_sataus") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "是否热门(小程序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否热门(小程序)", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String recommendHot;

    @Schema(description = "是否轮播图(小程序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否轮播图(小程序)", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String recommendBanner;

    @Schema(description = "浏览次数", example = "22140")
    @ExcelProperty("浏览次数")
    private Integer browseCount;
    private Integer upvoteCount;

    @Schema(description = "关联编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21838")
    @ExcelProperty("关联编号")
    private Long spuId;

}