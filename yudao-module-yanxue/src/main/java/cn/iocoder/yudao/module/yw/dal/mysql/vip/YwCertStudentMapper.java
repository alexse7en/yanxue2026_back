package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentDO;
import cn.iocoder.yudao.module.yw.vo.portal.query.YwPortalCertQueryReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalCertRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface YwCertStudentMapper extends BaseMapperX<YwCertStudentDO> {

    default List<YwCertStudentDO> selectListByApplyDetailIds(List<Long> applyDetailIds) {
        return selectList(new LambdaQueryWrapperX<YwCertStudentDO>()
                .in(YwCertStudentDO::getApplyDetailId, applyDetailIds)
                .orderByAsc(YwCertStudentDO::getId));
    }

    default void deleteByApplyDetailIds(List<Long> applyDetailIds) {
        delete(new LambdaQueryWrapperX<YwCertStudentDO>()
                .in(YwCertStudentDO::getApplyDetailId, applyDetailIds));
    }

    /**
     * 查询指定年度已使用的最大流水号，包含逻辑删除记录。
     *
     * cert_no 的唯一索引不会忽略 deleted = 1 的记录，因此编号不能复用。
     */
    @Select("SELECT COALESCE(MAX(CAST(SUBSTRING(cert_no, 10) AS UNSIGNED)), 0) " +
            "FROM yw_yanxue_cert_student " +
            "WHERE cert_year = #{certYear} " +
            "AND cert_no LIKE CONCAT('CCPST', #{certYear}, '%')")
    Integer selectMaxSequenceByYearIncludingDeleted(@Param("certYear") Integer certYear);

    @Select("<script>" +
            "SELECT id, " +
            "       cert_no AS certNo, " +
            "       '广东省中小学生研学实践活动证书' AS certName, " +
            "       student_name AS userName, " +
            "       id_card AS idCard, " +
            "       cert_image_url AS certImageUrl, " +
            "       DATE_FORMAT(issue_time, '%Y-%m-%d %H:%i:%s') AS issueDate " +
            "FROM yw_yanxue_cert_student " +
            "WHERE deleted = 0 " +
            "<if test='reqVO.name != null and reqVO.name != \"\"'>" +
            "  AND student_name LIKE CONCAT('%', #{reqVO.name}, '%') " +
            "</if>" +
            "<if test='reqVO.idCardSuffix != null and reqVO.idCardSuffix != \"\"'>" +
            "  AND id_card LIKE CONCAT('%', #{reqVO.idCardSuffix}) " +
            "</if>" +
            "<if test='reqVO.certNo != null and reqVO.certNo != \"\"'>" +
            "  AND cert_no = #{reqVO.certNo} " +
            "</if>" +
            "ORDER BY issue_time DESC, id DESC" +
            "</script>")
    List<YwPortalCertRespVO> selectPortalStudentCertList(@Param("reqVO") YwPortalCertQueryReqVO reqVO);
}
