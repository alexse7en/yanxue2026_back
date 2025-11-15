package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 试卷选项 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwPaperQuOptionRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24814")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "是否选中", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否选中")
    private String izAnswered;

    @Schema(description = "标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标签")
    private String abcd;

    @Schema(description = "讲师头像")
    @ExcelProperty("讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "选项id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17423")
    @ExcelProperty("选项id")
    private Long optionId;

    @Schema(description = "考题id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19535")
    @ExcelProperty("考题id")
    private Long quId;

    @Schema(description = "是否正确", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否正确")
    private String izRight;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    @ExcelProperty("图片地址")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("题干")
    private String content;

    @Schema(description = "解析")
    @ExcelProperty("解析")
    private String analysis;

}