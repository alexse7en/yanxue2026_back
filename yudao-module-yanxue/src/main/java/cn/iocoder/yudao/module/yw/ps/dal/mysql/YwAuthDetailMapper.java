package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthDetailDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 志愿者等级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwAuthDetailMapper extends BaseMapperX<YwAuthDetailDO> {

    default PageResult<YwAuthDetailDO> selectPage(YwAuthDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwAuthDetailDO>()
                .likeIfPresent(YwAuthDetailDO::getName, reqVO.getName())
                .eqIfPresent(YwAuthDetailDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwAuthDetailDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwAuthDetailDO::getScore, reqVO.getScore())
                .eqIfPresent(YwAuthDetailDO::getTeacher, reqVO.getTeacher())
                .eqIfPresent(YwAuthDetailDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwAuthDetailDO::getAuthId, reqVO.getAuthId())
                .eqIfPresent(YwAuthDetailDO::getDetailId, reqVO.getDetailId())
                .orderByDesc(YwAuthDetailDO::getId));
    }

}