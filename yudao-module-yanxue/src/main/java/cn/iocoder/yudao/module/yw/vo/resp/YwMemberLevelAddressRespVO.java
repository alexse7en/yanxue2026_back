package cn.iocoder.yudao.module.yw.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 用户收件地址 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwMemberLevelAddressRespVO {

    @Schema(description = "收件地址编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19454")
    @ExcelProperty("收件地址编号")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3702")
    @ExcelProperty("用户编号")
    private Long memberId;

    @Schema(description = "收件人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("收件人名称")
    private String name;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("手机号")
    private String mobile;

    @Schema(description = "地区编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "17338")
    @ExcelProperty("地区编码")
    private Long areaId;

    @Schema(description = "收件详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("收件详细地址")
    private String detailAddress;

    @Schema(description = "是否默认", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("是否默认")
    private Boolean defaultStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "证书编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18647")
    @ExcelProperty("证书编号")
    private Long levelId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("system_notice_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String status;

    @Schema(description = "快递公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("快递公司")
    private String company;

    @Schema(description = "快递单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("快递单号")
    private String deliveryNo;

}