package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperSaveReqVO;

/**
 * 试卷 Service 接口
 *
 * @author 芋道源码
 */
public interface YwPaperService {

    /**
     * 创建试卷
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPaper(@Valid YwPaperSaveReqVO createReqVO);

    /**
     * 更新试卷
     *
     * @param updateReqVO 更新信息
     */
    void updatePaper(@Valid YwPaperSaveReqVO updateReqVO);

    /**
     * 删除试卷
     *
     * @param id 编号
     */
    void deletePaper(Long id);

    /**
    * 批量删除试卷
    *
    * @param ids 编号
    */
    void deletePaperListByIds(List<Long> ids);

    /**
     * 获得试卷
     *
     * @param id 编号
     * @return 试卷
     */
    YwPaperDO getPaper(Long id);

    /**
     * 获得试卷分页
     *
     * @param pageReqVO 分页查询
     * @return 试卷分页
     */
    PageResult<YwPaperDO> getPaperPage(YwPaperPageReqVO pageReqVO);

    // ==================== 子表（试卷题目） ====================

    /**
     * 获得试卷题目分页
     *
     * @param pageReqVO 分页查询
     * @param paperId 试卷id
     * @return 试卷题目分页
     */
    PageResult<YwPaperQuDO> getPaperQuPage(PageParam pageReqVO, Long paperId);

    /**
     * 创建试卷题目
     *
     * @param paperQu 创建信息
     * @return 编号
     */
    Long createPaperQu(@Valid YwPaperQuDO paperQu);

    /**
     * 更新试卷题目
     *
     * @param paperQu 更新信息
     */
    void updatePaperQu(@Valid YwPaperQuDO paperQu);

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

    YwPaperEntity beginPaper(Long courseId, Long examId);
    YwExamTotalVo beginPaperSimple(Long examId);
    Boolean submitPaperSimple(YwExamTotalVo exam);


    Boolean submitPaperQu(YwPaperQuEntity entity);

    YwPaperDO submit(Long paperId);

}