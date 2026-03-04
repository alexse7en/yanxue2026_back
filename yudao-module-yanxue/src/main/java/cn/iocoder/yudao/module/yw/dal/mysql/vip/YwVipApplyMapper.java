package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipApplyDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwVipApplyMapper extends BaseMapperX<YwVipApplyDO> {

    default YwVipApplyDO selectLatestByMemberId(Long memberId) {
        return selectOne(new LambdaQueryWrapperX<YwVipApplyDO>()
                .eq(YwVipApplyDO::getMemberId, memberId)
                .orderByDesc(YwVipApplyDO::getUpdateTime)
                .last("limit 1"));
    }
}
