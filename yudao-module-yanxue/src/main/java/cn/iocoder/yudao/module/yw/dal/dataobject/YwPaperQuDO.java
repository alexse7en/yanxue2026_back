package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 试卷题目 DO
 *
 * @author 芋道源码
 */
@TableName("yw_paper_qu")
@KeySequence("yw_paper_qu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwPaperQuDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 是否回答
     */
    private String izAnswered;
    /**
     * 答案
     */
    private String answer;
    /**
     * 讲师头像
     */
    private String avatar;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Boolean delFlag;
    /**
     * 试卷id
     */
    private Long paperId;
    /**
     * 试卷id
     */
    private Long quId;
    /**
     * 实际答案
     */
    private String realAnswer;
    /**
     * 题目类型
     */
    private String quType;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 题干
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 解析
     */
    private String analysis;
    /**
     * id
     */
    private Long examId;
    /**
     * 得分
     */
    private Integer score;
    /**
     * 是否正确
     */
    private String izRight;

}