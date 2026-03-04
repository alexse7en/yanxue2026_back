package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgApplyRecordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwOrgApplyRecordMapper extends BaseMapperX<YwOrgApplyRecordDO> {

    default YwOrgApplyRecordDO selectLatestByMemberAndType(Long memberId, String applyType) {
        return selectOne(new LambdaQueryWrapperX<YwOrgApplyRecordDO>()
                .eq(YwOrgApplyRecordDO::getMemberId, memberId)
                .eq(YwOrgApplyRecordDO::getApplyType, applyType)
                .orderByDesc(YwOrgApplyRecordDO::getUpdateTime)
                .last("limit 1"));
    }
}
