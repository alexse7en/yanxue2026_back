package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentStatusPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentStatusSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentStatusDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 专家评审状态 Service 接口
 *
 * @author 芋道源码
 */
public interface YwAuthCommentStatusService {

    /**
     * 创建专家评审状态
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwAuthCommentStatus(@Valid YwAuthCommentStatusSaveReqVO createReqVO);

    /**
     * 更新专家评审状态
     *
     * @param updateReqVO 更新信息
     */
    void updateYwAuthCommentStatus(@Valid YwAuthCommentStatusSaveReqVO updateReqVO);

    /**
     * 删除专家评审状态
     *
     * @param id 编号
     */
    void deleteYwAuthCommentStatus(Long id);

    /**
    * 批量删除专家评审状态
    *
    * @param ids 编号
    */
    void deleteYwAuthCommentStatusListByIds(List<Long> ids);

    /**
     * 获得专家评审状态
     *
     * @param id 编号
     * @return 专家评审状态
     */
    YwAuthCommentStatusDO getYwAuthCommentStatus(Long id);

    /**
     * 获得专家评审状态分页
     *
     * @param pageReqVO 分页查询
     * @return 专家评审状态分页
     */
    PageResult<YwAuthCommentStatusDO> getYwAuthCommentStatusPage(YwAuthCommentStatusPageReqVO pageReqVO);
    int updateTeacherCommentStatus(Long authId, Long teacherId);
    boolean getAllTeacherCommentStatus(Long authId, String teacherIds);

    String getTeacherStatusByMemberId(Long authId,Long teacherId );

}