package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseDO;
import cn.iocoder.yudao.module.yw.vo.MemberConditionStatusVo;
import cn.iocoder.yudao.module.yw.vo.YwChapterMemberVO;
import cn.iocoder.yudao.module.yw.vo.YwCourseMemberVO;
import cn.iocoder.yudao.module.yw.vo.page.YwCoursePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 课程 Mapper
 *
 * @author wangxi
 */
@Mapper
public interface YwCourseMapper extends BaseMapperX<YwCourseDO> {

    default PageResult<YwCourseDO> selectPage(YwCoursePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwCourseDO>()
                .eqIfPresent(YwCourseDO::getId, reqVO.getId())
                .eqIfPresent(YwCourseDO::getTeacherId, reqVO.getTeacherId())
                .eqIfPresent(YwCourseDO::getCategory, reqVO.getCategory())
                .likeIfPresent(YwCourseDO::getTitle, reqVO.getTitle())
                .eqIfPresent(YwCourseDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(YwCourseDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwCourseDO::getDescription, reqVO.getDescription())
                .orderByDesc(YwCourseDO::getId));
    }

    @Select("select a.*, b.accomplish_flag, b.member_id from yw_course a left join yw_member_course b on b.course_id=a.id and  b.member_id=#{memberId}")
    List<YwCourseMemberVO> selectCourse(@Param("memberId") Long memberId);

    @Select("select a.*, b.accomplish_flag, b.member_id from yw_course a inner join yw_member_course b on b.course_id=a.id where a.id=#{courseId} and b.member_id=#{memberId}")
    YwCourseMemberVO selectCourseMemberVOById(@Param("courseId") Long courseId, @Param("memberId") Long memberId);
    int beginCourse(@Param("courseId") Long courseId, @Param("memberId") Long memberId);
    int beginCourseChapter(@Param("courseId") Long courseId, @Param("memberId") Long memberId);

    @Select("select a.*, b.accomplish_flag, b.member_id from yw_chapter a inner join yw_course c on a.course_id=c.id inner join yw_member_chapter b on b.chapter_id=a.id where c.id=#{courseId} and b.member_id=#{memberId}")
    List<YwChapterMemberVO> selectChapterMemberVOById(@Param("courseId") Long courseId, @Param("memberId") Long memberId);
    @Update("update yw_member_chapter set accomplish_flag='1'  where chapter_id=#{chapterId} and member_id=#{memberId}")
    int updateChapterMember(@Param("chapterId") Long chapterId, @Param("memberId") Long memberId);

    @Update("update yw_member_chapter set accomplish_flag='1'  where chapter_id in (select id from  yw_chapter where course_id=#{courseId}) and member_id=#{memberId}")
    int updateAllChapterMember(@Param("courseId") Long courseId, @Param("memberId") Long memberId);

    @Update("update yw_member_course set accomplish_flag='1'  where course_id=#{courseId} and member_id=#{memberId}")
    int updateCourseMember(@Param("courseId") Long courseId, @Param("memberId") Long memberId);

    @Select("select accomplish_flag as status,#{courseId} as course_id from yw_member_course  where course_id=#{courseId} and member_id=#{memberId}")
    MemberConditionStatusVo selectMemberConditionStatus(@Param("courseId") Long courseId, @Param("memberId") Long memberId);




}