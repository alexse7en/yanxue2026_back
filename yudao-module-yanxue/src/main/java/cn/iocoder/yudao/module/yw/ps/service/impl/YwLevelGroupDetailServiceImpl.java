package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwLevelGroupDetailService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwLevelGroupDetailPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwLevelGroupDetailSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwLevelGroupDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwLevelGroupDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 评分项 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwLevelGroupDetailServiceImpl implements YwLevelGroupDetailService {

    @Resource
    private YwLevelGroupDetailMapper ywLevelGroupDetailMapper;

    @Override
    public Long createYwLevelGroupDetail(YwLevelGroupDetailSaveReqVO createReqVO) {
        // 插入
        YwLevelGroupDetailDO ywLevelGroupDetail = BeanUtils.toBean(createReqVO, YwLevelGroupDetailDO.class);
        ywLevelGroupDetailMapper.insert(ywLevelGroupDetail);

        // 返回
        return ywLevelGroupDetail.getId();
    }

    @Override
    public void updateYwLevelGroupDetail(YwLevelGroupDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateYwLevelGroupDetailExists(updateReqVO.getId());
        // 更新
        YwLevelGroupDetailDO updateObj = BeanUtils.toBean(updateReqVO, YwLevelGroupDetailDO.class);
        ywLevelGroupDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwLevelGroupDetail(Long id) {
        // 校验存在
        validateYwLevelGroupDetailExists(id);
        // 删除
        ywLevelGroupDetailMapper.deleteById(id);
    }

    @Override
        public void deleteYwLevelGroupDetailListByIds(List<Long> ids) {
        // 删除
        ywLevelGroupDetailMapper.deleteByIds(ids);
        }


    private void validateYwLevelGroupDetailExists(Long id) {
        if (ywLevelGroupDetailMapper.selectById(id) == null) {
            throw exception(YW_LEVEL_GROUP_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public YwLevelGroupDetailDO getYwLevelGroupDetail(Long id) {
        return ywLevelGroupDetailMapper.selectById(id);
    }

    @Override
    public PageResult<YwLevelGroupDetailDO> getYwLevelGroupDetailPage(YwLevelGroupDetailPageReqVO pageReqVO) {
        return ywLevelGroupDetailMapper.selectPage(pageReqVO);
    }

}