package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoApplyDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwOrgInfoApplyMapper extends BaseMapperX<YwOrgInfoApplyDO> {

    default YwOrgInfoApplyDO selectLatestByUserAndOrginfoId(Long userId, Long orginfoId) {
        return selectOne(new LambdaQueryWrapperX<YwOrgInfoApplyDO>()
                .eq(YwOrgInfoApplyDO::getUserId, userId)
                .eq(YwOrgInfoApplyDO::getOrginfoId, orginfoId)
                .orderByDesc(YwOrgInfoApplyDO::getId)
                .last("limit 1"));
    }
}
