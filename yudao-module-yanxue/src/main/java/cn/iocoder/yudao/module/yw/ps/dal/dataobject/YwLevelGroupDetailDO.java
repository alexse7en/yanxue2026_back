package cn.iocoder.yudao.module.yw.ps.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评分项 DO
 *
 * @author 芋道源码
 */
@TableName("yw_level_group_detail")
@KeySequence("yw_level_group_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwLevelGroupDetailDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
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
    /**
     * 得分
     */
    private Long score;
    /**
     * 归属指标
     */
    private Long normId;
    /**
     * 破格条件
     */
    private Long excludeLevelId;


}