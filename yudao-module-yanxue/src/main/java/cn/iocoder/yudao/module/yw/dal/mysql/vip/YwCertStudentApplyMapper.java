package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwCertStudentApplyMapper extends BaseMapperX<YwCertStudentApplyDO> {

    default PageResult<YwCertStudentApplyDO> selectPageMy(Long userId, YwCertStudentApplyPageReqVO reqVO) {
        LambdaQueryWrapperX<YwCertStudentApplyDO> queryWrapper = new LambdaQueryWrapperX<YwCertStudentApplyDO>()
                .eq(YwCertStudentApplyDO::getUserId, userId)
                .orderByDesc(YwCertStudentApplyDO::getId);
        if (reqVO.getKeyword() != null && !reqVO.getKeyword().trim().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper.like(YwCertStudentApplyDO::getApplyNo, reqVO.getKeyword())
                    .or().like(YwCertStudentApplyDO::getCertNo, reqVO.getKeyword())
                    .or().like(YwCertStudentApplyDO::getCertName, reqVO.getKeyword()));
        }
        return selectPage(reqVO, queryWrapper);
    }
}
