package cn.iocoder.yudao.module.yw.dal.mysql;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwStudyBaseDO;
import cn.iocoder.yudao.module.yw.vo.page.YwStudyBasePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.yw.vo.*;

/**
 * 研学基地/营地 Mapper
 *
 * @author 科协超级管理员
 */
@Mapper
public interface YwStudyBaseMapper extends BaseMapperX<YwStudyBaseDO> {

    default PageResult<YwStudyBaseDO> selectPage(YwStudyBasePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwStudyBaseDO>()
                .likeIfPresent(YwStudyBaseDO::getName, reqVO.getName())
                .likeIfPresent(YwStudyBaseDO::getShortName, reqVO.getShortName())
                .eqIfPresent(YwStudyBaseDO::getProvince, reqVO.getProvince())
                .eqIfPresent(YwStudyBaseDO::getCity, reqVO.getCity())
                .eqIfPresent(YwStudyBaseDO::getDistrict, reqVO.getDistrict())
                .eqIfPresent(YwStudyBaseDO::getAddress, reqVO.getAddress())
                .eqIfPresent(YwStudyBaseDO::getThemeType, reqVO.getThemeType())
                .eqIfPresent(YwStudyBaseDO::getAgeRange, reqVO.getAgeRange())
                .eqIfPresent(YwStudyBaseDO::getIntro, reqVO.getIntro())
                .eqIfPresent(YwStudyBaseDO::getDetailContent, reqVO.getDetailContent())
                .eqIfPresent(YwStudyBaseDO::getMainImageUrl, reqVO.getMainImageUrl())
                .eqIfPresent(YwStudyBaseDO::getParkImage1Url, reqVO.getParkImage1Url())
                .eqIfPresent(YwStudyBaseDO::getParkImage2Url, reqVO.getParkImage2Url())
                .eqIfPresent(YwStudyBaseDO::getActivityImage1Url, reqVO.getActivityImage1Url())
                .eqIfPresent(YwStudyBaseDO::getActivityImage2Url, reqVO.getActivityImage2Url())
                .eqIfPresent(YwStudyBaseDO::getCourse1Desc, reqVO.getCourse1Desc())
                .eqIfPresent(YwStudyBaseDO::getCourse2Desc, reqVO.getCourse2Desc())
                .eqIfPresent(YwStudyBaseDO::getXPercent, reqVO.getXPercent())
                .eqIfPresent(YwStudyBaseDO::getYPercent, reqVO.getYPercent())
                .eqIfPresent(YwStudyBaseDO::getIsRecommend, reqVO.getIsRecommend())
                .eqIfPresent(YwStudyBaseDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwStudyBaseDO::getSort, reqVO.getSort())
                .betweenIfPresent(YwStudyBaseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(YwStudyBaseDO::getId));
    }

    // ========= 新增：供地图使用的列表查询 =========

    /**
     * 地图点位用列表查询：
     * - status = 1（启用）
     * - xPercent / yPercent 非空
     * - city / themeType / isRecommend 可选过滤
     */
    default List<YwStudyBaseDO> selectListForMap(String city, String themeType, Boolean isRecommend) {
        return selectList(new LambdaQueryWrapperX<YwStudyBaseDO>()
                .eqIfPresent(YwStudyBaseDO::getCity, city)
                .eqIfPresent(YwStudyBaseDO::getThemeType, themeType)
                .eqIfPresent(YwStudyBaseDO::getIsRecommend, isRecommend)
                .eq(YwStudyBaseDO::getStatus, 1) // 如果 DO 是 Boolean；如果是 Integer，就写 1
                .isNotNull(YwStudyBaseDO::getXPercent)
                .isNotNull(YwStudyBaseDO::getYPercent)
                .orderByAsc(YwStudyBaseDO::getSort)
                .orderByAsc(YwStudyBaseDO::getId));
    }

}