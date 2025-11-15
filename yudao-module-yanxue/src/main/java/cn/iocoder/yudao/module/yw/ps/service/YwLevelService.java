package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 志愿者等级 Service 接口
 *
 * @author 芋道源码
 */
public interface YwLevelService {

    /**
     * 创建志愿者等级
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwLevel(@Valid YwLevelSaveReqVO createReqVO);

    /**
     * 更新志愿者等级
     *
     * @param updateReqVO 更新信息
     */
    void updateYwLevel(@Valid YwLevelSaveReqVO updateReqVO);

    /**
     * 删除志愿者等级
     *
     * @param id 编号
     */
    void deleteYwLevel(Long id);

    /**
    * 批量删除志愿者等级
    *
    * @param ids 编号
    */
    void deleteYwLevelListByIds(List<Long> ids);

    /**
     * 获得志愿者等级
     *
     * @param id 编号
     * @return 志愿者等级
     */
    YwLevelDO getYwLevel(Long id);

    /**
     * 获得志愿者等级分页
     *
     * @param pageReqVO 分页查询
     * @return 志愿者等级分页
     */
    PageResult<YwLevelDO> getYwLevelPage(YwLevelPageReqVO pageReqVO);

}