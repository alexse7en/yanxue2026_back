package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwTeacherService;
import cn.iocoder.yudao.module.yw.vo.page.YwTeacherPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwTeacherSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwTeacherDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwTeacherMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 教师 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwTeacherServiceImpl implements YwTeacherService {

    @Resource
    private YwTeacherMapper teacherMapper;

    @Override
    public String createTeacher(YwTeacherSaveReqVO createReqVO) {
        // 插入
        YwTeacherDO teacher = BeanUtils.toBean(createReqVO, YwTeacherDO.class);
        teacherMapper.insert(teacher);

        // 返回
        return teacher.getId();
    }

    @Override
    public void updateTeacher(YwTeacherSaveReqVO updateReqVO) {
        // 校验存在
        validateTeacherExists(updateReqVO.getId());
        // 更新
        YwTeacherDO updateObj = BeanUtils.toBean(updateReqVO, YwTeacherDO.class);
        teacherMapper.updateById(updateObj);
    }

    @Override
    public void deleteTeacher(String id) {
        // 校验存在
        validateTeacherExists(id);
        // 删除
        teacherMapper.deleteById(id);
    }

    @Override
        public void deleteTeacherListByIds(List<String> ids) {
        // 删除
        teacherMapper.deleteByIds(ids);
        }


    private void validateTeacherExists(String id) {
        if (teacherMapper.selectById(id) == null) {
            throw exception(TEACHER_NOT_EXISTS);
        }
    }

    @Override
    public YwTeacherDO getTeacher(String id) {
        return teacherMapper.selectById(id);
    }

    @Override
    public PageResult<YwTeacherDO> getTeacherPage(YwTeacherPageReqVO pageReqVO) {
        return teacherMapper.selectPage(pageReqVO);
    }

}