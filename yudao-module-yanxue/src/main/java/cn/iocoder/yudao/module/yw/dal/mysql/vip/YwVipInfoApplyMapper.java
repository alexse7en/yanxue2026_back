package cn.iocoder.yudao.module.yw.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyPageReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YwVipInfoApplyMapper extends BaseMapperX<YwVipInfoApplyDO> {

    default YwVipInfoApplyDO selectLatestByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoApplyDO>()
                .eq(YwVipInfoApplyDO::getUserId, userId)
                .orderByDesc(YwVipInfoApplyDO::getId)
                .last("limit 1"));
    }

    default YwVipInfoApplyDO selectByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<YwVipInfoApplyDO>()
                .eq(YwVipInfoApplyDO::getUserId, userId)
                .last("limit 1"));
    }

    default PageResult<YwVipInfoApplyDO> selectPage(YwVipInfoApplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwVipInfoApplyDO>()
                .likeIfPresent(YwVipInfoApplyDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(YwVipInfoApplyDO::getApplyStatus, reqVO.getApplyStatus())
                .betweenIfPresent(YwVipInfoApplyDO::getCreateTime,
                        reqVO.getBeginApplyTime() == null ? null : reqVO.getBeginApplyTime().atStartOfDay(),
                        reqVO.getEndApplyTime() == null ? null : reqVO.getEndApplyTime().plusDays(1).atStartOfDay())
                .orderByDesc(YwVipInfoApplyDO::getId));
    }
}
