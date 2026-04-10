package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YwCertStudentMapper extends BaseMapperX<YwCertStudentDO> {

    default List<YwCertStudentDO> selectListByUserAndFilePath(Long userId, String uploadFilePath) {
        return selectList(new LambdaQueryWrapperX<YwCertStudentDO>()
                .eq(YwCertStudentDO::getUserId, userId)
                .eq(YwCertStudentDO::getUploadFilePath, uploadFilePath)
                .orderByAsc(YwCertStudentDO::getId));
    }

    default void deleteByUserAndFilePath(Long userId, String uploadFilePath) {
        delete(new LambdaQueryWrapperX<YwCertStudentDO>()
                .eq(YwCertStudentDO::getUserId, userId)
                .eq(YwCertStudentDO::getUploadFilePath, uploadFilePath));
    }
}
