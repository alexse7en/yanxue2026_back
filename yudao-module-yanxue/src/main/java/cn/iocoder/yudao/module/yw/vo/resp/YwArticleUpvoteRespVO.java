package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 文章管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwArticleUpvoteRespVO {

    @Schema(description = "文章管理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32637")
    @ExcelProperty("文章管理编号")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "515")
    @ExcelProperty("用户id")
    private Long memberId;

    @Schema(description = "文章id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2743")
    @ExcelProperty("文章id")
    private Long articleId;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}