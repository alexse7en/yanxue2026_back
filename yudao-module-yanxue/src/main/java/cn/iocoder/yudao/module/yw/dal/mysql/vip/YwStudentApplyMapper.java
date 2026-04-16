package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YwStudentApplyMapper extends BaseMapperX<YwStudentApplyDO> {

    default List<YwStudentApplyDO> selectListByBatchId(Long batchId) {
        return selectList(new LambdaQueryWrapperX<YwStudentApplyDO>()
                .eq(YwStudentApplyDO::getApplyBatchId, batchId)
                .orderByAsc(YwStudentApplyDO::getId));
    }

    default void deleteByBatchId(Long batchId) {
        delete(new LambdaQueryWrapperX<YwStudentApplyDO>()
                .eq(YwStudentApplyDO::getApplyBatchId, batchId));
    }
}
