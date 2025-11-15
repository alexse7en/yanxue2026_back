package cn.iocoder.yudao.module.yw.dal.dataobject;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 试题 DO
 *
 * @author 芋道源码
 */
@TableName("yw_qu")
@KeySequence("yw_qu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwQuDO extends BaseDO {

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

}