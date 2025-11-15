package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户等级 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwMemberLevelRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22793")
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

    @Schema(description = "会员号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9886")
    @ExcelProperty("会员号")
    private Long memberId;

    @Schema(description = "认证等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "26931")
    @ExcelProperty("认证等级")
    private Long levelId;

}