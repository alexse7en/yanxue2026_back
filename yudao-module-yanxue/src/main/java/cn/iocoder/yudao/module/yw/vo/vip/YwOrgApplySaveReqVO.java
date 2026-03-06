package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

@Data
public class YwOrgApplySaveReqVO {
    private Long id;
    private Long vipinfoId;
    private String applyType;
    private String filePath;
    private String fileType;
    private String unitName;
    private String destinationName;
    private String baseTheme;
    private String unitType;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
}
