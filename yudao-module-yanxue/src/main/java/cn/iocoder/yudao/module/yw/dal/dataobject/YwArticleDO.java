package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 文章管理 DO
 *
 * @author 芋道源码
 */
@TableName("yw_article")
@KeySequence("yw_article_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwArticleDO extends BaseDO {

    /**
     * 文章管理编号
     */
    @TableId
    private Long id;
    /**
     * 分类编号
     *
     * 枚举 {@link TODO article_category 对应的类}
     */
    private String category;
    private String classification;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章作者
     */
    private String author;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章封面图片地址
     */
    private String picUrl;
    private String picUrl2;
    /**
     * 文章简介
     */
    private String introduction;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     *
     * 枚举 {@link TODO article_sataus 对应的类}
     */
    private Integer status;
    /**
     * 是否热门(小程序)
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private String recommendHot;
    /**
     * 是否轮播图(小程序)
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private String recommendBanner;
    /**
     * 浏览次数
     */
    private Integer browseCount;
    private Integer upvoteCount;
    /**
     * 关联编号
     */
    private Long spuId;


}