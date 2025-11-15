package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper()
public interface YwOrganizationInfoMapper extends BaseMapper<YwOrganizationInfoDO> {

    @Select("SELECT COUNT(1) FROM yw_org_apply WHERE username = #{username} AND status = #{status}")
    int selectCountByUsernameAndStatus(@Param("username") String username, @Param("status") Integer status);

    @Select("SELECT COUNT(1) FROM yw_org_apply WHERE status = #{status}")
    Long selectCountByStatus(@Param("status") Integer status);
}

