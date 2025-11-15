package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupDetailSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评分项 Service 接口
 *
 * @author 芋道源码
 */
public interface YwLevelGroupDetailService {

    /**
     * 创建评分项
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwLevelGroupDetail(@Valid YwLevelGroupDetailSaveReqVO createReqVO);

    /**
     * 更新评分项
     *
     * @param updateReqVO 更新信息
     */
    void updateYwLevelGroupDetail(@Valid YwLevelGroupDetailSaveReqVO updateReqVO);

    /**
     * 删除评分项
     *
     * @param id 编号
     */
    void deleteYwLevelGroupDetail(Long id);

    /**
    * 批量删除评分项
    *
    * @param ids 编号
    */
    void deleteYwLevelGroupDetailListByIds(List<Long> ids);

    /**
     * 获得评分项
     *
     * @param id 编号
     * @return 评分项
     */
    YwLevelGroupDetailDO getYwLevelGroupDetail(Long id);

    /**
     * 获得评分项分页
     *
     * @param pageReqVO 分页查询
     * @return 评分项分页
     */
    PageResult<YwLevelGroupDetailDO> getYwLevelGroupDetailPage(YwLevelGroupDetailPageReqVO pageReqVO);

}