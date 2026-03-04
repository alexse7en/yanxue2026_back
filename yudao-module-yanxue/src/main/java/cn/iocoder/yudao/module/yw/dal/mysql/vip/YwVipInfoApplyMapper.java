package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoApplyDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwVipInfoApplyMapper extends BaseMapperX<YwVipInfoApplyDO> {

    default YwVipInfoApplyDO selectLatestByMemberId(Long memberId) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoApplyDO>()
                .eq(YwVipInfoApplyDO::getMemberId, memberId)
                .orderByDesc(YwVipInfoApplyDO::getUpdateTime)
                .last("limit 1"));
    }
}
