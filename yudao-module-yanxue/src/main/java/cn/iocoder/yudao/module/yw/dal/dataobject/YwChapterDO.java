package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 章节 DO
 *
 * @author 芋道源码
 */
@TableName("yw_chapter")
@KeySequence("yw_chapter_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwChapterDO extends BaseDO {

    /**
     * 章节id
     */
    @TableId
    private Long id;
    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 章节标题
     */
    private String title;
    private String describe;
    /**
     * 小节视频id（阿里云视频id）
     */
    private String videoSourceId;
    /**
     * 原始文件名称（用户上传文件时的视频名称）
     */
    private String videoOriginalName;
    /**
     * 播放次数
     */
    private Integer playCount;
    /**
     * 视频时长（秒）
     */
    private Double duration;
    /**
     * 视频大小（字节）
     */
    private Long size;
    /**
     * 逻辑删除，1（true）已删除，0（false）未删除
     */
    private Integer delFlag;
    private String cover;


}