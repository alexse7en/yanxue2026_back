package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuOptionPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 试卷选项 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwPaperQuOptionMapper extends BaseMapperX<YwPaperQuOptionDO> {

    default PageResult<YwPaperQuOptionDO> selectPage(YwPaperQuOptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwPaperQuOptionDO>()
                .eqIfPresent(YwPaperQuOptionDO::getIzAnswered, reqVO.getIzAnswered())
                .eqIfPresent(YwPaperQuOptionDO::getAbcd, reqVO.getAbcd())
                .eqIfPresent(YwPaperQuOptionDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(YwPaperQuOptionDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwPaperQuOptionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwPaperQuOptionDO::getOptionId, reqVO.getOptionId())
                .eqIfPresent(YwPaperQuOptionDO::getQuId, reqVO.getQuId())
                .eqIfPresent(YwPaperQuOptionDO::getIzRight, reqVO.getIzRight())
                .orderByDesc(YwPaperQuOptionDO::getId));
    }

    default int deleteByQuId(Long quId) {
        return delete(YwPaperQuOptionDO::getQuId, quId);
    }

    default int deleteByQuIds(List<Long> quIds) {
        return deleteBatch(YwPaperQuOptionDO::getQuId, quIds);
    }

    default PageResult<YwPaperQuOptionDO> selectPage(PageParam reqVO, Long quId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwPaperQuOptionDO>()
                .eq(YwPaperQuOptionDO::getQuId, quId)
                .orderByDesc(YwPaperQuOptionDO::getId));
    }

    @Select("select a.* from yw_paper_qu_option a inner join yw_paper_qu b on a.qu_id=b.id where b.paper_id=#{paperId}")
    List<YwPaperQuOptionDO> selecListByPaperId(@Param("paperId") Long paperId);

    @Select("select a.* from yw_qu_option a inner join yw_qu b on a.qu_id=b.id  where b.exam_id=#{examId}")
    List<YwQuOptionDO> selecListByExamId(@Param("examId") Long examId);
}