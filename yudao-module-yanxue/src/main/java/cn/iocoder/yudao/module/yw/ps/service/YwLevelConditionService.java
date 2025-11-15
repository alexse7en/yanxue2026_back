package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelConditionSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelConditionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评审条件 Service 接口
 *
 * @author 芋道源码
 */
public interface YwLevelConditionService {

    /**
     * 创建评审条件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwLevelCondition(@Valid YwLevelConditionSaveReqVO createReqVO);

    /**
     * 更新评审条件
     *
     * @param updateReqVO 更新信息
     */
    void updateYwLevelCondition(@Valid YwLevelConditionSaveReqVO updateReqVO);

    /**
     * 删除评审条件
     *
     * @param id 编号
     */
    void deleteYwLevelCondition(Long id);

    /**
    * 批量删除评审条件
    *
    * @param ids 编号
    */
    void deleteYwLevelConditionListByIds(List<Long> ids);

    /**
     * 获得评审条件
     *
     * @param id 编号
     * @return 评审条件
     */
    YwLevelConditionDO getYwLevelCondition(Long id);

    /**
     * 获得评审条件分页
     *
     * @param pageReqVO 分页查询
     * @return 评审条件分页
     */
    PageResult<YwLevelConditionDO> getYwLevelConditionPage(YwLevelConditionPageReqVO pageReqVO);

}