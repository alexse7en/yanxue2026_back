package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwPaperQuOptionService;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuOptionPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperQuOptionSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwPaperQuOptionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 试卷选项 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwPaperQuOptionServiceImpl implements YwPaperQuOptionService {

    @Resource
    private YwPaperQuOptionMapper paperQuOptionMapper;

    @Override
    public Long createPaperQuOption(YwPaperQuOptionSaveReqVO createReqVO) {
        // 插入
        YwPaperQuOptionDO paperQuOption = BeanUtils.toBean(createReqVO, YwPaperQuOptionDO.class);
        paperQuOptionMapper.insert(paperQuOption);

        // 返回
        return paperQuOption.getId();
    }

    @Override
    public void updatePaperQuOption(YwPaperQuOptionSaveReqVO updateReqVO) {
        // 校验存在
        validatePaperQuOptionExists(updateReqVO.getId());
        // 更新
        YwPaperQuOptionDO updateObj = BeanUtils.toBean(updateReqVO, YwPaperQuOptionDO.class);
        paperQuOptionMapper.updateById(updateObj);
    }

    @Override
    public void deletePaperQuOption(Long id) {
        // 校验存在
        validatePaperQuOptionExists(id);
        // 删除
        paperQuOptionMapper.deleteById(id);
    }

    @Override
        public void deletePaperQuOptionListByIds(List<Long> ids) {
        // 删除
        paperQuOptionMapper.deleteByIds(ids);
        }


    private void validatePaperQuOptionExists(Long id) {
        if (paperQuOptionMapper.selectById(id) == null) {
            throw exception(PAPER_QU_OPTION_NOT_EXISTS);
        }
    }

    @Override
    public YwPaperQuOptionDO getPaperQuOption(Long id) {
        return paperQuOptionMapper.selectById(id);
    }

    @Override
    public PageResult<YwPaperQuOptionDO> getPaperQuOptionPage(YwPaperQuOptionPageReqVO pageReqVO) {
        return paperQuOptionMapper.selectPage(pageReqVO);
    }

}