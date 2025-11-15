package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwAuthDetailService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthDetailSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthDetailMapper;

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
public class YwAuthDetailServiceImpl implements YwAuthDetailService {

    @Resource
    private YwAuthDetailMapper ywAuthDetailMapper;

    @Override
    public Long createYwAuthDetail(YwAuthDetailSaveReqVO createReqVO) {
        // 插入
        YwAuthDetailDO ywAuthDetail = BeanUtils.toBean(createReqVO, YwAuthDetailDO.class);
        ywAuthDetailMapper.insert(ywAuthDetail);

        // 返回
        return ywAuthDetail.getId();
    }

    @Override
    public void updateYwAuthDetail(YwAuthDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateYwAuthDetailExists(updateReqVO.getId());
        // 更新
        YwAuthDetailDO updateObj = BeanUtils.toBean(updateReqVO, YwAuthDetailDO.class);
        ywAuthDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwAuthDetail(Long id) {
        // 校验存在
        validateYwAuthDetailExists(id);
        // 删除
        ywAuthDetailMapper.deleteById(id);
    }

    @Override
        public void deleteYwAuthDetailListByIds(List<Long> ids) {
        // 删除
        ywAuthDetailMapper.deleteByIds(ids);
        }


    private void validateYwAuthDetailExists(Long id) {
        if (ywAuthDetailMapper.selectById(id) == null) {
            throw exception(YW_AUTH_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public YwAuthDetailDO getYwAuthDetail(Long id) {
        return ywAuthDetailMapper.selectById(id);
    }

    @Override
    public PageResult<YwAuthDetailDO> getYwAuthDetailPage(YwAuthDetailPageReqVO pageReqVO) {
        return ywAuthDetailMapper.selectPage(pageReqVO);
    }

}