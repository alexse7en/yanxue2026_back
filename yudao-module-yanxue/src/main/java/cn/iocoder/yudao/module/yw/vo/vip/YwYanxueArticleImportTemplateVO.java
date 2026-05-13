package cn.iocoder.yudao.module.yw.vo.vip;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 研学首页文章导入模板 VO
 */
@Data
@Accessors(chain = false)
@ExcelIgnoreUnannotated
public class YwYanxueArticleImportTemplateVO {

    @ExcelProperty(value = "主键 ID（更新填，新增留空）", index = 0)
    private Long id;

    @ExcelProperty(value = "文章种类/栏目（必填：宣传/活动/培训/认证/行业研究）", index = 1)
    private String category;

    @ExcelProperty(value = "文章标题（必填）", index = 2)
    private String title;

    @ExcelProperty(value = "作者（选填）", index = 3)
    private String author;

    @ExcelProperty(value = "文章内容（必填，支持富文本 HTML）", index = 4)
    private String content;

    @ExcelProperty(value = "文章缩略图 URL（选填）", index = 5)
    private String image;

    @ExcelProperty(value = "文章摘要（选填）", index = 6)
    private String summary;

    @ExcelProperty(value = "排序值（选填，数字越小越靠前）", index = 7)
    private Integer sortOrder;

    @ExcelProperty(value = "状态（必填：0草稿/1发布/2下架）", index = 8)
    private String status;

    @ExcelProperty(value = "是否置顶（选填：1是/0否）", index = 9)
    private String isTop;

    @ExcelProperty(value = "浏览次数（选填）", index = 10)
    private Integer viewCount;

    @DateTimeFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ExcelProperty(value = "发布时间（选填：yyyy-MM-dd HH:mm:ss）", index = 11)
    private LocalDateTime publishTime;

    @ExcelProperty(value = "附件下载 URL（选填，仅单个地址）", index = 12)
    private String downloadurl;

}
