package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwQuOptionPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwQuOptionSaveReqVO;

/**
 * 试题选项 Service 接口
 *
 * @author 芋道源码
 */
public interface YwQuOptionService {

    /**
     * 创建试题选项
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQuOption(@Valid YwQuOptionSaveReqVO createReqVO);

    /**
     * 更新试题选项
     *
     * @param updateReqVO 更新信息
     */
    void updateQuOption(@Valid YwQuOptionSaveReqVO updateReqVO);

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

    /**
     * 获得试题选项分页
     *
     * @param pageReqVO 分页查询
     * @return 试题选项分页
     */
    PageResult<YwQuOptionDO> getQuOptionPage(YwQuOptionPageReqVO pageReqVO);

}