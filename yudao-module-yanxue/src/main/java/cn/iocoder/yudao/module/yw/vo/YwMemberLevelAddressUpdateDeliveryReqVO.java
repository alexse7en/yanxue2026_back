package cn.iocoder.yudao.module.yw.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class YwMemberLevelAddressUpdateDeliveryReqVO {
    @NotNull(message = "memberId 不能为空")
    private Long memberId;

    @NotNull(message = "levelId 不能为空")
    private Long levelId;

    @NotBlank(message = "快递公司不能为空")
    private String company;

    @NotBlank(message = "快递单号不能为空")
    private String deliveryNo;
}
