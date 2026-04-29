package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.util.MyBatisUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwIndustrySurveyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    default PageResult<YwIndustrySurveyRespVO> selectDataCenterPage(YwIndustrySurveyPageReqVO reqVO) {
        IPage<YwIndustrySurveyRespVO> mpPage = MyBatisUtils.buildPage(reqVO);
        List<YwIndustrySurveyRespVO> list = selectDataCenterPage(mpPage, reqVO);
        return new PageResult<>(list, mpPage.getTotal());
    }

    @Select("<script>" +
            "SELECT s.*, " +
            "       u.nickname AS userName, " +
            "       v.company_name AS vipinfoName " +
            "FROM yw_yanxue_industry_survey s " +
            "LEFT JOIN system_users u ON u.id = s.user_id AND u.deleted = 0 " +
            "LEFT JOIN yw_yanxue_vipinfo v ON v.id = s.vipinfo_id " +
            "WHERE s.deleted = 0 " +
            "<if test='reqVO.id != null'> AND s.id = #{reqVO.id} </if>" +
            "<if test='reqVO.userId != null'> AND s.user_id = #{reqVO.userId} </if>" +
            "<if test='reqVO.userName != null and reqVO.userName != \"\"'>" +
            "  AND (u.nickname LIKE CONCAT('%', #{reqVO.userName}, '%') OR u.username LIKE CONCAT('%', #{reqVO.userName}, '%')) " +
            "</if>" +
            "<if test='reqVO.vipinfoId != null'> AND s.vipinfo_id = #{reqVO.vipinfoId} </if>" +
            "<if test='reqVO.vipinfoName != null and reqVO.vipinfoName != \"\"'>" +
            "  AND v.company_name LIKE CONCAT('%', #{reqVO.vipinfoName}, '%') " +
            "</if>" +
            "<if test='reqVO.surveyType != null and reqVO.surveyType != \"\"'> AND s.survey_type = #{reqVO.surveyType} </if>" +
            "ORDER BY s.id DESC" +
            "</script>")
    List<YwIndustrySurveyRespVO> selectDataCenterPage(IPage<YwIndustrySurveyRespVO> page,
                                                      @Param("reqVO") YwIndustrySurveyPageReqVO reqVO);

    @Select("SELECT s.*, " +
            "       u.nickname AS userName, " +
            "       v.company_name AS vipinfoName " +
            "FROM yw_yanxue_industry_survey s " +
            "LEFT JOIN system_users u ON u.id = s.user_id AND u.deleted = 0 " +
            "LEFT JOIN yw_yanxue_vipinfo v ON v.id = s.vipinfo_id " +
            "WHERE s.deleted = 0 AND s.id = #{id} " +
            "LIMIT 1")
    YwIndustrySurveyRespVO selectDataCenterById(@Param("id") Long id);
}
