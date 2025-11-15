package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberCourseDO;
import cn.iocoder.yudao.module.yw.vo.YwMemberCourseTotalVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberCoursePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 课程进度 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwMemberCourseMapper extends BaseMapperX<YwMemberCourseDO> {

    default PageResult<YwMemberCourseDO> selectPage(YwMemberCoursePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwMemberCourseDO>()
                .eqIfPresent(YwMemberCourseDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwMemberCourseDO::getCourseId, reqVO.getCourseId())
                .eqIfPresent(YwMemberCourseDO::getAccomplishFlag, reqVO.getAccomplishFlag())
                .eqIfPresent(YwMemberCourseDO::getLearningChapterNum, reqVO.getLearningChapterNum())
                .eqIfPresent(YwMemberCourseDO::getAllChapterNum, reqVO.getAllChapterNum())
                .eqIfPresent(YwMemberCourseDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwMemberCourseDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwMemberCourseDO::getLastChapterId, reqVO.getLastChapterId())
                .orderByDesc(YwMemberCourseDO::getId));
    }

@Select("select a.*, b.title from yw_member_course a inner join yw_course b on a.course_id=b.id where a.member_id=#{memberId} and accomplish_flag='1'")
    public List<YwMemberCourseTotalVO> myFinish(Long memberId);
}