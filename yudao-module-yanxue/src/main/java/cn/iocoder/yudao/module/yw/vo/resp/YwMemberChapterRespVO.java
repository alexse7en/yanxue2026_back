package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 章节进度 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwMemberChapterRespVO {

    @Schema(description = "职工小节表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31464")
    @ExcelProperty("职工小节表id")
    private String id;

    @Schema(description = "职工id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12178")
    @ExcelProperty("职工id")
    private String memberId;

    @Schema(description = "小节id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20645")
    @ExcelProperty("小节id")
    private String chapterId;

    @Schema(description = "已学习时长（s）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("已学习时长（s）")
    private Double learningTime;

    @Schema(description = "小节总时长（s）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("小节总时长（s）")
    private Double duration;

    @Schema(description = "小节是否学习完成，1（true）已完成， 0（false）未完成，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "小节是否学习完成，1（true）已完成， 0（false）未完成，默认0", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer accomplishFlag;

 
    @ExcelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "上次观看视频时长记录（s）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("上次观看视频时长记录（s）")
    private Double lastTime;

}