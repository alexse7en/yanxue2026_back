package cn.iocoder.yudao.module.yw.ps.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 志愿者等级 DO
 *
 * @author 芋道源码
 */
@TableName("yw_auth_detail")
@KeySequence("yw_auth_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwAuthDetailDO extends BaseDO {

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
     * 分数
     */
    private Integer score;
    /**
     * 审核导师
     */
    private String teacher;
    /**
     * 状态
     */
    private String status;
    private String urls;
    private String izSelected;
    /**
     * 认证id
     */
    private Long authId;
    /**
     * 条件id
     */
    private Long detailId;
    private Long memberId;
    private Long levelId;


}