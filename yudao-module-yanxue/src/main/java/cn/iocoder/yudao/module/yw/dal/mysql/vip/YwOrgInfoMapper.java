package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YwOrgInfoMapper extends BaseMapperX<YwOrgInfoDO> {

    default List<YwOrgInfoDO> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getUserId, userId)
                .orderByDesc(YwOrgInfoDO::getId));
    }
}
