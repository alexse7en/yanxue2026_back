package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.vo.page.YwQuPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 试题 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwQuMapper extends BaseMapperX<YwQuDO> {

    default PageResult<YwQuDO> selectPage(YwQuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwQuDO>()
                .eqIfPresent(YwQuDO::getQuType, reqVO.getQuType())
                .eqIfPresent(YwQuDO::getUrl, reqVO.getUrl())
                .eqIfPresent(YwQuDO::getContent, reqVO.getContent())
                .eqIfPresent(YwQuDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwQuDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwQuDO::getRemark, reqVO.getRemark())
                .eqIfPresent(YwQuDO::getAnalysis, reqVO.getAnalysis())
                .orderByDesc(YwQuDO::getId));
    }


    default int deleteByExamId(Long examId) {
        return delete(YwQuDO::getExamId, examId);
    }

    default int deleteByExamIds(List<Long> examIds) {
        return deleteBatch(YwQuDO::getExamId, examIds);
    }

    default PageResult<YwQuDO> selectPage(PageParam reqVO, Long examId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwQuDO>()
                .eq(YwQuDO::getExamId, examId)
                .orderByDesc(YwQuDO::getId));
    }

    default List<YwQuDO> selectList( Long examId) {
        return selectList( new LambdaQueryWrapperX<YwQuDO>()
                .eq(YwQuDO::getExamId, examId)
                .orderByAsc(YwQuDO::getId));
    }
}