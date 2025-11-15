package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwAuthCommentService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthCommentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_AUTH_COMMENT_NOT_EXISTS;

/**
 * 评审结果 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwAuthCommentServiceImpl implements YwAuthCommentService {

    @Resource
    private YwAuthCommentMapper ywAuthCommentMapper;

    @Override
    public Long createYwAuthComment(YwAuthCommentSaveReqVO createReqVO) {
        // 插入
        YwAuthCommentDO ywAuthComment = BeanUtils.toBean(createReqVO, YwAuthCommentDO.class);
        ywAuthCommentMapper.insert(ywAuthComment);

        // 返回
        return ywAuthComment.getId();
    }

    @Override
    public void updateYwAuthComment(YwAuthCommentSaveReqVO updateReqVO) {
        // 校验存在
        validateYwAuthCommentExists(updateReqVO.getId());
        // 更新
        YwAuthCommentDO updateObj = BeanUtils.toBean(updateReqVO, YwAuthCommentDO.class);
        ywAuthCommentMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwAuthComment(Long id) {
        // 校验存在
        validateYwAuthCommentExists(id);
        // 删除
        ywAuthCommentMapper.deleteById(id);
    }

    @Override
        public void deleteYwAuthCommentListByIds(List<Long> ids) {
        // 删除
        ywAuthCommentMapper.deleteByIds(ids);
        }


    private void validateYwAuthCommentExists(Long id) {
        if (ywAuthCommentMapper.selectById(id) == null) {
            throw exception(YW_AUTH_COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public YwAuthCommentDO getYwAuthComment(Long id) {
        return ywAuthCommentMapper.selectById(id);
    }

    @Override
    public PageResult<YwAuthCommentDO> getYwAuthCommentPage(YwAuthCommentPageReqVO pageReqVO) {
        return ywAuthCommentMapper.selectPage(pageReqVO);
    }

}