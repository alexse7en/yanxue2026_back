package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 试题选项 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwQuOptionRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30579")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "试题id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17703")
    @ExcelProperty("试题id")
    private Long quId;

    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否正确")
    @ExcelProperty("是否正确")
    private String izRight;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    @ExcelProperty("图片地址")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("题干")
    private String content;

    @Schema(description = "解析", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("解析")
    private String analysis;

    @Schema(description = "选项", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("选项")
    private String abcd;

}