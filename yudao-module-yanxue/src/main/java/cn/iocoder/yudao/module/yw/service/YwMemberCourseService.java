package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberCourseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.YwMemberCourseTotalVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberCourseSaveReqVO;

/**
 * 课程进度 Service 接口
 *
 * @author 芋道源码
 */
public interface YwMemberCourseService {

    /**
     * 创建课程进度
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMemberCourse(@Valid YwMemberCourseSaveReqVO createReqVO);

    /**
     * 更新课程进度
     *
     * @param updateReqVO 更新信息
     */
    void updateMemberCourse(@Valid YwMemberCourseSaveReqVO updateReqVO);

    /**
     * 删除课程进度
     *
     * @param id 编号
     */
    void deleteMemberCourse(String id);

    /**
    * 批量删除课程进度
    *
    * @param ids 编号
    */
    void deleteMemberCourseListByIds(List<String> ids);

    /**
     * 获得课程进度
     *
     * @param id 编号
     * @return 课程进度
     */
    YwMemberCourseDO getMemberCourse(String id);

    /**
     * 获得课程进度分页
     *
     * @param pageReqVO 分页查询
     * @return 课程进度分页
     */
    PageResult<YwMemberCourseDO> getMemberCoursePage(YwMemberCoursePageReqVO pageReqVO);
    List<YwMemberCourseDO> my();
    List<YwMemberCourseTotalVO> myFinish();

}