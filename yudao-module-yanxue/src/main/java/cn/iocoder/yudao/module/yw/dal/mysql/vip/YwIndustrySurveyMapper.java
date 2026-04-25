package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwIndustrySurveyDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwIndustrySurveyMapper extends BaseMapperX<YwIndustrySurveyDO> {

    default YwIndustrySurveyDO selectLatestByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwIndustrySurveyDO>()
                .eq(YwIndustrySurveyDO::getUserId, userId)
                .orderByDesc(YwIndustrySurveyDO::getId)
                .last("limit 1"));
    }

    default YwIndustrySurveyDO selectByUserIdAndSurveyType(Long userId, String surveyType) {
        return selectOne(new LambdaQueryWrapperX<YwIndustrySurveyDO>()
                .eq(YwIndustrySurveyDO::getUserId, userId)
                .eq(YwIndustrySurveyDO::getSurveyType, surveyType)
                .orderByDesc(YwIndustrySurveyDO::getId)
                .last("limit 1"));
    }

    default YwIndustrySurveyDO selectSubmittedByUserIdAndSurveyType(Long userId, String surveyType) {
        return selectOne(new LambdaQueryWrapperX<YwIndustrySurveyDO>()
                .eq(YwIndustrySurveyDO::getUserId, userId)
                .eq(YwIndustrySurveyDO::getSurveyType, surveyType)
                .eq(YwIndustrySurveyDO::getStatus, 1)
                .orderByDesc(YwIndustrySurveyDO::getId)
                .last("limit 1"));
    }
}
