package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwLevelConditionService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelConditionPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelConditionSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelConditionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwLevelConditionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 评审条件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwLevelConditionServiceImpl implements YwLevelConditionService {

    @Resource
    private YwLevelConditionMapper ywLevelConditionMapper;

    @Override
    public Long createYwLevelCondition(YwLevelConditionSaveReqVO createReqVO) {
        // 插入
        YwLevelConditionDO ywLevelCondition = BeanUtils.toBean(createReqVO, YwLevelConditionDO.class);
        ywLevelConditionMapper.insert(ywLevelCondition);

        // 返回
        return ywLevelCondition.getId();
    }

    @Override
    public void updateYwLevelCondition(YwLevelConditionSaveReqVO updateReqVO) {
        // 校验存在
        validateYwLevelConditionExists(updateReqVO.getId());
        // 更新
        YwLevelConditionDO updateObj = BeanUtils.toBean(updateReqVO, YwLevelConditionDO.class);
        ywLevelConditionMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwLevelCondition(Long id) {
        // 校验存在
        validateYwLevelConditionExists(id);
        // 删除
        ywLevelConditionMapper.deleteById(id);
    }

    @Override
        public void deleteYwLevelConditionListByIds(List<Long> ids) {
        // 删除
        ywLevelConditionMapper.deleteByIds(ids);
        }


    private void validateYwLevelConditionExists(Long id) {
        if (ywLevelConditionMapper.selectById(id) == null) {
            throw exception(YW_LEVEL_CONDITION_NOT_EXISTS);
        }
    }

    @Override
    public YwLevelConditionDO getYwLevelCondition(Long id) {
        return ywLevelConditionMapper.selectById(id);
    }

    @Override
    public PageResult<YwLevelConditionDO> getYwLevelConditionPage(YwLevelConditionPageReqVO pageReqVO) {
        return ywLevelConditionMapper.selectPage(pageReqVO);
    }

}