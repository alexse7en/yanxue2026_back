package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.vo.page.YwExamPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考卷设计 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwExamMapper extends BaseMapperX<YwExamDO> {

    default PageResult<YwExamDO> selectPage(YwExamPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwExamDO>()
                .eqIfPresent(YwExamDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwExamDO::getContent, reqVO.getContent())
                .eqIfPresent(YwExamDO::getCourseId, reqVO.getCourseId())
                .eqIfPresent(YwExamDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwExamDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwExamDO::getTimeLimit, reqVO.getTimeLimit())
                .betweenIfPresent(YwExamDO::getTotalTime, reqVO.getTotalTime())
                .eqIfPresent(YwExamDO::getTotalScore, reqVO.getTotalScore())
                .eqIfPresent(YwExamDO::getQualifyScore, reqVO.getQualifyScore())
                .orderByDesc(YwExamDO::getId));
    }

}