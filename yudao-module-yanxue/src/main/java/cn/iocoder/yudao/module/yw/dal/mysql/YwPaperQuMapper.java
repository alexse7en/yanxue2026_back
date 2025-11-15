package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 试卷题目 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwPaperQuMapper extends BaseMapperX<YwPaperQuDO> {

    default PageResult<YwPaperQuDO> selectPage(YwPaperQuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwPaperQuDO>()
                .eqIfPresent(YwPaperQuDO::getIzAnswered, reqVO.getIzAnswered())
                .eqIfPresent(YwPaperQuDO::getAnswer, reqVO.getAnswer())
                .eqIfPresent(YwPaperQuDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(YwPaperQuDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwPaperQuDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwPaperQuDO::getPaperId, reqVO.getPaperId())
                .eqIfPresent(YwPaperQuDO::getQuId, reqVO.getQuId())
                .eqIfPresent(YwPaperQuDO::getRealAnswer, reqVO.getRealAnswer())
                .orderByDesc(YwPaperQuDO::getId));
    }

    default int deleteByPaperId(Long paperId) {
        return delete(YwPaperQuDO::getPaperId, paperId);
    }

    default int deleteByPaperIds(List<Long> paperIds) {
        return deleteBatch(YwPaperQuDO::getPaperId, paperIds);
    }

    default PageResult<YwPaperQuDO> selectPage(PageParam reqVO, Long paperId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwPaperQuDO>()
                .eq(YwPaperQuDO::getPaperId, paperId)
                .orderByDesc(YwPaperQuDO::getId));
    }

    default List<YwPaperQuDO> selectList( Long paperId) {
        return selectList( new LambdaQueryWrapperX<YwPaperQuDO>()
                .eq(YwPaperQuDO::getPaperId, paperId)
                .orderByDesc(YwPaperQuDO::getId));
    }
}