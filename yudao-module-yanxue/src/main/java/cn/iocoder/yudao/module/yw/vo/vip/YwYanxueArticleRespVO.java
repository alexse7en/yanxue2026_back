package cn.iocoder.yudao.module.yw.vo.vip;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 研学首页文章 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwYanxueArticleRespVO {

    @Schema(description = "主键 ID")
    @ExcelProperty("主键 ID")
    private Long id;

    @Schema(description = "文章种类/栏目")
    @ExcelProperty("文章种类/栏目")
    private String category;

    @Schema(description = "文章标题")
    @ExcelProperty("文章标题")
    private String title;

    @Schema(description = "文章内容（支持富文本）")
    private String content;

    @Schema(description = "文章缩略图 URL")
    @ExcelProperty("文章缩略图 URL")
    private String image;

    @Schema(description = "文章摘要")
    @ExcelProperty("文章摘要")
    private String summary;

    @Schema(description = "排序值，越小越靠前")
    @ExcelProperty("排序值")
    private Integer sortOrder;

    @Schema(description = "状态：0-草稿 1-发布 2-下架")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "是否置顶：0-否 1-是")
    @ExcelProperty("是否置顶")
    private Boolean isTop;

    @Schema(description = "浏览次数")
    @ExcelProperty("浏览次数")
    private Integer viewCount;

    @Schema(description = "发布时间")
    @ExcelProperty("发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "附件下载 URL")
    @ExcelProperty("附件下载 URL")
    private String downloadurl;

    @Schema(description = "作者")
    @ExcelProperty("作者")
    private String author;
}
