package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class YwStudentApplyDetailSaveReqVO {

    @NotNull(message = "学生明细 ID 不能为空")
    private Long id;
    private String studentName;
    private String idCard;
    private String schoolName;
    private String className;
    private String courseName;
    private String courseHours;
    private String courseProvider;
    private LocalDate certDate;
    private LocalDate courseDate;
    private LocalDate stampDate;
    private String stampUnit;
}
