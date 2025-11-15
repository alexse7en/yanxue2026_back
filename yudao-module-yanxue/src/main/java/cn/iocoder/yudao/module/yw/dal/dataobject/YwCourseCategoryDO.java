package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 课程分类 DO
 *
 * @author 芋道源码
 */
@TableName("yw_course_category")
@KeySequence("yw_course_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwCourseCategoryDO extends BaseDO {

    /**
     * 课程分类id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 课程分类名称
     */
    private String categoryName;
    /**
     * 课程分类父类id
     */
    private String parentId;
    /**
     * 层级
     */
    private Integer level;


}