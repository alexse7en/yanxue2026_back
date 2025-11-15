package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 志愿者等级 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwAuthDetailRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31661")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "分数")
    @ExcelProperty("分数")
    private Integer score;

    @Schema(description = "审核导师")
    @ExcelProperty("审核导师")
    private String teacher;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "认证id", example = "14194")
    @ExcelProperty("认证id")
    private Long authId;

    @Schema(description = "条件id", example = "20285")
    @ExcelProperty("条件id")
    private Long detailId;

}