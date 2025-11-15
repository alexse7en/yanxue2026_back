package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 课程进度 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwMemberCourseRespVO {

    @Schema(description = "职工课程学习情况id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21863")
    @ExcelProperty("职工课程学习情况id")
    private String id;

    @Schema(description = "职工id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30158")
    @ExcelProperty("职工id")
    private String memberId;

    @Schema(description = "课程id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3465")
    @ExcelProperty("课程id")
    private String courseId;

    @Schema(description = "是否完成 1（true）已完成， 0（false）未完成，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否完成 1（true）已完成， 0（false）未完成，默认0", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer accomplishFlag;

    @Schema(description = "已学习小节数目，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("已学习小节数目，默认0")
    private Integer learningChapterNum;

    @Schema(description = "课程小节总数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("课程小节总数")
    private Integer allChapterNum;

 
    @ExcelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "上次学习的小节id", example = "9346")
    @ExcelProperty("上次学习的小节id")
    private String lastChapterId;

}