package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.module.member.dal.dataobject.level.MemberLevelDO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwMemberLevelDO;
import com.sun.xml.bind.v2.TODO;
import lombok.Data;

/**
 * 评审细则 DO
 *
 * @author 芋道源码
 */

@Data

public class YwMemberLevelWithAddressVo extends YwMemberLevelDO {
    private String deliveryName;
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
     * 状态
     *
     * 枚举 {@link TODO system_notice_type 对应的类}
     */
    private String deliveryStatus;
    /**
     * 快递公司
     */
    private String company;
    /**
     * 快递单号
     */
    private String deliveryNo;
    private Long deliveryId;

}