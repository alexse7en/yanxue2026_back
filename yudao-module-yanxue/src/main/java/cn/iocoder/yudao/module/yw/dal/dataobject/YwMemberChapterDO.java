package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 章节进度 DO
 *
 * @author 芋道源码
 */
@TableName("yw_member_chapter")
@KeySequence("yw_member_chapter_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwMemberChapterDO extends BaseDO {

    /**
     * 职工小节表id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 职工id
     */
    private String memberId;
    /**
     * 小节id
     */
    private String chapterId;
    /**
     * 已学习时长（s）
     */
    private Double learningTime;
    /**
     * 小节总时长（s）
     */
    private Double duration;
    /**
     * 小节是否学习完成，1（true）已完成， 0（false）未完成，默认0
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Integer accomplishFlag;
    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer delFlag;
    /**
     * 上次观看视频时长记录（s）
     */
    private Double lastTime;


}