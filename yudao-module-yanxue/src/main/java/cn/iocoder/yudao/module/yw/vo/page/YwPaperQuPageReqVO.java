package cn.iocoder.yudao.module.yw.vo.page;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 试卷题目分页 Request VO")
@Data
public class YwPaperQuPageReqVO extends PageParam {

    @Schema(description = "是否回答")
    private String izAnswered;

    @Schema(description = "答案")
    private String answer;

    @Schema(description = "讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "试卷id", example = "23967")
    private Long paperId;

    @Schema(description = "试卷id", example = "13217")
    private Long quId;

    @Schema(description = "实际答案")
    private String realAnswer;

    @Schema(description = "题目类型", example = "2")
    private String quType;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "题干")
    private String content;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "解析")
    private String analysis;

    @Schema(description = "id", example = "21668")
    private Long examId;

    @Schema(description = "得分")
    private Integer score;

    @Schema(description = "是否正确")
    private String izRight;

}