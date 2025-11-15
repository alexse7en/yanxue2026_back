package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwChapterService;
import cn.iocoder.yudao.module.yw.vo.page.YwChapterPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwChapterSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwChapterMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 章节 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwChapterServiceImpl implements YwChapterService {

    @Resource
    private YwChapterMapper chapterMapper;

    @Override
    public Long createChapter(YwChapterSaveReqVO createReqVO) {
        // 插入
        YwChapterDO chapter = BeanUtils.toBean(createReqVO, YwChapterDO.class);
        chapterMapper.insert(chapter);

        // 返回
        return chapter.getId();
    }

    @Override
    public void updateChapter(YwChapterSaveReqVO updateReqVO) {
        // 校验存在
        validateChapterExists(updateReqVO.getId());
        // 更新
        YwChapterDO updateObj = BeanUtils.toBean(updateReqVO, YwChapterDO.class);
        chapterMapper.updateById(updateObj);
    }

    @Override
    public void deleteChapter(Long id) {
        // 校验存在
        validateChapterExists(id);
        // 删除
        chapterMapper.deleteById(id);
    }

    @Override
        public void deleteChapterListByIds(List<String> ids) {
        // 删除
        chapterMapper.deleteByIds(ids);
        }


    private void validateChapterExists(Long id) {
        if (chapterMapper.selectById(id) == null) {
            throw exception(CHAPTER_NOT_EXISTS);
        }
    }

    @Override
    public YwChapterDO getChapter(String id) {
        return chapterMapper.selectById(id);
    }

    @Override
    public PageResult<YwChapterDO> getChapterPage(YwChapterPageReqVO pageReqVO) {
        return chapterMapper.selectPage(pageReqVO);
    }

}