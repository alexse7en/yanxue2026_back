package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwMemberChapterService;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberChapterPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberChapterSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberChapterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwMemberChapterMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 章节进度 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwMemberChapterServiceImpl implements YwMemberChapterService {

    @Resource
    private YwMemberChapterMapper memberChapterMapper;

    @Override
    public String createMemberChapter(YwMemberChapterSaveReqVO createReqVO) {
        // 插入
        YwMemberChapterDO memberChapter = BeanUtils.toBean(createReqVO, YwMemberChapterDO.class);
        memberChapterMapper.insert(memberChapter);

        // 返回
        return memberChapter.getId();
    }

    @Override
    public void updateMemberChapter(YwMemberChapterSaveReqVO updateReqVO) {
        // 校验存在
        validateMemberChapterExists(updateReqVO.getId());
        // 更新
        YwMemberChapterDO updateObj = BeanUtils.toBean(updateReqVO, YwMemberChapterDO.class);
        memberChapterMapper.updateById(updateObj);
    }

    @Override
    public void deleteMemberChapter(String id) {
        // 校验存在
        validateMemberChapterExists(id);
        // 删除
        memberChapterMapper.deleteById(id);
    }

    @Override
        public void deleteMemberChapterListByIds(List<String> ids) {
        // 删除
        memberChapterMapper.deleteByIds(ids);
        }


    private void validateMemberChapterExists(String id) {
        if (memberChapterMapper.selectById(id) == null) {
            throw exception(MEMBER_CHAPTER_NOT_EXISTS);
        }
    }

    @Override
    public YwMemberChapterDO getMemberChapter(String id) {
        return memberChapterMapper.selectById(id);
    }

    @Override
    public PageResult<YwMemberChapterDO> getMemberChapterPage(YwMemberChapterPageReqVO pageReqVO) {
        return memberChapterMapper.selectPage(pageReqVO);
    }

}