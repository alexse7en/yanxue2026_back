package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 课程进度 DO
 *
 * @author 芋道源码
 */
@TableName("yw_member_course")
@KeySequence("yw_member_course_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwMemberCourseDO extends BaseDO {

    /**
     * 职工课程学习情况id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 职工id
     */
    private String memberId;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 是否完成 1（true）已完成， 0（false）未完成，默认0
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Integer accomplishFlag;
    /**
     * 已学习小节数目，默认0
     */
    private Integer learningChapterNum;
    /**
     * 课程小节总数
     */
    private Integer allChapterNum;
    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer delFlag;
    /**
     * 上次学习的小节id
     */
    private String lastChapterId;


}