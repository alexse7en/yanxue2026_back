package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 研学首页文章新增/修改 Request VO")
@Data
public class YwYanxueArticleSaveReqVO {

    @Schema(description = "主键 ID")
    private Long id;

    @Schema(description = "文章种类/栏目", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "文章种类/栏目不能为空")
    @Size(max = 50, message = "文章种类/栏目长度不能超过 50 个字符")
    private String category;

    @Schema(description = "文章标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题长度不能超过 200 个字符")
    private String title;

    @Schema(description = "文章内容（支持富文本）")
    private String content;

    @Schema(description = "文章缩略图 URL")
    @Size(max = 500, message = "文章缩略图 URL 长度不能超过 500 个字符")
    private String image;

    @Schema(description = "文章摘要")
    @Size(max = 500, message = "文章摘要长度不能超过 500 个字符")
    private String summary;

    @Schema(description = "排序值，越小越靠前")
    private Integer sortOrder;

    @Schema(description = "状态：0-草稿 1-发布 2-下架")
    private Integer status;

    @Schema(description = "是否置顶：0-否 1-是")
    private Boolean isTop;

    @Schema(description = "浏览次数")
    private Integer viewCount;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "附件下载 URL")
    @Size(max = 500, message = "附件下载 URL 长度不能超过 500 个字符")
    @Pattern(regexp = "^[^,\\uFF0C]*$", message = "附件下载 URL 只能保存单个地址")
    private String downloadurl;

    @Schema(description = "作者")
    @Size(max = 255, message = "作者长度不能超过 255 个字符")
    private String author;
}
