package cn.iocoder.yudao.module.yw.dal.dataobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户收件地址 DO
 *
 * @author 芋道源码
 */
@TableName("yw_member_level_address")
@KeySequence("yw_member_level_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YwMemberLevelAddressDO extends BaseDO {

    /**
     * 收件地址编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long memberId;
    /**
     * 收件人名称
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 地区编码
     */
    private Long areaId;
    /**
     * 收件详细地址
     */
    private String detailAddress;
    /**
     * 是否默认
     */
    private Boolean defaultStatus;
    /**
     * 证书编号
     */
    private Long levelId;
    /**
     * 状态
     *
     * 枚举 {@link TODO system_notice_type 对应的类}
     */
    private String status;
    /**
     * 快递公司
     */
    private String company;
    /**
     * 快递单号
     */
    private String deliveryNo;


}