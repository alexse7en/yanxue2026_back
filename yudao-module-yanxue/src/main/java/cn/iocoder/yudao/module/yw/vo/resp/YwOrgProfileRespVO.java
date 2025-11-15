package cn.iocoder.yudao.module.yw.vo.resp;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class YwOrgProfileRespVO {
    /** 0-否，1-是 */
    private Integer izvip;
    /** 逗号分隔的荣誉代码，如 "1,2,3" */
    private String  honorCodes;     // 原始字符串，如 "bigten,science_star"
    private List<HonorItem> honors; // 解析后的明细

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HonorItem {
        private Long   id;        // 字典数据 id（3018）
        private String code;      // 字典 value（bigten）
        private String label;     // 字典 label（年度十大）
        private String imageUrl;  // 字典 remark（图片 URL）
    }
}
