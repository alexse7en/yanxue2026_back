package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwMemberLevelDO;
import cn.iocoder.yudao.module.yw.ps.vo.YwMemberLevelWithAddressVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwMemberLevelPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户等级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwMemberAuthLevelMapper extends BaseMapperX<YwMemberLevelDO> {

    default PageResult<YwMemberLevelDO> selectPage(YwMemberLevelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwMemberLevelDO>()
                .likeIfPresent(YwMemberLevelDO::getName, reqVO.getName())
                .eqIfPresent(YwMemberLevelDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwMemberLevelDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwMemberLevelDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwMemberLevelDO::getLevelId, reqVO.getLevelId())
                .orderByDesc(YwMemberLevelDO::getId));
    }

    @Select("select * from yw_level a where not exists(select 1 from yw_auth_level b where b.level_id>=a.id and b.member_id=#{memberId} and b.status='10')")
    List<YwMemberLevelDO> selectCanAuthMemberLevel(@Param("memberId") Long memberId);

    @Select("select a.*,b.name as delivery_name, b.mobile, b.area_id, b.detail_address, b.status as delivery_status, b.company, b.delivery_no from yw_member_level a left join yw_member_level_address b on a.member_id=b.member_id and a.level_id=b.level_id where a.member_id=#{memberId} ")
    List<YwMemberLevelWithAddressVo> selectMemberLevel(@Param("memberId") Long memberId);

    @Select("select a.*,b.id as delivery_id,b.name as delivery_name, b.mobile, b.area_id, b.detail_address, b.status as delivery_status, b.company, b.delivery_no from yw_member_level a left join yw_member_level_address b on a.member_id=b.member_id and a.level_id=b.level_id where a.id=#{id} ")
    YwMemberLevelWithAddressVo selectMemberLevelById(@Param("id") Long id);
}