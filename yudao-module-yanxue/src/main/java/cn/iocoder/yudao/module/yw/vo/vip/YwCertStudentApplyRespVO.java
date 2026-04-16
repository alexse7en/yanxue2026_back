package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 学生证书申请批次 Response VO")
@Data
public class YwCertStudentApplyRespVO {

    private Long id;
    private String applyNo;
    private Integer applyStatus;
    private String uploadFilePath;
    private String fileType;
    private Integer parseStatus;
    private String parseError;
    private Integer parseCount;
    private String downloadUrl;
    private String auditRemark;
    private LocalDateTime auditTime;
    private Long auditorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<YwStudentApplyDetailRespVO> details;
}
