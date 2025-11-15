package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuOptionPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperQuOptionSaveReqVO;

/**
 * 试卷选项 Service 接口
 *
 * @author 芋道源码
 */
public interface YwPaperQuOptionService {

    /**
     * 创建试卷选项
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPaperQuOption(@Valid YwPaperQuOptionSaveReqVO createReqVO);

    /**
     * 更新试卷选项
     *
     * @param updateReqVO 更新信息
     */
    void updatePaperQuOption(@Valid YwPaperQuOptionSaveReqVO updateReqVO);

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

    /**
     * 获得试卷选项分页
     *
     * @param pageReqVO 分页查询
     * @return 试卷选项分页
     */
    PageResult<YwPaperQuOptionDO> getPaperQuOptionPage(YwPaperQuOptionPageReqVO pageReqVO);

}