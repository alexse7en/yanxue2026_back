package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 试卷题目 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwPaperQuRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13115")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "是否回答", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否回答")
    private String izAnswered;

    @Schema(description = "答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("答案")
    private String answer;

    @Schema(description = "讲师头像")
    @ExcelProperty("讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "试卷id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23967")
    @ExcelProperty("试卷id")
    private Long paperId;

    @Schema(description = "试卷id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13217")
    @ExcelProperty("试卷id")
    private Long quId;

    @Schema(description = "实际答案")
    @ExcelProperty("实际答案")
    private String realAnswer;

    @Schema(description = "题目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("题目类型")
    private String quType;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    @ExcelProperty("图片地址")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("题干")
    private String content;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "解析")
    @ExcelProperty("解析")
    private String analysis;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21668")
    @ExcelProperty("id")
    private Long examId;

    @Schema(description = "得分")
    @ExcelProperty("得分")
    private Integer score;

    @Schema(description = "是否正确")
    @ExcelProperty("是否正确")
    private String izRight;

}