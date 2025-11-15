package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwChapterPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwChapterSaveReqVO;

/**
 * 章节 Service 接口
 *
 * @author 芋道源码
 */
public interface YwChapterService {

    /**
     * 创建章节
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createChapter(@Valid YwChapterSaveReqVO createReqVO);

    /**
     * 更新章节
     *
     * @param updateReqVO 更新信息
     */
    void updateChapter(@Valid YwChapterSaveReqVO updateReqVO);

    /**
     * 删除章节
     *
     * @param id 编号
     */
    void deleteChapter(Long id);

    /**
    * 批量删除章节
    *
    * @param ids 编号
    */
    void deleteChapterListByIds(List<String> ids);

    /**
     * 获得章节
     *
     * @param id 编号
     * @return 章节
     */
    YwChapterDO getChapter(String id);

    /**
     * 获得章节分页
     *
     * @param pageReqVO 分页查询
     * @return 章节分页
     */
    PageResult<YwChapterDO> getChapterPage(YwChapterPageReqVO pageReqVO);

}