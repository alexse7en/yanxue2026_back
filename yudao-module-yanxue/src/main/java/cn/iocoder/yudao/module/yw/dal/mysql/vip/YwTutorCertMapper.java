package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwTutorCertDO;
import cn.iocoder.yudao.module.yw.vo.portal.query.YwPortalCertQueryReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface YwTutorCertMapper extends BaseMapperX<YwTutorCertDO> {

    @Select("<script>" +
            "SELECT id, name, people_id, certificate_no, sex, post, score, grade, qr_code, avatar, effective_data, " +
            "       certpic, create_time, update_time " +
            "FROM yx_certificate_retrieval1 " +
            "WHERE 1 = 1 " +
            "<choose>" +
            "  <when test='reqVO.certNo != null and reqVO.certNo != \"\"'>" +
            "    AND certificate_no = #{reqVO.certNo} " +
            "  </when>" +
            "  <otherwise>" +
            "    AND name = #{reqVO.name} " +
            "    AND people_id LIKE CONCAT('%', #{reqVO.idCardSuffix}) " +
            "  </otherwise>" +
            "</choose>" +
            "ORDER BY id DESC " +
            "LIMIT 20" +
            "</script>")
    List<YwTutorCertDO> selectPortalTutorCertList(@Param("reqVO") YwPortalCertQueryReqVO reqVO);

    @Update("UPDATE yx_certificate_retrieval1 SET certpic = #{certpic}, update_time = NOW() WHERE id = #{id}")
    int updateCertpic(@Param("id") Long id, @Param("certpic") String certpic);
}
