package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwLevelGroupNormService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupNormPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupNormSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupNormDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwLevelGroupNormMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 评审细则 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwLevelGroupNormServiceImpl implements YwLevelGroupNormService {

    @Resource
    private YwLevelGroupNormMapper ywLevelGroupNormMapper;

    @Override
    public Long createYwLevelGroupNorm(YwLevelGroupNormSaveReqVO createReqVO) {
        // 插入
        YwLevelGroupNormDO ywLevelGroupNorm = BeanUtils.toBean(createReqVO, YwLevelGroupNormDO.class);
        ywLevelGroupNormMapper.insert(ywLevelGroupNorm);

        // 返回
        return ywLevelGroupNorm.getId();
    }

    @Override
    public void updateYwLevelGroupNorm(YwLevelGroupNormSaveReqVO updateReqVO) {
        // 校验存在
        validateYwLevelGroupNormExists(updateReqVO.getId());
        // 更新
        YwLevelGroupNormDO updateObj = BeanUtils.toBean(updateReqVO, YwLevelGroupNormDO.class);
        ywLevelGroupNormMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwLevelGroupNorm(Long id) {
        // 校验存在
        validateYwLevelGroupNormExists(id);
        // 删除
        ywLevelGroupNormMapper.deleteById(id);
    }

    @Override
        public void deleteYwLevelGroupNormListByIds(List<Long> ids) {
        // 删除
        ywLevelGroupNormMapper.deleteByIds(ids);
        }


    private void validateYwLevelGroupNormExists(Long id) {
        if (ywLevelGroupNormMapper.selectById(id) == null) {
            throw exception(YW_LEVEL_GROUP_NORM_NOT_EXISTS);
        }
    }

    @Override
    public YwLevelGroupNormDO getYwLevelGroupNorm(Long id) {
        return ywLevelGroupNormMapper.selectById(id);
    }

    @Override
    public PageResult<YwLevelGroupNormDO> getYwLevelGroupNormPage(YwLevelGroupNormPageReqVO pageReqVO) {
        return ywLevelGroupNormMapper.selectPage(pageReqVO);
    }

}