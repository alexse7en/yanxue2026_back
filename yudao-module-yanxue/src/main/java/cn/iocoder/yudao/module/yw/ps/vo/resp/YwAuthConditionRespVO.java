package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 志愿者评审条件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwAuthConditionRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26677")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "会员号", example = "6701")
    @ExcelProperty("会员号")
    private Long memberId;

    @Schema(description = "认证等级", example = "4955")
    @ExcelProperty("认证等级")
    private Long levelId;

    @Schema(description = "图片路径")
    @ExcelProperty("图片路径")
    private String urls;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "认证id", example = "32717")
    @ExcelProperty("认证id")
    private Long authId;

}