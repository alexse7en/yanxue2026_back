package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

@Data
public class YwOrgInfoApplySaveReqVO {
    private Long id;
    private Long orginfoId;
    private String unitName;
    private String destinationName;
    private String baseTheme;
    private String unitType;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private Integer fulltimeTutorCount;
    private Integer certFulltimeTutorCount;
    private Integer parttimeTutorCount;
}
