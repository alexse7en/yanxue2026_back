package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.module.yw.ps.service.YwAuthCommentStatusService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthCommentStatusPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentStatusSaveReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthCommentStatusDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthCommentStatusMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_AUTH_COMMENT_STATUS_NOT_EXISTS;

/**
 * 专家评审状态 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwAuthCommentStatusServiceImpl implements YwAuthCommentStatusService {

    @Resource
    private YwAuthCommentStatusMapper ywAuthCommentStatusMapper;

    @Override
    public Long createYwAuthCommentStatus(YwAuthCommentStatusSaveReqVO createReqVO) {
        // 插入
        YwAuthCommentStatusDO ywAuthCommentStatus = BeanUtils.toBean(createReqVO, YwAuthCommentStatusDO.class);
        ywAuthCommentStatusMapper.insert(ywAuthCommentStatus);

        // 返回
        return ywAuthCommentStatus.getId();
    }

    @Override
    public void updateYwAuthCommentStatus(YwAuthCommentStatusSaveReqVO updateReqVO) {
        // 校验存在
        validateYwAuthCommentStatusExists(updateReqVO.getId());
        // 更新
        YwAuthCommentStatusDO updateObj = BeanUtils.toBean(updateReqVO, YwAuthCommentStatusDO.class);
        ywAuthCommentStatusMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwAuthCommentStatus(Long id) {
        // 校验存在
        validateYwAuthCommentStatusExists(id);
        // 删除
        ywAuthCommentStatusMapper.deleteById(id);
    }

    @Override
        public void deleteYwAuthCommentStatusListByIds(List<Long> ids) {
        // 删除
        ywAuthCommentStatusMapper.deleteByIds(ids);
        }


    private void validateYwAuthCommentStatusExists(Long id) {
        if (ywAuthCommentStatusMapper.selectById(id) == null) {
            throw exception(YW_AUTH_COMMENT_STATUS_NOT_EXISTS);
        }
    }

    @Override
    public YwAuthCommentStatusDO getYwAuthCommentStatus(Long id) {
        return ywAuthCommentStatusMapper.selectById(id);
    }

    @Override
    public PageResult<YwAuthCommentStatusDO> getYwAuthCommentStatusPage(YwAuthCommentStatusPageReqVO pageReqVO) {
        return ywAuthCommentStatusMapper.selectPage(pageReqVO);
    }

    @Override
    public int updateTeacherCommentStatus(Long authId, Long teacherId){
        int result=ywAuthCommentStatusMapper.izAuthMemberCommented(authId);
        return result;


    }
    @Override
    public   boolean getAllTeacherCommentStatus(Long authId, String teacherIds){
        String[] teacherIdList=teacherIds.split(",");
        LambdaQueryWrapper<YwAuthCommentStatusDO> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(YwAuthCommentStatusDO::getAuthId,authId).in(YwAuthCommentStatusDO::getTeacherId,teacherIdList);
        List<YwAuthCommentStatusDO> list=ywAuthCommentStatusMapper.selectList(queryWrapper);
        if(list==null || list.size()==0){
            return false;
        }else if(list.size()==teacherIdList.length){
            return true;
        }
        return false;
    }

    @Override
    public String getTeacherStatusByMemberId(Long authId,Long teacherId ){
        LambdaQueryWrapper<YwAuthCommentStatusDO> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(YwAuthCommentStatusDO::getAuthId,authId).in(YwAuthCommentStatusDO::getTeacherId,teacherId);
        List<YwAuthCommentStatusDO> list=ywAuthCommentStatusMapper.selectList(queryWrapper);
        if(list==null || list.size()==0){
            return "0";
        }else{
            return "1";
        }
    }
}