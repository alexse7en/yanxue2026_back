package cn.iocoder.yudao.module.yw.vo.vip;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 研学首页文章 Excel VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
@ExcelIgnoreUnannotated
public class YwYanxueArticleExcelVO {

    @ExcelProperty(value = "主键 ID", index = 0)
    private Long id;

    @ExcelProperty(value = "文章种类/栏目", index = 1)
    private String category;

    @ExcelProperty(value = "文章标题", index = 2)
    private String title;

    @ExcelProperty(value = "作者", index = 3)
    private String author;

    @ExcelProperty(value = "文章内容", index = 4)
    private String content;

    @ExcelProperty(value = "文章缩略图 URL", index = 5)
    private String image;

    @ExcelProperty(value = "文章摘要", index = 6)
    private String summary;

    @ExcelProperty(value = "排序值", index = 7)
    private Integer sortOrder;

    @ExcelProperty(value = "状态", index = 8)
    private String status;

    @ExcelProperty(value = "是否置顶", index = 9)
    private String isTop;

    @ExcelProperty(value = "浏览次数", index = 10)
    private Integer viewCount;

    @DateTimeFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ExcelProperty(value = "发布时间", index = 11)
    private LocalDateTime publishTime;

    @ExcelProperty(value = "附件下载 URL", index = 12)
    private String downloadurl;

}
