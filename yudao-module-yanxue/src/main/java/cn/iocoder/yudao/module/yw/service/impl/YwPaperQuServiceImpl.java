package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwPaperQuService;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperQuPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperQuSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwPaperQuMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwPaperQuOptionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 试卷题目 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwPaperQuServiceImpl implements YwPaperQuService {

    @Resource
    private YwPaperQuMapper paperQuMapper;
    @Resource
    private YwPaperQuOptionMapper paperQuOptionMapper;

    @Override
    public Long createPaperQu(YwPaperQuSaveReqVO createReqVO) {
        // 插入
        YwPaperQuDO paperQu = BeanUtils.toBean(createReqVO, YwPaperQuDO.class);
        paperQuMapper.insert(paperQu);

        // 返回
        return paperQu.getId();
    }

    @Override
    public void updatePaperQu(YwPaperQuSaveReqVO updateReqVO) {
        // 校验存在
        validatePaperQuExists(updateReqVO.getId());
        // 更新
        YwPaperQuDO updateObj = BeanUtils.toBean(updateReqVO, YwPaperQuDO.class);
        paperQuMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePaperQu(Long id) {
        // 校验存在
        validatePaperQuExists(id);
        // 删除
        paperQuMapper.deleteById(id);

        // 删除子表
        deletePaperQuOptionByQuId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deletePaperQuListByIds(List<Long> ids) {
        // 删除
        paperQuMapper.deleteByIds(ids);
    
    // 删除子表
            deletePaperQuOptionByQuIds(ids);
    }


    private void validatePaperQuExists(Long id) {
        if (paperQuMapper.selectById(id) == null) {
            throw exception(PAPER_QU_NOT_EXISTS);
        }
    }

    @Override
    public YwPaperQuDO getPaperQu(Long id) {
        return paperQuMapper.selectById(id);
    }

    @Override
    public PageResult<YwPaperQuDO> getPaperQuPage(YwPaperQuPageReqVO pageReqVO) {
        return paperQuMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（试卷选项） ====================

    @Override
    public PageResult<YwPaperQuOptionDO> getPaperQuOptionPage(PageParam pageReqVO, Long quId) {
        return paperQuOptionMapper.selectPage(pageReqVO, quId);
    }

    @Override
    public Long createPaperQuOption(YwPaperQuOptionDO paperQuOption) {
        paperQuOption.clean(); // 清理掉创建、更新时间等相关属性值
        paperQuOptionMapper.insert(paperQuOption);
        return paperQuOption.getId();
    }

    @Override
    public void updatePaperQuOption(YwPaperQuOptionDO paperQuOption) {
        // 校验存在
        validatePaperQuOptionExists(paperQuOption.getId());
        // 更新
        paperQuOption.clean(); // 解决更新情况下：updateTime 不更新
        paperQuOptionMapper.updateById(paperQuOption);
    }

    @Override
    public void deletePaperQuOption(Long id) {
        // 删除
        paperQuOptionMapper.deleteById(id);
    }

	@Override
	public void deletePaperQuOptionListByIds(List<Long> ids) {
        // 删除
        paperQuOptionMapper.deleteByIds(ids);
	}

    @Override
    public YwPaperQuOptionDO getPaperQuOption(Long id) {
        return paperQuOptionMapper.selectById(id);
    }

    private void validatePaperQuOptionExists(Long id) {
        if (paperQuOptionMapper.selectById(id) == null) {
            throw exception(PAPER_QU_OPTION_NOT_EXISTS);
        }
    }

    private void deletePaperQuOptionByQuId(Long quId) {
        paperQuOptionMapper.deleteByQuId(quId);
    }

	private void deletePaperQuOptionByQuIds(List<Long> quIds) {
        paperQuOptionMapper.deleteByQuIds(quIds);
	}

}