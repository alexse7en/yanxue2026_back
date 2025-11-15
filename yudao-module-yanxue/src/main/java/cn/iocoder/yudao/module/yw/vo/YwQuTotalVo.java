package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.mapstruct.AfterMapping;

import java.util.LinkedHashMap;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class YwQuTotalVo  {

    /**
     * id
     */
    @TableId
    public  Long id;
    public  Long examId;
    /**
     * 题目类型
     */
    @ExcelProperty("题型")
    public  String quType;
    /**
     * 图片地址
     */
    public  String url;
    /**
     * 题干
     */
    @ExcelProperty("题干")
    public  String content;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    public  Boolean delFlag;
    /**
     * 备注
     */
    public  String remark;
    /**
     * 解析
     */
    @ExcelProperty("试题解析")
    public  String analysis;
    @ExcelProperty("答案")
    public  String answer;
    @ExcelProperty("分值")
    public  Integer score;




    @ExcelProperty("选项 A")
    private String optionA;

    /**
     * 选项 B
     */
    @ExcelProperty("选项 B")
    private String optionB;

    /**
     * 选项 C
     */
    @ExcelProperty("选项 C")
    private String optionC;

    /**
     * 选项 D
     */
    @ExcelProperty("选项 D")
    private String optionD;

    /**
     * 选项 E
     */
    @ExcelProperty("选项 E")
    private String optionE;

    /**
     * 选项 F
     */
    @ExcelProperty("选项 F")
    private String optionF;

    /**
     * 选项 G
     */
    @ExcelProperty("选项 G")
    private String optionG;

    /**
     * 选项 H
     */
    @ExcelProperty("选项 H")
    private String optionH;

    @AfterMapping
    public Map<String, String> getNonEmptyOptions() {
        Map<String, String> options = new LinkedHashMap<>();
        if (optionA != null && !optionA.trim().isEmpty()) options.put("A", optionA);
        if (optionB != null && !optionB.trim().isEmpty()) options.put("B", optionB);
        if (optionC != null && !optionC.trim().isEmpty()) options.put("C", optionC);
        if (optionD != null && !optionD.trim().isEmpty()) options.put("D", optionD);
        if (optionE != null && !optionE.trim().isEmpty()) options.put("E", optionE);
        if (optionF != null && !optionF.trim().isEmpty()) options.put("F", optionF);
        if (optionG != null && !optionG.trim().isEmpty()) options.put("G", optionG);
        if (optionH != null && !optionH.trim().isEmpty()) options.put("H", optionH);
        return options;
    }
}
