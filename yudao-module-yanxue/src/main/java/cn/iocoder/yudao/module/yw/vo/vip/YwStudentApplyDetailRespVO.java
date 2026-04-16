package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import java.time.LocalDate;

@Data
public class YwStudentApplyDetailRespVO {

    private Long id;
    private String studentName;
    private String idCard;
    private String schoolName;
    private String className;
    private String courseName;
    private String courseHours;
    private String courseProvider;
    private LocalDate certDate;
    private String stampUnit;
}
