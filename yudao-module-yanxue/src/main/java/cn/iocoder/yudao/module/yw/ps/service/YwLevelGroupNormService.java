package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupNormPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupNormSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupNormDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评审细则 Service 接口
 *
 * @author 芋道源码
 */
public interface YwLevelGroupNormService {

    /**
     * 创建评审细则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwLevelGroupNorm(@Valid YwLevelGroupNormSaveReqVO createReqVO);

    /**
     * 更新评审细则
     *
     * @param updateReqVO 更新信息
     */
    void updateYwLevelGroupNorm(@Valid YwLevelGroupNormSaveReqVO updateReqVO);

    /**
     * 删除评审细则
     *
     * @param id 编号
     */
    void deleteYwLevelGroupNorm(Long id);

    /**
    * 批量删除评审细则
    *
    * @param ids 编号
    */
    void deleteYwLevelGroupNormListByIds(List<Long> ids);

    /**
     * 获得评审细则
     *
     * @param id 编号
     * @return 评审细则
     */
    YwLevelGroupNormDO getYwLevelGroupNorm(Long id);

    /**
     * 获得评审细则分页
     *
     * @param pageReqVO 分页查询
     * @return 评审细则分页
     */
    PageResult<YwLevelGroupNormDO> getYwLevelGroupNormPage(YwLevelGroupNormPageReqVO pageReqVO);

}