package cn.iocoder.yudao.module.yw.dal.mysql;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.module.yw.vo.page.YwQuOptionPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 试题选项 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwQuOptionMapper extends BaseMapperX<YwQuOptionDO> {

    default PageResult<YwQuOptionDO> selectPage(YwQuOptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwQuOptionDO>()
                .eqIfPresent(YwQuOptionDO::getQuId, reqVO.getQuId())
                .eqIfPresent(YwQuOptionDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwQuOptionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwQuOptionDO::getIzRight, reqVO.getIzRight())
                .eqIfPresent(YwQuOptionDO::getUrl, reqVO.getUrl())
                .eqIfPresent(YwQuOptionDO::getContent, reqVO.getContent())
                .eqIfPresent(YwQuOptionDO::getAnalysis, reqVO.getAnalysis())
                .orderByDesc(YwQuOptionDO::getId));
    }

    default PageResult<YwQuOptionDO> selectPage(PageParam reqVO, Long quId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwQuOptionDO>()
                .eq(YwQuOptionDO::getQuId, quId)
                .orderByDesc(YwQuOptionDO::getId));
    }
    default List<YwQuOptionDO> selectListByQuId(Long quId) {
        return selectList(YwQuOptionDO::getQuId, quId);
    }

    default int deleteByQuId(Long quId) {
        return delete(YwQuOptionDO::getQuId, quId);
    }

    default int deleteByQuIds(List<Long> quIds) {
        return deleteBatch(YwQuOptionDO::getQuId, quIds);
    }

}