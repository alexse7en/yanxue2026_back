package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.YwMemberApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwOrganizationInfoMapper;
import cn.iocoder.yudao.module.yw.service.YwOrganizationInfoService;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationInfoSubmitReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class YwOrganizationInfoServiceImpl implements YwOrganizationInfoService {

    @Resource
    private YwOrganizationInfoMapper organizationInfoMapper;
    @Resource
    private YwMemberApplyMapper memberApplyMapper;

    @Override
    public void submitOrganizationInfo(YwOrganizationInfoSubmitReqVO reqVO, String username) {
        YwOrganizationInfoDO info = new YwOrganizationInfoDO();
        info.setUsername(username);
        info.setIntro(reqVO.getIntro());
        info.setMaterial(reqVO.getMaterial());
        info.setStatus(0); // 待审核
        info.setSubmitTime(LocalDateTime.now());
        organizationInfoMapper.insert(info);
    }

    public boolean checkExistByUsernameAndStatus(String username, int status) {
        // MyBatis方式举例，假设有selectCountByUsernameAndStatus方法
        Integer count = organizationInfoMapper.selectCountByUsernameAndStatus(username, status);
        return count != null && count > 0;
    }

    public List<YwOrganizationInfoDO> getPassedOrgsByUserId(String username) {
        return organizationInfoMapper.selectList(
                new LambdaQueryWrapper<YwOrganizationInfoDO>()
                        .eq(YwOrganizationInfoDO::getUsername,username)
                        .eq(YwOrganizationInfoDO::getStatus, 1) // 假设1代表已通过
        );
    }

    @Override
    public List<YwOrganizationInfoDO> listActive() {
        return organizationInfoMapper.selectList(Wrappers.<YwOrganizationInfoDO>lambdaQuery()
                .eq(YwOrganizationInfoDO::getStatus, 1)
                .orderByAsc(YwOrganizationInfoDO::getId));
    }

    @Override
    public YwOrganizationInfoDO getById(Long id) {
        return organizationInfoMapper.selectById(id);
    }

    @Override
    public YwOrganizationInfoDO getByName(String name) {
        return organizationInfoMapper.selectOne(Wrappers.<YwOrganizationInfoDO>lambdaQuery()
                .eq(YwOrganizationInfoDO::getUsername, name).last("limit 1"));
    }

    @Override
    @Transactional
    public Long apply(Long memberId, Long orgId) {
        // 是否已有待审/已通过申请
        YwMemberApplyDO exists = memberApplyMapper.selectOne(Wrappers.<YwMemberApplyDO>lambdaQuery()
                .eq(YwMemberApplyDO::getMemberId, memberId)
                .in(YwMemberApplyDO::getStatus, Arrays.asList(0,1))
                .orderByDesc(YwMemberApplyDO::getId)
                .last("limit 1"));
        if (exists != null && exists.getStatus() != null && exists.getStatus() != 2) {


        }

        YwMemberApplyDO record = new YwMemberApplyDO();
        record.setMemberId(memberId);
        record.setOrgId(orgId);
        record.setStatus(0);
        record.setApplyTime(LocalDateTime.now());
        memberApplyMapper.insert(record);
        return record.getId();
    }

    @Override
    public YwMemberApplyDO getMyLatestApply(Long memberId) {
        return memberApplyMapper.selectOne(Wrappers.<YwMemberApplyDO>lambdaQuery()
                .eq(YwMemberApplyDO::getMemberId, memberId)
                .orderByDesc(YwMemberApplyDO::getId).last("limit 1"));
    }



}

