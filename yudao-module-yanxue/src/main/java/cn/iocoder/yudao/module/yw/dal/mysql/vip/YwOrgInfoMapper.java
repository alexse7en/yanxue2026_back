package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.util.MyBatisUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.vo.portal.page.YwPortalOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper
public interface YwOrgInfoMapper extends BaseMapperX<YwOrgInfoDO> {

    default List<YwOrgInfoDO> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getUserId, userId)
                .orderByDesc(YwOrgInfoDO::getId));
    }

    default YwOrgInfoDO selectByApplyId(Long applyId) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getApplyId, applyId)
                .last("limit 1"));
    }

    default YwOrgInfoDO selectByUserIdAndOrgType(Long userId, String orgType) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getUserId, userId)
                .eqIfPresent(YwOrgInfoDO::getOrgType, orgType)
                .orderByDesc(YwOrgInfoDO::getId)
                .last("limit 1"));
    }

    default PageResult<YwOrgInfoDO> selectPortalPage(YwPortalOrgInfoPageReqVO reqVO) {
        LambdaQueryWrapperX<YwOrgInfoDO> queryWrapper = new LambdaQueryWrapperX<YwOrgInfoDO>()
                .likeIfPresent(YwOrgInfoDO::getUnitName, reqVO.getUnitName())
                .eqIfPresent(YwOrgInfoDO::getOrgType, reqVO.getOrgType())
                .eqIfPresent(YwOrgInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(YwOrgInfoDO::getId);
        queryWrapper.and(StringUtils.hasText(reqVO.getKeyword()),
                wrapper -> wrapper.like(YwOrgInfoDO::getUnitName, reqVO.getKeyword())
                        .or().like(YwOrgInfoDO::getAddress, reqVO.getKeyword())
                        .or().like(YwOrgInfoDO::getDestinationName, reqVO.getKeyword()));
        return selectPage(reqVO, queryWrapper);
    }

    default PageResult<YwOrgInfoRespVO> selectDataCenterPage(YwOrgInfoPageReqVO reqVO) {
        IPage<YwOrgInfoRespVO> mpPage = MyBatisUtils.buildPage(reqVO);
        List<YwOrgInfoRespVO> list = selectDataCenterPage(mpPage, reqVO);
        return new PageResult<>(list, mpPage.getTotal());
    }

    @Select("<script>" +
            "SELECT o.*, " +
            "       o.cert_tutor_count AS certTutorCount, " +
            "       o.cert_tutor_count AS fulltimeTutorCount, " +
            "       u.nickname AS userName, " +
            "       v.company_name AS vipinfoName " +
            "FROM yw_yanxue_orginfo o " +
            "LEFT JOIN system_users u ON u.id = o.user_id AND u.deleted = 0 " +
            "LEFT JOIN yw_yanxue_vipinfo v ON v.id = o.vipinfo_id " +
            "WHERE o.deleted = 0 " +
            "<if test='reqVO.id != null'> AND o.id = #{reqVO.id} </if>" +
            "<if test='reqVO.userId != null'> AND o.user_id = #{reqVO.userId} </if>" +
            "<if test='reqVO.userName != null and reqVO.userName != \"\"'>" +
            "  AND (u.nickname LIKE CONCAT('%', #{reqVO.userName}, '%') OR u.username LIKE CONCAT('%', #{reqVO.userName}, '%')) " +
            "</if>" +
            "<if test='reqVO.vipinfoId != null'> AND o.vipinfo_id = #{reqVO.vipinfoId} </if>" +
            "<if test='reqVO.vipinfoName != null and reqVO.vipinfoName != \"\"'>" +
            "  AND v.company_name LIKE CONCAT('%', #{reqVO.vipinfoName}, '%') " +
            "</if>" +
            "<if test='reqVO.orgType != null and reqVO.orgType != \"\"'> AND o.org_type = #{reqVO.orgType} </if>" +
            "<if test='reqVO.unitName != null and reqVO.unitName != \"\"'> AND o.unit_name LIKE CONCAT('%', #{reqVO.unitName}, '%') </if>" +
            "<if test='reqVO.baseTheme != null and reqVO.baseTheme != \"\"'> AND o.base_theme LIKE CONCAT('%', #{reqVO.baseTheme}, '%') </if>" +
            "ORDER BY o.id DESC" +
            "</script>")
    List<YwOrgInfoRespVO> selectDataCenterPage(IPage<YwOrgInfoRespVO> page, @Param("reqVO") YwOrgInfoPageReqVO reqVO);

    @Select("SELECT o.*, " +
            "       o.cert_tutor_count AS certTutorCount, " +
            "       o.cert_tutor_count AS fulltimeTutorCount, " +
            "       u.nickname AS userName, " +
            "       v.company_name AS vipinfoName " +
            "FROM yw_yanxue_orginfo o " +
            "LEFT JOIN system_users u ON u.id = o.user_id AND u.deleted = 0 " +
            "LEFT JOIN yw_yanxue_vipinfo v ON v.id = o.vipinfo_id " +
            "WHERE o.deleted = 0 AND o.id = #{id} " +
            "LIMIT 1")
    YwOrgInfoRespVO selectDataCenterById(@Param("id") Long id);
}
