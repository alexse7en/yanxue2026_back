package cn.iocoder.yudao.module.yw.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 课程新增/修改 Request VO")
@Data
public class YwCourseSaveReqVO {
    private Long id;
    @Schema(description = "讲师id", example = "12469")
    private String teacherId;

    @Schema(description = "课程分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18369")
    @NotEmpty(message = "课程分类id不能为空")
    private String category;


    @Schema(description = "课程标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "课程标题不能为空")
    private String title;

    @Schema(description = "课时数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "课时数不能为空")
    private Integer lessonNum;

    @Schema(description = "课程封面图片url", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "课程封面图片url不能为空")
    private String cover;

    @Schema(description = "课程简介", requiredMode = Schema.RequiredMode.REQUIRED, example = "随便")
    @NotEmpty(message = "课程简介不能为空")
    private String description;

}