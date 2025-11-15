package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwQuOptionService;
import cn.iocoder.yudao.module.yw.vo.page.YwQuOptionPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwQuOptionSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwQuOptionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 试题选项 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwQuOptionServiceImpl implements YwQuOptionService {

    @Resource
    private YwQuOptionMapper quOptionMapper;

    @Override
    public Long createQuOption(YwQuOptionSaveReqVO createReqVO) {
        // 插入
        YwQuOptionDO quOption = BeanUtils.toBean(createReqVO, YwQuOptionDO.class);
        quOptionMapper.insert(quOption);

        // 返回
        return quOption.getId();
    }

    @Override
    public void updateQuOption(YwQuOptionSaveReqVO updateReqVO) {
        // 校验存在
        validateQuOptionExists(updateReqVO.getId());
        // 更新
        YwQuOptionDO updateObj = BeanUtils.toBean(updateReqVO, YwQuOptionDO.class);
        quOptionMapper.updateById(updateObj);
    }

    @Override
    public void deleteQuOption(Long id) {
        // 校验存在
        validateQuOptionExists(id);
        // 删除
        quOptionMapper.deleteById(id);
    }

    @Override
        public void deleteQuOptionListByIds(List<Long> ids) {
        // 删除
        quOptionMapper.deleteByIds(ids);
        }


    private void validateQuOptionExists(Long id) {
        if (quOptionMapper.selectById(id) == null) {
            throw exception(QU_OPTION_NOT_EXISTS);
        }
    }

    @Override
    public YwQuOptionDO getQuOption(Long id) {
        return quOptionMapper.selectById(id);
    }

    @Override
    public PageResult<YwQuOptionDO> getQuOptionPage(YwQuOptionPageReqVO pageReqVO) {
        return quOptionMapper.selectPage(pageReqVO);
    }

}