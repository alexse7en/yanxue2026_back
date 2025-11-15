package cn.iocoder.yudao.module.yw.ps.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 作品申请 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwAuthProductRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15981")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", example = "李四")
    @ExcelProperty("名称")
    private String name;



    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "分数1")
    @ExcelProperty("分数1")
    private Integer score1;

    @Schema(description = "会员号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19910")
    @ExcelProperty("会员号")
    private Long memberId;


    @Schema(description = "状态", example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("article_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String status;

    @Schema(description = "总得分")
    @ExcelProperty("总得分")
    private Integer realScore;

    @Schema(description = "宣传id", example = "29453")
    @ExcelProperty("宣传id")
    private Long articleId;

    @Schema(description = "分数2")
    @ExcelProperty("分数2")
    private Integer score2;

    @Schema(description = "分数3")
    @ExcelProperty("分数3")
    private Integer score3;

    @Schema(description = "分数4")
    @ExcelProperty("分数4")
    private Integer score4;

    @Schema(description = "分数5")
    @ExcelProperty("分数5")
    private Integer score5;

    @Schema(description = "作品路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("作品路径")
    private String url;

    @Schema(description = "后台照片", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("后台照片")
    private String images;

    private String memberName;//会员姓名

}
