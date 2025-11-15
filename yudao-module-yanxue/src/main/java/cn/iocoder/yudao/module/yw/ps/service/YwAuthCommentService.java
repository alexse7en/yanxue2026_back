package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentSaveReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评审结果 Service 接口
 *
 * @author 芋道源码
 */
public interface YwAuthCommentService {

    /**
     * 创建评审结果
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwAuthComment(@Valid YwAuthCommentSaveReqVO createReqVO);

    /**
     * 更新评审结果
     *
     * @param updateReqVO 更新信息
     */
    void updateYwAuthComment(@Valid YwAuthCommentSaveReqVO updateReqVO);

    /**
     * 删除评审结果
     *
     * @param id 编号
     */
    void deleteYwAuthComment(Long id);

    /**
    * 批量删除评审结果
    *
    * @param ids 编号
    */
    void deleteYwAuthCommentListByIds(List<Long> ids);

    /**
     * 获得评审结果
     *
     * @param id 编号
     * @return 评审结果
     */
    YwAuthCommentDO getYwAuthComment(Long id);

    /**
     * 获得评审结果分页
     *
     * @param pageReqVO 分页查询
     * @return 评审结果分页
     */
    PageResult<YwAuthCommentDO> getYwAuthCommentPage(YwAuthCommentPageReqVO pageReqVO);

}