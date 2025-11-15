package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthConditionSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthConditionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 志愿者评审条件 Service 接口
 *
 * @author 芋道源码
 */
public interface YwAuthConditionService {

    /**
     * 创建志愿者评审条件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwAuthCondition(@Valid YwAuthConditionSaveReqVO createReqVO);

    /**
     * 更新志愿者评审条件
     *
     * @param updateReqVO 更新信息
     */
    void updateYwAuthCondition(@Valid YwAuthConditionSaveReqVO updateReqVO);

    /**
     * 删除志愿者评审条件
     *
     * @param id 编号
     */
    void deleteYwAuthCondition(Long id);

    /**
    * 批量删除志愿者评审条件
    *
    * @param ids 编号
    */
    void deleteYwAuthConditionListByIds(List<Long> ids);

    /**
     * 获得志愿者评审条件
     *
     * @param id 编号
     * @return 志愿者评审条件
     */
    YwAuthConditionDO getYwAuthCondition(Long id);

    /**
     * 获得志愿者评审条件分页
     *
     * @param pageReqVO 分页查询
     * @return 志愿者评审条件分页
     */
    PageResult<YwAuthConditionDO> getYwAuthConditionPage(YwAuthConditionPageReqVO pageReqVO);

}