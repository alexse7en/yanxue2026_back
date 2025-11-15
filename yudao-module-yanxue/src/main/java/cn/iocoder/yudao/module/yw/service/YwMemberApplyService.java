package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.YwMemberApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.YwMemberApplyBatchAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberApplySaveReqVO;

/**
 * 组织-志愿者挂靠审核 Service 接口
 *
 * @author 芋道源码
 */
public interface YwMemberApplyService {

    /**
     * 创建组织-志愿者挂靠审核
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwMemberApply(@Valid YwMemberApplySaveReqVO createReqVO);

    /**
     * 更新组织-志愿者挂靠审核
     *
     * @param updateReqVO 更新信息
     */
    void updateYwMemberApply(@Valid YwMemberApplySaveReqVO updateReqVO);

    /**
     * 删除组织-志愿者挂靠审核
     *
     * @param id 编号
     */
    void deleteYwMemberApply(Long id);

    /**
    * 批量删除组织-志愿者挂靠审核
    *
    * @param ids 编号
    */
    void deleteYwMemberApplyListByIds(List<Long> ids);

    /**
     * 获得组织-志愿者挂靠审核
     *
     * @param id 编号
     * @return 组织-志愿者挂靠审核
     */
    cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO getYwMemberApply(Long id);

    /**
     * 获得组织-志愿者挂靠审核分页
     *
     * @param pageReqVO 分页查询
     * @return 组织-志愿者挂靠审核分页
     */
    PageResult<YwMemberApplyRespVO> getYwMemberApplyPage(YwMemberApplyPageReqVO pageReqVO);

    void auditYwMemberApply(YwMemberApplyAuditReqVO reqVO);

    void batchAuditYwMemberApply(YwMemberApplyBatchAuditReqVO reqVO);

}
