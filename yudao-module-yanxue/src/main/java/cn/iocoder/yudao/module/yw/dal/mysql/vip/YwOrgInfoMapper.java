package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YwOrgInfoMapper extends BaseMapperX<YwOrgInfoDO> {

    default List<YwOrgInfoDO> selectMyPassedList(Long memberId) {
        return selectList(new LambdaQueryWrapperX<YwOrgInfoDO>()
                .eq(YwOrgInfoDO::getMemberId, memberId)
                .eq(YwOrgInfoDO::getStatus, 1)
                .orderByDesc(YwOrgInfoDO::getUpdateTime));
    }
}
