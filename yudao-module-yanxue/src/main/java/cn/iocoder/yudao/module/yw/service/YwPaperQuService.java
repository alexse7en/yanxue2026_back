package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperQuSaveReqVO;

/**
 * 试卷题目 Service 接口
 *
 * @author 芋道源码
 */
public interface YwPaperQuService {

    /**
     * 创建试卷题目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPaperQu(@Valid YwPaperQuSaveReqVO createReqVO);

    /**
     * 更新试卷题目
     *
     * @param updateReqVO 更新信息
     */
    void updatePaperQu(@Valid YwPaperQuSaveReqVO updateReqVO);

    /**
     * 删除试卷题目
     *
     * @param id 编号
     */
    void deletePaperQu(Long id);

    /**
    * 批量删除试卷题目
    *
    * @param ids 编号
    */
    void deletePaperQuListByIds(List<Long> ids);

    /**
     * 获得试卷题目
     *
     * @param id 编号
     * @return 试卷题目
     */
    YwPaperQuDO getPaperQu(Long id);

    /**
     * 获得试卷题目分页
     *
     * @param pageReqVO 分页查询
     * @return 试卷题目分页
     */
    PageResult<YwPaperQuDO> getPaperQuPage(YwPaperQuPageReqVO pageReqVO);

    // ==================== 子表（试卷选项） ====================

    /**
     * 获得试卷选项分页
     *
     * @param pageReqVO 分页查询
     * @param quId 考题id
     * @return 试卷选项分页
     */
    PageResult<YwPaperQuOptionDO> getPaperQuOptionPage(PageParam pageReqVO, Long quId);

    /**
     * 创建试卷选项
     *
     * @param paperQuOption 创建信息
     * @return 编号
     */
    Long createPaperQuOption(@Valid YwPaperQuOptionDO paperQuOption);

    /**
     * 更新试卷选项
     *
     * @param paperQuOption 更新信息
     */
    void updatePaperQuOption(@Valid YwPaperQuOptionDO paperQuOption);

    /**
     * 删除试卷选项
     *
     * @param id 编号
     */
    void deletePaperQuOption(Long id);

    /**
    * 批量删除试卷选项
    *
    * @param ids 编号
    */
    void deletePaperQuOptionListByIds(List<Long> ids);

	/**
	 * 获得试卷选项
	 *
	 * @param id 编号
     * @return 试卷选项
	 */
    YwPaperQuOptionDO getPaperQuOption(Long id);

}