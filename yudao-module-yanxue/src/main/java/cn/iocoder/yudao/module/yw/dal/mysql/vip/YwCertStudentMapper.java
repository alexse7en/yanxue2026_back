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

    default YwCertStudentDO selectLatestByYear(Integer certYear) {
        return selectOne(new LambdaQueryWrapperX<YwCertStudentDO>()
                .eq(YwCertStudentDO::getCertYear, certYear)
                .orderByDesc(YwCertStudentDO::getCertNo)
                .last("limit 1"));
    }

    @Select("<script>" +
            "SELECT id, " +
            "       cert_no AS certNo, " +
            "       '广东省中小学生研学实践活动证书' AS certName, " +
            "       student_name AS userName, " +
            "       id_card AS idCard, " +
            "       cert_image_url AS certImageUrl, " +
            "       issue_time AS issueDate " +
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
