package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 学生证书生成申请 Response VO")
@Data
public class YwCertStudentApplyRespVO {

    private Long id;
    private String applyNo;
    private String filePath;
    private String fileType;
    private Integer parseStatus;
    private String parseError;
    private Integer parseCount;
    private Integer certStatus;
    private String certNo;
    private String certName;
    private String certUrl;
    private String downloadUrl;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
}
