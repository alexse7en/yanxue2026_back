package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.page.YwQuPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwQuSaveReqVO;

/**
 * 试题 Service 接口
 *
 * @author 芋道源码
 */
public interface YwQuService {

    /**
     * 创建试题
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQu(@Valid YwQuSaveReqVO createReqVO);

    /**
     * 更新试题
     *
     * @param updateReqVO 更新信息
     */
    void updateQu(@Valid YwQuSaveReqVO updateReqVO);

    /**
     * 删除试题
     *
     * @param id 编号
     */
    void deleteQu(Long id);

    /**
    * 批量删除试题
    *
    * @param ids 编号
    */
    void deleteQuListByIds(List<Long> ids);

    /**
     * 获得试题
     *
     * @param id 编号
     * @return 试题
     */
    YwQuDO getQu(Long id);

    /**
     * 获得试题分页
     *
     * @param pageReqVO 分页查询
     * @return 试题分页
     */
    PageResult<YwQuDO> getQuPage(YwQuPageReqVO pageReqVO);

    // ==================== 子表（试题选项） ====================

    /**
     * 获得试题选项分页
     *
     * @param pageReqVO 分页查询
     * @param quId 试题id
     * @return 试题选项分页
     */
    PageResult<YwQuOptionDO> getQuOptionPage(PageParam pageReqVO, Long quId);

    /**
     * 创建试题选项
     *
     * @param quOption 创建信息
     * @return 编号
     */
    Long createQuOption(@Valid YwQuOptionDO quOption);

    /**
     * 更新试题选项
     *
     * @param quOption 更新信息
     */
    void updateQuOption(@Valid YwQuOptionDO quOption);

    /**
     * 删除试题选项
     *
     * @param id 编号
     */
    void deleteQuOption(Long id);

    /**
    * 批量删除试题选项
    *
    * @param ids 编号
    */
    void deleteQuOptionListByIds(List<Long> ids);

	/**
	 * 获得试题选项
	 *
	 * @param id 编号
     * @return 试题选项
	 */
    YwQuOptionDO getQuOption(Long id);

}