package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 文章管理 DO
 *
 * @author 科协超级管理员
 */
@TableName("yw_article_upvote")
@KeySequence("yw_article_upvote_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwArticleUpvoteDO extends BaseDO {

    /**
     * 文章管理编号
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Integer delFlag;


}