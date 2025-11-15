package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class YwQuVo extends YwQuDO {
    List<YwQuOptionDO> options=new ArrayList<>();

    public  String realAnswer;
}
