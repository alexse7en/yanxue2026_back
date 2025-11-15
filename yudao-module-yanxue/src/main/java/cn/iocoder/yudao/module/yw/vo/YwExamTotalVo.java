package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class YwExamTotalVo extends YwExamDO {
    List<YwQuVo> qus=new ArrayList<>();
}
