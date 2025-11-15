package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试卷 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwPaperMapper extends BaseMapperX<YwPaperDO> {

    default PageResult<YwPaperDO> selectPage(YwPaperPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwPaperDO>()
                .eqIfPresent(YwPaperDO::getExamId, reqVO.getExamId())
                .eqIfPresent(YwPaperDO::getMemberId, reqVO.getMemberId())
                .betweenIfPresent(YwPaperDO::getUserTime, reqVO.getUserTime())
                .eqIfPresent(YwPaperDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwPaperDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwPaperDO::getUserScore, reqVO.getUserScore())
                .eqIfPresent(YwPaperDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwPaperDO::getIzPass, reqVO.getIzPass())
                .orderByDesc(YwPaperDO::getId));
    }
    void  beginPaper(@Param("param") Map<String, Object> params);
    int beginPaperQu(@Param("paperId") Long paperId, @Param("list") List<YwQuDO> list);
    int beginPaperQuOption(@Param("paperId") Long paperId, @Param("quId") Long quId, @Param("list") List<YwQuOptionDO> list);
}