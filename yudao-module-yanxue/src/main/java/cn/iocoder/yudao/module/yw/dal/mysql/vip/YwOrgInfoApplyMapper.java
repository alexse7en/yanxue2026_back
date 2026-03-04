package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoApplyDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwOrgInfoApplyMapper extends BaseMapperX<YwOrgInfoApplyDO> {

    default YwOrgInfoApplyDO selectLatestByMemberAndOrginfoId(Long memberId, Long orginfoId) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoApplyDO>()
                .eq(YwOrgInfoApplyDO::getMemberId, memberId)
                .eq(YwOrgInfoApplyDO::getOrginfoId, orginfoId)
                .orderByDesc(YwOrgInfoApplyDO::getUpdateTime)
                .last("limit 1"));
    }
}
