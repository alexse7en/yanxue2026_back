package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.service.YwMemberCourseService;
import cn.iocoder.yudao.module.yw.vo.YwMemberCourseTotalVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberCourseSaveReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberCourseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwMemberCourseMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 课程进度 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwMemberCourseServiceImpl implements YwMemberCourseService {

    @Resource
    private YwMemberCourseMapper memberCourseMapper;

    @Override
    public String createMemberCourse(YwMemberCourseSaveReqVO createReqVO) {
        // 插入
        YwMemberCourseDO memberCourse = BeanUtils.toBean(createReqVO, YwMemberCourseDO.class);
        memberCourseMapper.insert(memberCourse);

        // 返回
        return memberCourse.getId();
    }

    @Override
    public void updateMemberCourse(YwMemberCourseSaveReqVO updateReqVO) {
        // 校验存在
        validateMemberCourseExists(updateReqVO.getId());
        // 更新
        YwMemberCourseDO updateObj = BeanUtils.toBean(updateReqVO, YwMemberCourseDO.class);
        memberCourseMapper.updateById(updateObj);
    }

    @Override
    public void deleteMemberCourse(String id) {
        // 校验存在
        validateMemberCourseExists(id);
        // 删除
        memberCourseMapper.deleteById(id);
    }

    @Override
        public void deleteMemberCourseListByIds(List<String> ids) {
        // 删除
        memberCourseMapper.deleteByIds(ids);
        }


    private void validateMemberCourseExists(String id) {
        if (memberCourseMapper.selectById(id) == null) {
            throw exception(MEMBER_COURSE_NOT_EXISTS);
        }
    }

    @Override
    public YwMemberCourseDO getMemberCourse(String id) {
        return memberCourseMapper.selectById(id);
    }

    @Override
    public PageResult<YwMemberCourseDO> getMemberCoursePage(YwMemberCoursePageReqVO pageReqVO) {
        return memberCourseMapper.selectPage(pageReqVO);
    }

    @Override
    public List<YwMemberCourseDO> my(){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        LambdaQueryWrapper<YwMemberCourseDO> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YwMemberCourseDO::getMemberId,memberId).orderByDesc(YwMemberCourseDO::getCreateTime);
        return memberCourseMapper.selectList(lambdaQueryWrapper);
    }
    @Override
    public List<YwMemberCourseTotalVO> myFinish(){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        return memberCourseMapper.myFinish(memberId);
    }

}