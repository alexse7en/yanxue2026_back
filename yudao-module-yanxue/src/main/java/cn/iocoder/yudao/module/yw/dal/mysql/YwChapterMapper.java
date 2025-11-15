package cn.iocoder.yudao.module.yw.dal.mysql;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.module.yw.vo.page.YwChapterPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 章节 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwChapterMapper extends BaseMapperX<YwChapterDO> {

    default PageResult<YwChapterDO> selectPage(YwChapterPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwChapterDO>()
                .eqIfPresent(YwChapterDO::getCourseId, reqVO.getCourseId())
                .likeIfPresent(YwChapterDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwChapterDO::getVideoSourceId, reqVO.getVideoSourceId())
                .likeIfPresent(YwChapterDO::getVideoOriginalName, reqVO.getVideoOriginalName())
                .betweenIfPresent(YwChapterDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(YwChapterDO::getId));
    }

    default int deleteByCourseId(Long courseId) {
        return delete(YwChapterDO::getCourseId, courseId);
    }

    default int deleteByCourseIds(List<Long> courseIds) {
        return deleteBatch(YwChapterDO::getCourseId, courseIds);
    }

    default PageResult<YwChapterDO> selectPage(PageParam reqVO, Long courseId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwChapterDO>()
                .eq(YwChapterDO::getCourseId, courseId)
                .orderByDesc(YwChapterDO::getId));
    }
}