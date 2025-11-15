package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwCourseCategoryPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseCategorySaveReqVO;

/**
 * 课程分类 Service 接口
 *
 * @author 芋道源码
 */
public interface YwCourseCategoryService {

    /**
     * 创建课程分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createCourseCategory(@Valid YwCourseCategorySaveReqVO createReqVO);

    /**
     * 更新课程分类
     *
     * @param updateReqVO 更新信息
     */
    void updateCourseCategory(@Valid YwCourseCategorySaveReqVO updateReqVO);

    /**
     * 删除课程分类
     *
     * @param id 编号
     */
    void deleteCourseCategory(String id);

    /**
    * 批量删除课程分类
    *
    * @param ids 编号
    */
    void deleteCourseCategoryListByIds(List<String> ids);

    /**
     * 获得课程分类
     *
     * @param id 编号
     * @return 课程分类
     */
    YwCourseCategoryDO getCourseCategory(String id);

    /**
     * 获得课程分类分页
     *
     * @param pageReqVO 分页查询
     * @return 课程分类分页
     */
    PageResult<YwCourseCategoryDO> getCourseCategoryPage(YwCourseCategoryPageReqVO pageReqVO);

}