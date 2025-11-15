package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.*;
import cn.iocoder.yudao.module.yw.service.YwCourseService;
import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.vo.page.YwCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseSaveReqVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwCourseMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwChapterMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 课程 Service 实现类
 *
 * @author wangxi
 */
@Service
@Validated
public class YwCourseServiceImpl implements YwCourseService {

    @Resource
    private YwCourseMapper courseMapper;
    @Resource
    private YwChapterMapper chapterMapper;

    @Override
    public Long createCourse(YwCourseSaveReqVO createReqVO) {
        // 插入
        YwCourseDO course = BeanUtils.toBean(createReqVO, YwCourseDO.class);
        courseMapper.insert(course);

        // 返回
        return course.getId();
    }

    @Override
    public void updateCourse(YwCourseSaveReqVO updateReqVO) {
        // 校验存在
        validateCourseExists(updateReqVO.getId());
        // 更新
        YwCourseDO updateObj = BeanUtils.toBean(updateReqVO, YwCourseDO.class);
        courseMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourse(Long id) {
        // 校验存在
        validateCourseExists(id);
        // 删除
        courseMapper.deleteById(id);

        // 删除子表
        deleteChapterByCourseId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteCourseListByIds(List<Long> ids) {
        // 删除
        courseMapper.deleteByIds(ids);
    
    // 删除子表
            deleteChapterByCourseIds(ids);
    }


    private void validateCourseExists(Long id) {
        if (courseMapper.selectById(id) == null) {
            throw exception(COURSE_NOT_EXISTS);
        }
    }

    @Override
    public YwCourseDO getCourse(Long id) {
        return courseMapper.selectById(id);
    }

    @Override
    public PageResult<YwCourseDO> getCoursePage(YwCoursePageReqVO pageReqVO) {
        return courseMapper.selectPage(pageReqVO);
    }
    @Override
    public List<YwCourseMemberVO> selectCourse() {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        return courseMapper.selectCourse(memberId);
    }

    // ==================== 子表（章节） ====================

    @Override
    public PageResult<YwChapterDO> getChapterPage(PageParam pageReqVO, Long courseId) {
        return chapterMapper.selectPage(pageReqVO, courseId);
    }

    @Override
    public Long createChapter(YwChapterDO chapter) {
        chapter.clean() ;// 清理掉创建、更新时间等相关属性值
        chapterMapper.insert(chapter);
        return chapter.getId();
    }

    @Override
    public void updateChapter(YwChapterDO chapter) {
        // 校验存在
        validateChapterExists(chapter.getId());
        // 更新
        chapter.clean(); // 解决更新情况下：updateTime 不更新
        chapterMapper.updateById(chapter);
    }

    @Override
    public void deleteChapter(Long id) {
        // 删除
        chapterMapper.deleteById(id);
    }

	@Override
	public void deleteChapterListByIds(List<Long> ids) {
        // 删除
        chapterMapper.deleteByIds(ids);
	}

    @Override
    public YwChapterDO getChapter(Long id) {
        return chapterMapper.selectById(id);
    }

    private void validateChapterExists(Long id) {
        if (chapterMapper.selectById(id) == null) {
            throw exception(CHAPTER_NOT_EXISTS);
        }
    }

    private void deleteChapterByCourseId(Long courseId) {
        chapterMapper.deleteByCourseId(courseId);
    }

	private void deleteChapterByCourseIds(List<Long> courseIds) {
        chapterMapper.deleteByCourseIds(courseIds);
	}


    @Override
    public YwCourseMemberVO beginCourse(Long courseId){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        courseMapper.beginCourse(courseId,memberId);
        courseMapper.beginCourseChapter(courseId,memberId);

        return selectCourseMember(courseId,memberId);
    }

    @Override
    public YwCourseMemberVO selectCourseId(Long courseId){

        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        YwCourseMemberVO vo=selectCourseMember(courseId,memberId);
        if(vo==null || StringUtils.isEmpty(vo.getId())){
            return beginCourse(courseId);
        }
        return vo;
    }

    public YwCourseMemberVO selectCourseMember(Long courseId,Long memberId){
        YwCourseMemberVO courseMemberVO=courseMapper.selectCourseMemberVOById(courseId, memberId);
        if(courseMemberVO==null || StringUtils.isEmpty(courseMemberVO.getId())){
            return null;
        }
        List<YwChapterMemberVO> chapters=courseMapper.selectChapterMemberVOById(courseId, memberId);
        courseMemberVO.setList(chapters);
        return courseMemberVO;
    }
    @Override
    public int updateChapterMember(Long chapterId){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        return courseMapper.updateChapterMember(chapterId,memberId);
    }

    @Override
    public int updateAllChapterMember(Long courseId, Long memberId){
        return courseMapper.updateAllChapterMember(courseId,memberId);
    }

    @Override
    public int updateCourseMember(Long courseId, Long memberId){
        return courseMapper.updateCourseMember(courseId,memberId);
    }

    @Value("${yw.first-courese-id}")
    Long firstCourseId;
    @Override
    public MemberConditionStatusVo selectMemberConditionStatus(){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        MemberConditionStatusVo vo=courseMapper.selectMemberConditionStatus(firstCourseId,memberId);
        if(vo==null){
            vo=new MemberConditionStatusVo();
            vo.setCourseId(String.valueOf(firstCourseId));
        }
        return vo;
    }
}