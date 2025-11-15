package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberChapterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberChapterPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberChapterSaveReqVO;

/**
 * 章节进度 Service 接口
 *
 * @author 芋道源码
 */
public interface YwMemberChapterService {

    /**
     * 创建章节进度
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMemberChapter(@Valid YwMemberChapterSaveReqVO createReqVO);

    /**
     * 更新章节进度
     *
     * @param updateReqVO 更新信息
     */
    void updateMemberChapter(@Valid YwMemberChapterSaveReqVO updateReqVO);

    /**
     * 删除章节进度
     *
     * @param id 编号
     */
    void deleteMemberChapter(String id);

    /**
    * 批量删除章节进度
    *
    * @param ids 编号
    */
    void deleteMemberChapterListByIds(List<String> ids);

    /**
     * 获得章节进度
     *
     * @param id 编号
     * @return 章节进度
     */
    YwMemberChapterDO getMemberChapter(String id);

    /**
     * 获得章节进度分页
     *
     * @param pageReqVO 分页查询
     * @return 章节进度分页
     */
    PageResult<YwMemberChapterDO> getMemberChapterPage(YwMemberChapterPageReqVO pageReqVO);

}