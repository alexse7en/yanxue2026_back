package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 教师 DO
 *
 * @author 芋道源码
 */
@TableName("yw_teacher")
@KeySequence("yw_teacher_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwTeacherDO extends BaseDO {

    /**
     * 讲师id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 讲师姓名
     */
    private String name;
    /**
     * 讲师简介
     */
    private String introduce;
    /**
     * 讲师头像
     */
    private String avatar;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Boolean delFlag;


}