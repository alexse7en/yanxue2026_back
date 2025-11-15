package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 试卷 DO
 *
 * @author 芋道源码
 */
@TableName("yw_paper")
@KeySequence("yw_paper_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwPaperDO extends BaseDO {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 试卷id
     */
    private Long examId;
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 考试时长
     */
    private Integer userTime;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Boolean delFlag;
    /**
     * 得分
     */
    private Integer userScore;
    /**
     * 试卷状态
     */
    private String status;
    /**
     * 是否通过
     */
    private String izPass;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String content;
    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 总分值
     */
    private Integer totalScore;
    /**
     * 及格分值
     */
    private Integer qualifyScore;
    /**
     * 是否限时
     */
    private String timeLimit;
    /**
     * 考试时间
     */
    private Integer totalTime;


}