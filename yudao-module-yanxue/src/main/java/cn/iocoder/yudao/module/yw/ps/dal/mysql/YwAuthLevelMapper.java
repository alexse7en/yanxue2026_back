package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthConditionMemberVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthDetailMemberVo;
import cn.iocoder.yudao.module.yw.ps.vo.YwAuthLevelForTeacherVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthLevelDO;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 评审申请 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwAuthLevelMapper extends BaseMapperX<YwAuthLevelDO> {

    default PageResult<YwAuthLevelDO> selectPage(YwAuthLevelPageReqVO reqVO,
                                                 Collection<Long> memberIdsFilter,
                                                 Collection<Long> teacherIdsFilter) {
        LambdaQueryWrapperX<YwAuthLevelDO> qw = new LambdaQueryWrapperX<YwAuthLevelDO>()
                .likeIfPresent(YwAuthLevelDO::getName, reqVO.getName())
                .eqIfPresent(YwAuthLevelDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(YwAuthLevelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(YwAuthLevelDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(YwAuthLevelDO::getMemberId, memberIdsFilter)
                .orderByDesc(YwAuthLevelDO::getId);

        // 导师筛选（teacher 是 "1,2,250" 这样的字符串）
        if (teacherIdsFilter != null && !teacherIdsFilter.isEmpty()) {
            qw.and(w -> {
                for (Long tid : teacherIdsFilter) {
                    // MySQL: FIND_IN_SET(?, teacher)
                    w.or().apply("FIND_IN_SET({0}, teacher)", tid);
                }
            });
        }
        return selectPage(reqVO, qw);
    }

    default List<YwAuthLevelDO> selectPageList(YwAuthLevelPageReqVO reqVO) {
        LambdaQueryWrapperX<YwAuthLevelDO> qw = new LambdaQueryWrapperX<YwAuthLevelDO>()
                .likeIfPresent(YwAuthLevelDO::getName, reqVO.getName())
                .eqIfPresent(YwAuthLevelDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(YwAuthLevelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(YwAuthLevelDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(YwAuthLevelDO::getMemberId, reqVO.getMemberId())
                .orderByDesc(YwAuthLevelDO::getId);

        return selectList( qw);
    }

    void  beginAuth(@Param("param") Map<String, Object> params);
    void  beginAuthCondition(@Param("levelId")Long levelId, @Param("memberId")Long memberId, @Param("authId")Long authId);
    void  beginAuthDetail(@Param("levelId")Long levelId, @Param("memberId")Long memberId, @Param("authId")Long authId);
    List<YwAuthConditionMemberVo> SelectAuthCondition(@Param("authId")Long authId);
    List<YwAuthDetailMemberVo> SelectAuthDetail(@Param("authId")Long authId);
    List<YwAuthConditionMemberVo> SelectAuthConditionForTeacherComment(@Param("authId")Long authId,@Param("memberId")Long memberId);
    List<YwAuthDetailMemberVo> SelectAuthDetailForTeacherComment(@Param("authId")Long authId,@Param("memberId")Long memberId);

    @Update("update yw_auth_level set teacher=#{teacher}, status='5' where id =#{authId}")
    int updateAuthTeacher(@Param("authId")Long authId, @Param("teacher")String teacher);

    @Update("update yw_auth_condition set urls=#{urls}, iz_selected=#{izSelected}  where id =#{condiId}")
    int updateAuthCondition(@Param("condiId")Long condiId, @Param("izSelected")String izSelected, @Param("urls")String urls);

    @Update("update yw_auth_detail set urls=#{urls}, iz_selected=#{izSelected} where id =#{condiId}")
    int updateAuthDetail(@Param("condiId")Long condiId, @Param("izSelected")String izSelected, @Param("urls")String urls);

    @Delete("delete from yw_auth_comment where auth_id=#{authId} and teacher_id =#{memberId}")
    int deleteAuthComment(@Param("authId")Long authId, @Param("memberId")Long memberId);

    @Delete("delete from yw_auth_comment_status where auth_id=#{authId} and teacher_id =#{memberId}")
    int deleteAuthCommentStatus(@Param("authId")Long authId, @Param("memberId")Long memberId);

    @Update("update  yw_auth_level set status='4' where id=#{authId} ")
    int submitAuth(@Param("authId")Long authId);

    @Update("update  yw_auth_level set status='1' where id=#{authId} and (status='0' or status is null)")
    int updateToSave(@Param("authId")Long authId);

    @Update("update  yw_auth_level set status=#{status} where id=#{authId} ")
    int updateStatus(@Param("authId")Long authId, @Param("status")String status);

    @Select("<script>" +
            "select a.*, b.status as teacher_status,b.teacher_id " +
            "from yw_auth_level a " +
            "left join yw_auth_comment_status b on a.id=b.auth_id and b.teacher_id=#{teacher} " +
            "where CONCAT(',', a.teacher, ',') LIKE CONCAT('%,', #{teacher}, ',%') " +
            "<if test='teacherStatus != null'>" +
            "   <choose>" +
            "       <when test='teacherStatus == 1'>" +
            "           and b.status = 1" +
            "       </when>" +
            "       <when test='teacherStatus == 0'>" +
            "           and b.status is null" +
            "       </when>" +
            "   </choose>" +
            "</if>" +
            "order by  create_time desc" +
            "</script>")
    List<YwAuthLevelForTeacherVo> getTeacherJob(@Param("teacher") Long teacher, @Param("teacherStatus") String teacherStatus);

    @Select("select a.* from yw_auth_level a  where a.status!='0' and a.member_id=#{memberId} order by create_time desc")
    List<YwAuthLevelDO> my(@Param("memberId")Long memberId);

    @Select("SELECT COUNT(1) FROM yw_auth_level WHERE status = #{status} AND (teacher IS NULL OR teacher = 0)")
    Long selectCountByStatusAndAuditUserIsNull(@Param("status") Integer status);
}