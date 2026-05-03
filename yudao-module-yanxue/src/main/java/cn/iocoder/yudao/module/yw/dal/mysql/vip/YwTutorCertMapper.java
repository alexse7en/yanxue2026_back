package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwTutorCertDO;
import cn.iocoder.yudao.module.yw.vo.portal.query.YwPortalCertQueryReqVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalCertRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface YwTutorCertMapper extends BaseMapperX<YwTutorCertDO> {

    @Select("<script>" +
            "SELECT id, " +
            "       certificate_no AS certNo, " +
            "       CASE " +
            "         WHEN post IS NOT NULL AND post != '' THEN CONCAT(post, '研学指导师证书') " +
            "         ELSE '研学指导师证书' " +
            "       END AS certName, " +
            "       name AS userName, " +
            "       people_id AS idCard, " +
            "       NULL AS certImageUrl, " +
            "       effective_data AS issueDate " +
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
    List<YwPortalCertRespVO> selectPortalTutorCertList(@Param("reqVO") YwPortalCertQueryReqVO reqVO);
}
