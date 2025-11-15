    package cn.iocoder.yudao.module.member.dal.mysql.user;

    import cn.hutool.core.collection.CollUtil;
    import cn.hutool.core.lang.Assert;
    import cn.hutool.core.util.StrUtil;
    import cn.iocoder.yudao.framework.common.pojo.PageResult;
    import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
    import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
    import cn.iocoder.yudao.module.member.controller.admin.user.vo.MemberUserPageReqVO;
    import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
    import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Param;
    import org.apache.ibatis.annotations.Select;
    import org.apache.ibatis.annotations.Update;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    /**
     * 会员 User Mapper
     *
     * @author 芋道源码
     */
    @Mapper
    public interface MemberUserMapper extends BaseMapperX<MemberUserDO> {

        default MemberUserDO selectByMobile(String mobile) {
            return selectOne(MemberUserDO::getMobile, mobile);
        }

        default List<MemberUserDO> selectListByNicknameLike(String nickname) {
            return selectList(new LambdaQueryWrapperX<MemberUserDO>()
                    .likeIfPresent(MemberUserDO::getNickname, nickname));
        }

        default PageResult<MemberUserDO> selectPage(MemberUserPageReqVO reqVO) {
            // 处理 tagIds 过滤条件
            String tagIdSql = "";
            if (CollUtil.isNotEmpty(reqVO.getTagIds())) {
                tagIdSql = reqVO.getTagIds().stream()
                        .map(tagId -> "FIND_IN_SET(" + tagId + ", tag_ids)")
                        .collect(Collectors.joining(" OR "));
            }
            // 分页查询
            return selectPage(reqVO, new LambdaQueryWrapperX<MemberUserDO>()
                    .likeIfPresent(MemberUserDO::getMobile, reqVO.getMobile())
                    .betweenIfPresent(MemberUserDO::getLoginDate, reqVO.getLoginDate())
                    .likeIfPresent(MemberUserDO::getNickname, reqVO.getNickname())
                    .betweenIfPresent(MemberUserDO::getCreateTime, reqVO.getCreateTime())
                    .eqIfPresent(MemberUserDO::getLevelId, reqVO.getLevelId())
                    //.eqIfPresent(MemberUserDO::getGroupId, reqVO.getGroupId())
                    .eqIfPresent(MemberUserDO::getIzteacher, reqVO.getIzteacher())
                    .eqIfPresent(MemberUserDO::getOrgName, reqVO.getOrgName())
                    .apply(StrUtil.isNotEmpty(tagIdSql), tagIdSql)
                    .orderByDesc(MemberUserDO::getId));
        }

        default Long selectCountByGroupId(Long groupId) {
            return selectCount(MemberUserDO::getGroupId, groupId);
        }

        default Long selectCountByLevelId(Long levelId) {
            return selectCount(MemberUserDO::getLevelId, levelId);
        }

        default Long selectCountByTagId(Long tagId) {
            return selectCount(new LambdaQueryWrapperX<MemberUserDO>()
                    .apply("FIND_IN_SET({0}, tag_ids)", tagId));
        }

        /**
         * 更新用户积分（增加）
         *
         * @param id        用户编号
         * @param incrCount 增加积分（正数）
         */
        default void updatePointIncr(Long id, Integer incrCount) {
            Assert.isTrue(incrCount > 0);
            LambdaUpdateWrapper<MemberUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<MemberUserDO>()
                    .setSql(" point = point + " + incrCount)
                    .eq(MemberUserDO::getId, id);
            update(null, lambdaUpdateWrapper);
        }

        /**
         * 更新用户积分（减少）
         *
         * @param id        用户编号
         * @param incrCount 增加积分（负数）
         * @return 更新行数
         */
        default int updatePointDecr(Long id, Integer incrCount) {
            Assert.isTrue(incrCount < 0);
            LambdaUpdateWrapper<MemberUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<MemberUserDO>()
                    .setSql(" point = point + " + incrCount) // 负数，所以使用 + 号
                    .eq(MemberUserDO::getId, id);
            return update(null, lambdaUpdateWrapper);
        }
        @Update("update member_user set org_school=#{school}, org_profession=#{profession}, area=#{area}, mark=#{mark} where id=#{id}")
        int addUserDetail(@Param("id") Long id,@Param("school")  String school,@Param("profession")  String profession,@Param("mark")  String mark,@Param("area")  String area);
        @Update("update member_user set name=#{name}, idcard=#{idCard} where id=#{id}")
        int addUserRenzhen(@Param("id") Long id,@Param("name")  String name,@Param("idCard")  String idCard);

        @Update("update member_user set status=#{status} where id=#{id}")
        int addUserRenzhenStatus(@Param("id") Long id, @Param("status") Long status);

        @Update("update member_user set level_id=#{levelId} where id=#{id}")
        int updateMemberLevelId(@Param("id") Long id, @Param("levelId") Long levelId);

        @Select("SELECT COUNT(1) FROM member_user")
        Long selectCountAll();

        @Select("SELECT COUNT(1) FROM member_user WHERE create_time BETWEEN #{begin} AND #{end}")
        Long selectCountBetweenCreateTime(@Param("begin") LocalDateTime begin,
                                          @Param("end") LocalDateTime end);

        @Select("SELECT COUNT(1) FROM member_user WHERE level_id IS NOT NULL")
        Long selectCountVerifiedByNameNotBlank();

        /** 当天新增（按 create_time 分组） */
        @Select("SELECT DATE(create_time) AS d, COUNT(1) AS c " +
                "FROM member_user " +
                "WHERE create_time >= #{begin} AND create_time < #{end} " +
                "GROUP BY DATE(create_time) " +
                "ORDER BY d")
        List<Map<String, Object>> selectDailyNewMembers(@Param("begin") LocalDateTime begin,
                                                        @Param("end") LocalDateTime end);

        /** 当天完成认证数（按“通过时间”分组） */
        @Select("SELECT DATE(update_time) AS d, COUNT(1) AS c " +
                "FROM member_user " +
                "WHERE status = 10 " +
                "  AND update_time >= #{begin} AND update_time < #{end} " +
                "GROUP BY DATE(update_time) " +
                "ORDER BY d")
        List<Map<String, Object>> selectDailyNewVerified(@Param("begin") LocalDateTime begin,
                                                         @Param("end") LocalDateTime end);

        /** 统计区间内活跃用户（login_date 在 [begin, end)）*/
        @Select("SELECT COUNT(1) " +
                "FROM member_user " +
                "WHERE login_date >= #{begin} AND login_date < #{end}")
        Long countActiveBetween(@Param("begin") LocalDateTime begin,
                                @Param("end") LocalDateTime end);

        /** 统计区间内新增会员（以 create_time 为准，若你是 register_time 就换列名）*/
        @Select("SELECT COUNT(1) " +
                "FROM member_user " +
                "WHERE create_time >= #{begin} AND create_time < #{end}")
        Long countNewMembersBetween(@Param("begin") LocalDateTime begin,
                                    @Param("end") LocalDateTime end);

        /** 当天活跃（按 login_date 分组，DAU） */
        @Select("SELECT DATE(login_date) AS d, COUNT(1) AS c " +
                "FROM member_user " +
                "WHERE login_date >= #{begin} AND login_date < #{end} " +
                "GROUP BY DATE(login_date) " +
                "ORDER BY d")
        List<Map<String, Object>> selectDailyActiveByLoginDate(@Param("begin") LocalDateTime begin,
                                                               @Param("end") LocalDateTime end);



    }
