package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwQuService;
import cn.iocoder.yudao.module.yw.vo.page.YwQuPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwQuSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwQuMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwQuOptionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 试题 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwQuServiceImpl implements YwQuService {

    @Resource
    private YwQuMapper quMapper;
    @Resource
    private YwQuOptionMapper quOptionMapper;

    @Override
    public Long createQu(YwQuSaveReqVO createReqVO) {
        // 插入
        YwQuDO qu = BeanUtils.toBean(createReqVO, YwQuDO.class);
        quMapper.insert(qu);

        // 返回
        return qu.getId();
    }

    @Override
    public void updateQu(YwQuSaveReqVO updateReqVO) {
        // 校验存在
        validateQuExists(updateReqVO.getId());
        // 更新
        YwQuDO updateObj = BeanUtils.toBean(updateReqVO, YwQuDO.class);
        quMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQu(Long id) {
        // 校验存在
        validateQuExists(id);
        // 删除
        quMapper.deleteById(id);

        // 删除子表
        deleteQuOptionByQuId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteQuListByIds(List<Long> ids) {
        // 删除
        quMapper.deleteByIds(ids);
    
    // 删除子表
            deleteQuOptionByQuIds(ids);
    }


    private void validateQuExists(Long id) {
        if (quMapper.selectById(id) == null) {
            throw exception(QU_NOT_EXISTS);
        }
    }

    @Override
    public YwQuDO getQu(Long id) {
        return quMapper.selectById(id);
    }

    @Override
    public PageResult<YwQuDO> getQuPage(YwQuPageReqVO pageReqVO) {
        return quMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（试题选项） ====================

    @Override
    public PageResult<YwQuOptionDO> getQuOptionPage(PageParam pageReqVO, Long quId) {
        return quOptionMapper.selectPage(pageReqVO, quId);
    }

    @Override
    public Long createQuOption(YwQuOptionDO quOption) {
        quOption.clean(); // 清理掉创建、更新时间等相关属性值
        quOptionMapper.insert(quOption);
        return quOption.getId();
    }

    @Override
    public void updateQuOption(YwQuOptionDO quOption) {
        // 校验存在
        validateQuOptionExists(quOption.getId());
        // 更新
        quOption.clean(); // 解决更新情况下：updateTime 不更新
        quOptionMapper.updateById(quOption);
    }

    @Override
    public void deleteQuOption(Long id) {
        // 删除
        quOptionMapper.deleteById(id);
    }

	@Override
	public void deleteQuOptionListByIds(List<Long> ids) {
        // 删除
        quOptionMapper.deleteByIds(ids);
	}

    @Override
    public YwQuOptionDO getQuOption(Long id) {
        return quOptionMapper.selectById(id);
    }

    private void validateQuOptionExists(Long id) {
        if (quOptionMapper.selectById(id) == null) {
            throw exception(QU_OPTION_NOT_EXISTS);
        }
    }

    private void deleteQuOptionByQuId(Long quId) {
        quOptionMapper.deleteByQuId(quId);
    }

	private void deleteQuOptionByQuIds(List<Long> quIds) {
        quOptionMapper.deleteByQuIds(quIds);
	}

}