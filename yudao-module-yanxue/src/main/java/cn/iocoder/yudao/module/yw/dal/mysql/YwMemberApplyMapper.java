package cn.iocoder.yudao.module.yw.dal.mysql;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberApplyRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 组织-志愿者挂靠审核 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwMemberApplyMapper extends BaseMapperX<YwMemberApplyDO> {

    default PageResult<YwMemberApplyDO> selectPage(YwMemberApplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwMemberApplyDO>()
                .eqIfPresent(YwMemberApplyDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(YwMemberApplyDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwMemberApplyDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(YwMemberApplyDO::getApplyTime, reqVO.getApplyTime())
                .betweenIfPresent(YwMemberApplyDO::getAuditTime, reqVO.getAuditTime())
                .eqIfPresent(YwMemberApplyDO::getAuditUser, reqVO.getAuditUser())
                .eqIfPresent(YwMemberApplyDO::getAuditReason, reqVO.getAuditReason())
                .orderByDesc(YwMemberApplyDO::getId));
    }

    List<YwMemberApplyRespVO> selectPageWithMemberInfo(YwMemberApplyPageReqVO pageReqVO);

    @Select("SELECT COUNT(1) FROM yw_member_apply WHERE status = #{status} AND (audit_user IS NULL OR audit_user = 0)")
    Long selectCountByStatusAndAuditUserIsNull(@Param("status") Integer status);

}
