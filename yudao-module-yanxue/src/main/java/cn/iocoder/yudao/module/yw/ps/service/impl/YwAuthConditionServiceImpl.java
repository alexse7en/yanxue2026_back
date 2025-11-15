package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwAuthConditionService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthConditionSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthConditionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthConditionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_AUTH_CONDITION_NOT_EXISTS;

/**
 * 志愿者评审条件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwAuthConditionServiceImpl implements YwAuthConditionService {

    @Resource
    private YwAuthConditionMapper ywAuthConditionMapper;

    @Override
    public Long createYwAuthCondition(YwAuthConditionSaveReqVO createReqVO) {
        // 插入
        YwAuthConditionDO ywAuthCondition = BeanUtils.toBean(createReqVO, YwAuthConditionDO.class);
        ywAuthConditionMapper.insert(ywAuthCondition);

        // 返回
        return ywAuthCondition.getId();
    }

    @Override
    public void updateYwAuthCondition(YwAuthConditionSaveReqVO updateReqVO) {
        // 校验存在
        validateYwAuthConditionExists(updateReqVO.getId());
        // 更新
        YwAuthConditionDO updateObj = BeanUtils.toBean(updateReqVO, YwAuthConditionDO.class);
        ywAuthConditionMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwAuthCondition(Long id) {
        // 校验存在
        validateYwAuthConditionExists(id);
        // 删除
        ywAuthConditionMapper.deleteById(id);
    }

    @Override
        public void deleteYwAuthConditionListByIds(List<Long> ids) {
        // 删除
        ywAuthConditionMapper.deleteByIds(ids);
        }


    private void validateYwAuthConditionExists(Long id) {
        if (ywAuthConditionMapper.selectById(id) == null) {
            throw exception(YW_AUTH_CONDITION_NOT_EXISTS);
        }
    }

    @Override
    public YwAuthConditionDO getYwAuthCondition(Long id) {
        return ywAuthConditionMapper.selectById(id);
    }

    @Override
    public PageResult<YwAuthConditionDO> getYwAuthConditionPage(YwAuthConditionPageReqVO pageReqVO) {
        return ywAuthConditionMapper.selectPage(pageReqVO);
    }

}