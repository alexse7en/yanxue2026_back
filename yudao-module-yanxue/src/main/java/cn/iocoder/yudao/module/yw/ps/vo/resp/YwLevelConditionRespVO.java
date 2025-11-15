package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评审条件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwLevelConditionRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15939")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述")
    private String introduce;

    @Schema(description = "讲师头像")
    @ExcelProperty("讲师头像")
    private String avatar;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "证明图片")
    @ExcelProperty("证明图片")
    private String urls;

    @Schema(description = "强制填写")
    @ExcelProperty("强制填写")
    private String izForce;

    @Schema(description = "勾选类型", example = "1")
    @ExcelProperty("勾选类型")
    private String inputType;

    @Schema(description = "关联课程", example = "12216")
    @ExcelProperty("关联课程")
    private Long examId;

    @Schema(description = "关联课程", requiredMode = Schema.RequiredMode.REQUIRED, example = "8049")
    @ExcelProperty("关联课程")
    private Long levelId;

}