package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwLevelGroupService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwLevelGroupMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 评审种类 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwLevelGroupServiceImpl implements YwLevelGroupService {

    @Resource
    private YwLevelGroupMapper ywLevelGroupMapper;

    @Override
    public Long createYwLevelGroup(YwLevelGroupSaveReqVO createReqVO) {
        // 插入
        YwLevelGroupDO ywLevelGroup = BeanUtils.toBean(createReqVO, YwLevelGroupDO.class);
        ywLevelGroupMapper.insert(ywLevelGroup);

        // 返回
        return ywLevelGroup.getId();
    }

    @Override
    public void updateYwLevelGroup(YwLevelGroupSaveReqVO updateReqVO) {
        // 校验存在
        validateYwLevelGroupExists(updateReqVO.getId());
        // 更新
        YwLevelGroupDO updateObj = BeanUtils.toBean(updateReqVO, YwLevelGroupDO.class);
        ywLevelGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwLevelGroup(Long id) {
        // 校验存在
        validateYwLevelGroupExists(id);
        // 删除
        ywLevelGroupMapper.deleteById(id);
    }

    @Override
        public void deleteYwLevelGroupListByIds(List<Long> ids) {
        // 删除
        ywLevelGroupMapper.deleteByIds(ids);
        }


    private void validateYwLevelGroupExists(Long id) {
        if (ywLevelGroupMapper.selectById(id) == null) {
            throw exception(YW_LEVEL_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public YwLevelGroupDO getYwLevelGroup(Long id) {
        return ywLevelGroupMapper.selectById(id);
    }

    @Override
    public PageResult<YwLevelGroupDO> getYwLevelGroupPage(YwLevelGroupPageReqVO pageReqVO) {
        return ywLevelGroupMapper.selectPage(pageReqVO);
    }

}