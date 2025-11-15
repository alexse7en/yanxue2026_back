package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwLevelService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwLevelMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 志愿者等级 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwLevelServiceImpl implements YwLevelService {

    @Resource
    private YwLevelMapper ywLevelMapper;

    @Override
    public Long createYwLevel(YwLevelSaveReqVO createReqVO) {
        // 插入
        YwLevelDO ywLevel = BeanUtils.toBean(createReqVO, YwLevelDO.class);
        ywLevelMapper.insert(ywLevel);

        // 返回
        return ywLevel.getId();
    }

    @Override
    public void updateYwLevel(YwLevelSaveReqVO updateReqVO) {
        // 校验存在
        validateYwLevelExists(updateReqVO.getId());
        // 更新
        YwLevelDO updateObj = BeanUtils.toBean(updateReqVO, YwLevelDO.class);
        ywLevelMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwLevel(Long id) {
        // 校验存在
        validateYwLevelExists(id);
        // 删除
        ywLevelMapper.deleteById(id);
    }

    @Override
        public void deleteYwLevelListByIds(List<Long> ids) {
        // 删除
        ywLevelMapper.deleteByIds(ids);
        }


    private void validateYwLevelExists(Long id) {
        if (ywLevelMapper.selectById(id) == null) {
            throw exception(YW_LEVEL_NOT_EXISTS);
        }
    }

    @Override
    public YwLevelDO getYwLevel(Long id) {
        return ywLevelMapper.selectById(id);
    }

    @Override
    public PageResult<YwLevelDO> getYwLevelPage(YwLevelPageReqVO pageReqVO) {
        return ywLevelMapper.selectPage(pageReqVO);
    }

}