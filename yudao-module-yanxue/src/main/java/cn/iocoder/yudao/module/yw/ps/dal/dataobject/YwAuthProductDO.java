package cn.iocoder.yudao.module.yw.ps.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 作品申请 DO
 *
 * @author 芋道源码
 */
@TableName("yw_auth_product")
@KeySequence("yw_auth_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwAuthProductDO extends BaseDO {

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
     * 分数1
     */
    private Integer score1;
    /**
     * 会员号
     */
    private Long memberId;
    /**
     * 状态
     *
     * 枚举 {@link TODO article_status 对应的类}
     */
    private String status;
    /**
     * 总得分
     */
    private Integer realScore;
    /**
     * 宣传id
     */
    private Long articleId;
    /**
     * 分数2
     */
    private Integer score2;
    /**
     * 分数3
     */
    private Integer score3;
    /**
     * 分数4
     */
    private Integer score4;
    /**
     * 分数5
     */
    private Integer score5;
    /**
     * 作品路径
     */
    private String url;
    /**
     * 后台照片
     */
    private String images;


}