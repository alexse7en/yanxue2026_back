package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberCourseDO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YwMemberCourseTotalVO extends YwMemberCourseDO {
    private String title;
}
