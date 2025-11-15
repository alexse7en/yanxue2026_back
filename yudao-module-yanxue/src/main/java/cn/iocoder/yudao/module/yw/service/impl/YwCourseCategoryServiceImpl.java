package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwCourseCategoryService;
import cn.iocoder.yudao.module.yw.vo.page.YwCourseCategoryPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseCategorySaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwCourseCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 课程分类 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwCourseCategoryServiceImpl implements YwCourseCategoryService {

    @Resource
    private YwCourseCategoryMapper courseCategoryMapper;

    @Override
    public String createCourseCategory(YwCourseCategorySaveReqVO createReqVO) {
        // 插入
        YwCourseCategoryDO courseCategory = BeanUtils.toBean(createReqVO, YwCourseCategoryDO.class);
        courseCategoryMapper.insert(courseCategory);

        // 返回
        return courseCategory.getId();
    }

    @Override
    public void updateCourseCategory(YwCourseCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateCourseCategoryExists(updateReqVO.getId());
        // 更新
        YwCourseCategoryDO updateObj = BeanUtils.toBean(updateReqVO, YwCourseCategoryDO.class);
        courseCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCourseCategory(String id) {
        // 校验存在
        validateCourseCategoryExists(id);
        // 删除
        courseCategoryMapper.deleteById(id);
    }

    @Override
        public void deleteCourseCategoryListByIds(List<String> ids) {
        // 删除
        courseCategoryMapper.deleteByIds(ids);
        }


    private void validateCourseCategoryExists(String id) {
        if (courseCategoryMapper.selectById(id) == null) {
            throw exception(COURSE_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public YwCourseCategoryDO getCourseCategory(String id) {
        return courseCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<YwCourseCategoryDO> getCourseCategoryPage(YwCourseCategoryPageReqVO pageReqVO) {
        return courseCategoryMapper.selectPage(pageReqVO);
    }

}