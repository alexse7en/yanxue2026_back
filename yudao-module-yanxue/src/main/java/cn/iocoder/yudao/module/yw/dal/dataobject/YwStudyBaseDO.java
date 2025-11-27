package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import org.apache.poi.hpsf.Decimal;

/**
 * 研学基地/营地 DO
 *
 * @author 科协超级管理员
 */
@TableName("yw_study_base")
@KeySequence("yw_study_base_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwStudyBaseDO extends BaseDO {

    /**
     * 编号（主键）
     */
    @TableId
    private Long id;
    /**
     * 基地（营地）全称
     */
    private String name;
    /**
     * 地图短名称/按钮名称
     */
    private String shortName;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区/县
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 主题类型（如红色教育、科技创新等）
     */
    private String themeType;
    /**
     * 适配年龄段（如8-12岁、小学高年级等）
     */
    private String ageRange;
    /**
     * 研学基地（营地）简介（摘要）
     */
    private String intro;
    /**
     * 基地详情富文本（可选）
     */
    private String detailContent;
    /**
     * 主图1张
     */
    private String mainImageUrl;
    /**
     * 园区图片1
     */
    private String parkImage1Url;
    /**
     * 园区图片2
     */
    private String parkImage2Url;
    /**
     * 研学活动图片1
     */
    private String activityImage1Url;
    /**
     * 研学活动图片2
     */
    private String activityImage2Url;
    /**
     * 课程1简介（文本大致简介）
     */
    private String course1Desc;
    /**
     * 课程2简介（文本大致简介）
     */
    private String course2Desc;
    /**
     * 地图 X 坐标（0-100 百分比）
     */
    private BigDecimal xPercent;
    /**
     * 地图 Y 坐标（0-100 百分比）
     */
    private BigDecimal yPercent;
    /**
     * 是否推荐：0=否 1=是
     */
    private Boolean isRecommend;
    /**
     * 状态：0=禁用 1=启用
     */
    private Integer status;
    /**
     * 显示排序（越大越前）
     */
    private Integer sort;


}
