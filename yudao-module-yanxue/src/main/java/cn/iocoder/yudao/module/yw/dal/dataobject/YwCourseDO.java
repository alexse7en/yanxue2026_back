package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 课程 DO
 *
 * @author wangxi
 */
@TableName("yw_course")
@KeySequence("yw_course_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwCourseDO extends BaseDO {

    /**
     * 课程id
     */
    @TableId
    private Long id;
    /**
     * 讲师id
     */
    private String teacherId;
    private Long examId;
    /**
     * 课程分类id
     */
    private String category;
    /**
     * 课程标题
     */
    private String title;
    /**
     * 课时数
     */
    private Integer lessonNum;
    /**
     * 课程封面图片url
     */
    private String cover;
    /**
     * 浏览量
     */
    private Integer viewCount;
    /**
     * 课程参与人数
     */
    private Integer learningNum;
    /**
     * 评论数
     */
    private Integer commentNum;
    /**
     * 课程状态，Draft已保存未发布，Provisional未保存临时数据，Normal已发布
     */
    private String status;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Boolean delFlag;
    /**
     * 课程简介
     */
    private String description;


}