package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.MemberConditionStatusVo;
import cn.iocoder.yudao.module.yw.vo.YwCourseMemberVO;
import cn.iocoder.yudao.module.yw.vo.page.YwCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseSaveReqVO;

/**
 * 课程 Service 接口
 *
 * @author wangxi
 */
public interface YwCourseService {

    /**
     * 创建课程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCourse(@Valid YwCourseSaveReqVO createReqVO);

    /**
     * 更新课程
     *
     * @param updateReqVO 更新信息
     */
    void updateCourse(@Valid YwCourseSaveReqVO updateReqVO);

    /**
     * 删除课程
     *
     * @param id 编号
     */
    void deleteCourse(Long id);

    /**
    * 批量删除课程
    *
    * @param ids 编号
    */
    void deleteCourseListByIds(List<Long> ids);

    /**
     * 获得课程
     *
     * @param id 编号
     * @return 课程
     */
    YwCourseDO getCourse(Long id);

    /**
     * 获得课程分页
     *
     * @param pageReqVO 分页查询
     * @return 课程分页
     */
    PageResult<YwCourseDO> getCoursePage(YwCoursePageReqVO pageReqVO);
    List<YwCourseMemberVO> selectCourse();

    // ==================== 子表（章节） ====================

    /**
     * 获得章节分页
     *
     * @param pageReqVO 分页查询
     * @param courseId 课程id
     * @return 章节分页
     */
    PageResult<YwChapterDO> getChapterPage(PageParam pageReqVO, Long courseId);

    /**
     * 创建章节
     *
     * @param chapter 创建信息
     * @return 编号
     */
    Long createChapter(@Valid YwChapterDO chapter);

    /**
     * 更新章节
     *
     * @param chapter 更新信息
     */
    void updateChapter(@Valid YwChapterDO chapter);

    /**
     * 删除章节
     *
     * @param id 编号
     */
    void deleteChapter(Long id);

    /**
    * 批量删除章节
    *
    * @param ids 编号
    */
    void deleteChapterListByIds(List<Long> ids);

	/**
	 * 获得章节
	 *
	 * @param id 编号
     * @return 章节
	 */
    YwChapterDO getChapter(Long id);

    YwCourseMemberVO beginCourse(Long courseId);
    YwCourseMemberVO selectCourseId(Long courseId);
    int updateChapterMember(Long chapterId);
    int updateAllChapterMember(Long courseId, Long memberId);
    int updateCourseMember(Long courseId, Long memberId);

    MemberConditionStatusVo selectMemberConditionStatus();
}