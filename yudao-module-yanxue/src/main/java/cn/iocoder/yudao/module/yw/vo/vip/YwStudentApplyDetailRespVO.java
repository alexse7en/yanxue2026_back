package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

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
    private LocalDate courseDate;
    private LocalDate stampDate;
    private String stampUnit;
    private Boolean valid;
    private Map<String, String> fieldErrors;
}
