package cn.iocoder.yudao.module.system.dal.mysql.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.logger.LoginLogDO;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogDO> {

    default PageResult<LoginLogDO> selectPage(LoginLogPageReqVO reqVO) {
        LambdaQueryWrapperX<LoginLogDO> query = new LambdaQueryWrapperX<LoginLogDO>()
                .likeIfPresent(LoginLogDO::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLogDO::getUsername, reqVO.getUsername())
                .betweenIfPresent(LoginLogDO::getCreateTime, reqVO.getCreateTime());
        if (Boolean.TRUE.equals(reqVO.getStatus())) {
            query.eq(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
            query.gt(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        }
        query.orderByDesc(LoginLogDO::getId); // 降序
        return selectPage(reqVO, query);
    }
    @Select("SELECT COUNT(DISTINCT user_id) FROM system_login_log " +
            "WHERE user_type = #{userType} AND log_type = #{logType} AND result = #{result} " +
            "AND create_time BETWEEN #{begin} AND #{end}")
    Long selectDistinctUserCount(@Param("userType") Integer userType,
                                 @Param("logType") Integer logType,
                                 @Param("result") Integer result,
                                 @Param("begin") LocalDateTime begin,
                                 @Param("end") LocalDateTime end);

    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS d, COUNT(DISTINCT user_id) AS c " +
            "FROM system_login_log WHERE user_type = #{userType} AND result = 0 " + // 0=成功（按你的枚举调整）
            "AND create_time >= #{begin} GROUP BY d")
    List<Map<String, Object>> selectDailyDau(@Param("userType") Integer userType,
                                             @Param("begin") LocalDateTime begin);

}
