package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwIndustrySurveyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface YwIndustrySurveyConvert {

    YwIndustrySurveyConvert INSTANCE = Mappers.getMapper(YwIndustrySurveyConvert.class);

    YwIndustrySurveyRespVO convert(YwIndustrySurveyDO bean);
}
