package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.page.YwExamPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwExamSaveReqVO;
import cn.iocoder.yudao.module.yw.vo.YwQuTotalVo;

/**
 * 考卷设计 Service 接口
 *
 * @author 芋道源码
 */
public interface YwExamService {

    /**
     * 创建考卷设计
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createExam(@Valid YwExamSaveReqVO createReqVO);

    /**
     * 更新考卷设计
     *
     * @param updateReqVO 更新信息
     */
    void updateExam(@Valid YwExamSaveReqVO updateReqVO);

    /**
     * 删除考卷设计
     *
     * @param id 编号
     */
    void deleteExam(Long id);

    /**
    * 批量删除考卷设计
    *
    * @param ids 编号
    */
    void deleteExamListByIds(List<Long> ids);

    /**
     * 获得考卷设计
     *
     * @param id 编号
     * @return 考卷设计
     */
    YwExamDO getExam(Long id);

    /**
     * 获得考卷设计分页
     *
     * @param pageReqVO 分页查询
     * @return 考卷设计分页
     */
    PageResult<YwExamDO> getExamPage(YwExamPageReqVO pageReqVO);

    // ==================== 子表（试题） ====================

    /**
     * 获得试题分页
     *
     * @param pageReqVO 分页查询
     * @param quType 题目类型
     * @return 试题分页
     */
    PageResult<YwQuDO> getQuPage(PageParam pageReqVO, Long examId);

    List<YwQuDO> getQuList(Long examId);

    /**
     * 创建试题
     *
     * @param qu 创建信息
     * @return 编号
     */
    Long createQu(@Valid YwQuDO qu);

    /**
     * 更新试题
     *
     * @param qu 更新信息
     */
    void updateQu(@Valid YwQuDO qu);

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
    Boolean importExam(List<YwQuTotalVo>  list, YwExamDO examDO);
}