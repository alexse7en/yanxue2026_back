package cn.iocoder.yudao.module.yw.ps.dal.dataobject;

import cn.hutool.core.date.DateTime;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

import java.time.LocalDateTime;

/**
 * 用户等级 DO
 *
 * @author 芋道源码
 */
@TableName("yw_member_level")
@KeySequence("yw_member_level_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwMemberLevelDO extends BaseDO {

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
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Boolean delFlag;
    /**
     * 会员号
     */
    private Long memberId;
    /**
     * 认证等级
     */
    private Long levelId;

}