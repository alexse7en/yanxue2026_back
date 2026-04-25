package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveySaveReqVO;

public interface YwIndustrySurveyService {

    YwIndustrySurveyRespVO getMyIndustrySurvey(String surveyType);

    Long createIndustrySurvey(YwIndustrySurveySaveReqVO reqVO);

    void updateIndustrySurvey(YwIndustrySurveySaveReqVO reqVO);
}
