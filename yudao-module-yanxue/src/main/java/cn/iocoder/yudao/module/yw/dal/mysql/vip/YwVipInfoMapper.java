package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwVipInfoMapper extends BaseMapperX<YwVipInfoDO> {

    default YwVipInfoDO selectByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoDO>()
                .eq(YwVipInfoDO::getUserId, userId)
                .orderByDesc(YwVipInfoDO::getId)
                .last("limit 1"));
    }
}
