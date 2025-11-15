package cn.iocoder.yudao.module.yw.vo.save;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;

@Schema(description = "管理后台 - 试题新增/修改 Request VO")
@Data
public class YwQuSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23581")
    private Long id;

    @Schema(description = "题目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "题目类型不能为空")
    private String quType;

    @Schema(description = "图片地址", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "题干", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "题干不能为空")
    private String content;

    

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "解析")
    private String analysis;

    @Schema(description = "答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("答案")
    public  String answer;

    @Schema(description = "分值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分值")
    public  Integer score;

}