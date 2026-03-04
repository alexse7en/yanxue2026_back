package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwVipInfoMapper extends BaseMapperX<YwVipInfoDO> {

    default YwVipInfoDO selectMyActive(Long memberId) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoDO>()
                .eq(YwVipInfoDO::getMemberId, memberId)
                .eq(YwVipInfoDO::getStatus, 1)
                .orderByDesc(YwVipInfoDO::getUpdateTime)
                .last("limit 1"));
    }
}
