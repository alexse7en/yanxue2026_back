package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseCategoryDO;
import cn.iocoder.yudao.module.yw.vo.page.YwCourseCategoryPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程分类 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwCourseCategoryMapper extends BaseMapperX<YwCourseCategoryDO> {

    default PageResult<YwCourseCategoryDO> selectPage(YwCourseCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwCourseCategoryDO>()
                .likeIfPresent(YwCourseCategoryDO::getCategoryName, reqVO.getCategoryName())
                .eqIfPresent(YwCourseCategoryDO::getParentId, reqVO.getParentId())
                .eqIfPresent(YwCourseCategoryDO::getLevel, reqVO.getLevel())
                .betweenIfPresent(YwCourseCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(YwCourseCategoryDO::getId));
    }

}