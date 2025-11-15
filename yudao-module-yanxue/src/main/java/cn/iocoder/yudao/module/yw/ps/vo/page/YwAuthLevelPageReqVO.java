package cn.iocoder.yudao.module.yw.ps.vo.page;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评审申请分页 Request VO")
@Data
public class YwAuthLevelPageReqVO extends PageParam {

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "逻辑删除，1（true）已删除，0（false）未删除")
    private Boolean delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "分数")
    private Integer score;

    @Schema(description = "会员号", example = "27537")
    private Long memberId;

    @Schema(description = "认证等级", example = "26559")
    private Long levelId;

    @Schema(description = "审核导师")
    private String teacher;

    @Schema(description = "状态", example = "1")
    private String status;

    // 新增：查询项（可选）
    private String memberName;   // 按会员姓名模糊查
    private String idCard;       // 按身份证模糊/精确查
    private String teacherName;  // 按导师姓名模糊查
}
