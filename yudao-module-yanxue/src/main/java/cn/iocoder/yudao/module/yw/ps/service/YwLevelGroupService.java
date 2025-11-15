package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评审种类 Service 接口
 *
 * @author 芋道源码
 */
public interface YwLevelGroupService {

    /**
     * 创建评审种类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwLevelGroup(@Valid YwLevelGroupSaveReqVO createReqVO);

    /**
     * 更新评审种类
     *
     * @param updateReqVO 更新信息
     */
    void updateYwLevelGroup(@Valid YwLevelGroupSaveReqVO updateReqVO);

    /**
     * 删除评审种类
     *
     * @param id 编号
     */
    void deleteYwLevelGroup(Long id);

    /**
    * 批量删除评审种类
    *
    * @param ids 编号
    */
    void deleteYwLevelGroupListByIds(List<Long> ids);

    /**
     * 获得评审种类
     *
     * @param id 编号
     * @return 评审种类
     */
    YwLevelGroupDO getYwLevelGroup(Long id);

    /**
     * 获得评审种类分页
     *
     * @param pageReqVO 分页查询
     * @return 评审种类分页
     */
    PageResult<YwLevelGroupDO> getYwLevelGroupPage(YwLevelGroupPageReqVO pageReqVO);

}