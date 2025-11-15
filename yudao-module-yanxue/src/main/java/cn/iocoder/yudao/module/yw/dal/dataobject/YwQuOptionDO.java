package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 试题选项 DO
 *
 * @author 芋道源码
 */
@TableName("yw_qu_option")
@KeySequence("yw_qu_option_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwQuOptionDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 试题id
     */
    private Long quId;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Boolean delFlag;
    /**
     * 是否正确
     */
    private String izRight;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 题干
     */
    private String content;
    /**
     * 解析
     */
    private String analysis;
    private String abcd;

}