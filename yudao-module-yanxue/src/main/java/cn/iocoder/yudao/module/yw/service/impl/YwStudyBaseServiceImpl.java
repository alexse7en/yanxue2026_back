package cn.iocoder.yudao.module.yw.service.impl;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.yw.vo.page.YwStudyBasePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwStudyBaseSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwStudyBaseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.yw.service.YwStudyBaseService;

import cn.iocoder.yudao.module.yw.dal.mysql.YwStudyBaseMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 研学基地/营地 Service 实现类
 *
 * @author 科协超级管理员
 */
@Service
@Validated
public class YwStudyBaseServiceImpl implements YwStudyBaseService {

    @Resource
    private YwStudyBaseMapper studyBaseMapper;

    @Override
    public Long createStudyBase(YwStudyBaseSaveReqVO createReqVO) {
        // 插入
        YwStudyBaseDO studyBase = BeanUtils.toBean(createReqVO, YwStudyBaseDO.class);
        studyBaseMapper.insert(studyBase);

        // 返回
        return studyBase.getId();
    }

    @Override
    public void updateStudyBase(YwStudyBaseSaveReqVO updateReqVO) {
        // 校验存在
        validateStudyBaseExists(updateReqVO.getId());
        // 更新
        YwStudyBaseDO updateObj = BeanUtils.toBean(updateReqVO, YwStudyBaseDO.class);
        studyBaseMapper.updateById(updateObj);
    }

    @Override
    public void deleteStudyBase(Long id) {
        // 校验存在
        validateStudyBaseExists(id);
        // 删除
        studyBaseMapper.deleteById(id);
    }

    @Override
    public void deleteStudyBaseListByIds(List<Long> ids) {
        // 删除
        studyBaseMapper.deleteByIds(ids);
    }


    private void validateStudyBaseExists(Long id) {
        if (studyBaseMapper.selectById(id) == null) {
            throw exception(STUDY_BASE_NOT_EXISTS);
        }
    }

    @Override
    public YwStudyBaseDO getStudyBase(Long id) {
        return studyBaseMapper.selectById(id);
    }

    @Override
    public PageResult<YwStudyBaseDO> getStudyBasePage(YwStudyBasePageReqVO pageReqVO) {
        return studyBaseMapper.selectPage(pageReqVO);
    }

    // ========= 新增：给前台地图用的查询实现 =========

    @Override
    public List<YwStudyBaseDO> getStudyBaseListForMap(String city, String themeType, Boolean isRecommend) {
        return studyBaseMapper.selectListForMap(city, themeType, isRecommend);
    }

}