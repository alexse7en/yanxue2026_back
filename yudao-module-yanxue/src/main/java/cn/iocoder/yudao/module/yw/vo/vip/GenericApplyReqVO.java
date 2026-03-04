package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import java.util.Map;

@Data
public class GenericApplyReqVO {

    private Long id;
    private Long orginfoId;
    private Long vipinfoId;
    private String applyType;
    private Map<String, Object> formData;
}
