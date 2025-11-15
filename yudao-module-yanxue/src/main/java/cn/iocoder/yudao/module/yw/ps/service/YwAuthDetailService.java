package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthDetailSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 志愿者等级 Service 接口
 *
 * @author 芋道源码
 */
public interface YwAuthDetailService {

    /**
     * 创建志愿者等级
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwAuthDetail(@Valid YwAuthDetailSaveReqVO createReqVO);

    /**
     * 更新志愿者等级
     *
     * @param updateReqVO 更新信息
     */
    void updateYwAuthDetail(@Valid YwAuthDetailSaveReqVO updateReqVO);

    /**
     * 删除志愿者等级
     *
     * @param id 编号
     */
    void deleteYwAuthDetail(Long id);

    /**
    * 批量删除志愿者等级
    *
    * @param ids 编号
    */
    void deleteYwAuthDetailListByIds(List<Long> ids);

    /**
     * 获得志愿者等级
     *
     * @param id 编号
     * @return 志愿者等级
     */
    YwAuthDetailDO getYwAuthDetail(Long id);

    /**
     * 获得志愿者等级分页
     *
     * @param pageReqVO 分页查询
     * @return 志愿者等级分页
     */
    PageResult<YwAuthDetailDO> getYwAuthDetailPage(YwAuthDetailPageReqVO pageReqVO);

}