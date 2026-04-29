package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveySaveReqVO;

public interface YwIndustrySurveyService {

    PageResult<YwIndustrySurveyRespVO> getIndustrySurveyPage(YwIndustrySurveyPageReqVO pageReqVO);

    YwIndustrySurveyRespVO getIndustrySurvey(Long id);

    YwIndustrySurveyRespVO getMyIndustrySurvey(String surveyType);

    Long createIndustrySurvey(YwIndustrySurveySaveReqVO reqVO);

    void updateIndustrySurvey(YwIndustrySurveySaveReqVO reqVO);

    void updateIndustrySurveyForDataCenter(YwIndustrySurveySaveReqVO reqVO);
}
