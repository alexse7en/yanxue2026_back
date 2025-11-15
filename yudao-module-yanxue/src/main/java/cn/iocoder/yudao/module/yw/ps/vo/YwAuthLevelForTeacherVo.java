package cn.iocoder.yudao.module.yw.ps.vo;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthLevelDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 评审申请 DO
 *
 * @author 芋道源码
 */
@Data
public class YwAuthLevelForTeacherVo extends YwAuthLevelDO {
    Long teacherId;
    String teacherStatus;


}