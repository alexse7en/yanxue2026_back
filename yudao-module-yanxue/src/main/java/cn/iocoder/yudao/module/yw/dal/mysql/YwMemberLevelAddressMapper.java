package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberLevelAddressDO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberLevelAddressPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

/**
 * 用户收件地址 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwMemberLevelAddressMapper extends BaseMapperX<YwMemberLevelAddressDO> {

    default PageResult<YwMemberLevelAddressDO> selectPage(YwMemberLevelAddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwMemberLevelAddressDO>()
                .eqIfPresent(YwMemberLevelAddressDO::getId, reqVO.getId())
                .eqIfPresent(YwMemberLevelAddressDO::getMemberId, reqVO.getMemberId())
                .likeIfPresent(YwMemberLevelAddressDO::getName, reqVO.getName())
                .eqIfPresent(YwMemberLevelAddressDO::getMobile, reqVO.getMobile())
                .eqIfPresent(YwMemberLevelAddressDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(YwMemberLevelAddressDO::getDetailAddress, reqVO.getDetailAddress())
                .eqIfPresent(YwMemberLevelAddressDO::getDefaultStatus, reqVO.getDefaultStatus())
                .betweenIfPresent(YwMemberLevelAddressDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwMemberLevelAddressDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(YwMemberLevelAddressDO::getStatus, reqVO.getStatus())
                .eqIfPresent(YwMemberLevelAddressDO::getCompany, reqVO.getCompany())
                .eqIfPresent(YwMemberLevelAddressDO::getDeliveryNo, reqVO.getDeliveryNo())
                .orderByDesc(YwMemberLevelAddressDO::getId));
    }

    /** ✅ 按 memberId + levelId 查询唯一地址 */
    default YwMemberLevelAddressDO selectByMemberIdAndLevelId(Long memberId, Long levelId) {
        return selectOne(new LambdaQueryWrapperX<YwMemberLevelAddressDO>()
                .eq(YwMemberLevelAddressDO::getMemberId, memberId)
                .eq(YwMemberLevelAddressDO::getLevelId, levelId)
                .last("limit 1"));
    }

    /** ✅ 仅更新快递公司与单号（按 memberId + levelId） */
    default int updateDeliveryByMemberIdAndLevelId(Long memberId, Long levelId, String company, String deliveryNo) {
        YwMemberLevelAddressDO update = new YwMemberLevelAddressDO();
        update.setCompany(company);
        update.setDeliveryNo(deliveryNo);
        return update(update, new LambdaQueryWrapperX<YwMemberLevelAddressDO>()
                .eq(YwMemberLevelAddressDO::getMemberId, memberId)
                .eq(YwMemberLevelAddressDO::getLevelId, levelId));
    }

}
