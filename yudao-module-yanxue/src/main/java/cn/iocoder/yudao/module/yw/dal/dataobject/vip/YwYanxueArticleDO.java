package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 研学首页文章 DO
 *
 * @author 科协超级管理员
 */
@TableName("yw_yanxue_article")
@KeySequence("yw_yanxue_article_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwYanxueArticleDO extends BaseDO {

    @TableId
    private Long id;
    private String category;
    private String title;
    private String content;
    private String image;
    private String summary;
    private Integer sortOrder;
    private Integer status;
    private Boolean isTop;
    private Integer viewCount;
    private LocalDateTime publishTime;
    private String downloadurl;
    private String author;
}
