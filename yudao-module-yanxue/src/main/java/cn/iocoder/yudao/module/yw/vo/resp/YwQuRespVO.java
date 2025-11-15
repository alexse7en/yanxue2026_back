package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 试题 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwQuRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23581")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "题目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("题目类型")
    private String quType;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    @ExcelProperty("图片地址")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("题干")
    private String content;

    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "解析", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("解析")
    private String analysis;

    @Schema(description = "答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("答案")
    public  String answer;

    @Schema(description = "分值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分值")
    public  Integer score;

}