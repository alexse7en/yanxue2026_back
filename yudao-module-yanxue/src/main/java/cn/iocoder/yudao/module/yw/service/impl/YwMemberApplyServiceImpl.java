package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.YwMemberApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwOrganizationInfoMapper;
import cn.iocoder.yudao.module.yw.service.YwMemberApplyService;
import cn.iocoder.yudao.module.yw.vo.YwMemberApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.YwMemberApplyBatchAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberApplySaveReqVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;



import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_MEMBER_APPLY_NOT_EXISTS;

/**
 * 组织-志愿者挂靠审核 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
@Transactional(rollbackFor = Exception.class) // 建议加在类或方法上
public class YwMemberApplyServiceImpl implements YwMemberApplyService {

    @Resource
    private YwMemberApplyMapper ywMemberApplyMapper;
    @Resource
    private MemberUserMapper memberUserMapper;
    @Resource
    private YwOrganizationInfoMapper ywOrganizationInfoMapper;

    private static final Integer STATUS_PASS = 1;

    @Override
    public Long createYwMemberApply(YwMemberApplySaveReqVO createReqVO) {
        // 插入
        YwMemberApplyDO ywMemberApply = BeanUtils.toBean(createReqVO, YwMemberApplyDO.class);
        ywMemberApplyMapper.insert(ywMemberApply);

        // 返回
        return ywMemberApply.getId();
    }

    @Override
    public void updateYwMemberApply(YwMemberApplySaveReqVO updateReqVO) {
        // 校验存在
        validateYwMemberApplyExists(updateReqVO.getId());
        // 更新
        YwMemberApplyDO updateObj = BeanUtils.toBean(updateReqVO, YwMemberApplyDO.class);
        ywMemberApplyMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwMemberApply(Long id) {
        // 校验存在
        validateYwMemberApplyExists(id);
        // 删除
        ywMemberApplyMapper.deleteById(id);
    }

    @Override
        public void deleteYwMemberApplyListByIds(List<Long> ids) {
        // 删除
        ywMemberApplyMapper.deleteByIds(ids);
        }


    private void validateYwMemberApplyExists(Long id) {
        if (ywMemberApplyMapper.selectById(id) == null) {
            throw exception(YW_MEMBER_APPLY_NOT_EXISTS);
        }
    }

    @Override
    public YwMemberApplyDO getYwMemberApply(Long id) {
        return ywMemberApplyMapper.selectById(id);
    }

    @Override
    public PageResult<YwMemberApplyRespVO> getYwMemberApplyPage(YwMemberApplyPageReqVO pageReqVO) {
// 1. 查询申请表分页数据
        PageResult<YwMemberApplyDO> applyPage = ywMemberApplyMapper.selectPage(pageReqVO);

        // 2. 判空保护
        if (applyPage == null || applyPage.getList() == null || applyPage.getList().isEmpty()) {
            return new PageResult<>(Collections.emptyList(), applyPage != null ? applyPage.getTotal() : 0L);
        }

        // 3. 批量查member
        List<Long> memberIds = applyPage.getList().stream()
                .map(YwMemberApplyDO::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 4. 这里需要注入 memberUserMapper
        List<MemberUserDO> memberList = memberUserMapper.selectByIds(memberIds);
        Map<Long, MemberUserDO> memberMap = memberList.stream()
                .collect(Collectors.toMap(MemberUserDO::getId, x -> x, (a, b) -> a));

        // 5. 合成VO
        List<YwMemberApplyRespVO> voList = applyPage.getList().stream().map(apply -> {
            YwMemberApplyRespVO vo = BeanUtils.toBean(apply, YwMemberApplyRespVO.class);
            MemberUserDO member = memberMap.get(apply.getMemberId());
            if (member != null) {
                vo.setName(member.getName());
                vo.setIdCard(member.getIdcard());
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageResult<>(voList, applyPage.getTotal());
    }

    @Override
    public void auditYwMemberApply(YwMemberApplyAuditReqVO reqVO) {

        YwMemberApplyDO apply = ywMemberApplyMapper.selectById(reqVO.getId());
        if (apply == null) {
            throw exception(YW_MEMBER_APPLY_NOT_EXISTS);
        }
        Integer oldStatus = apply.getStatus();
        Integer newStatus = reqVO.getStatus();

        // 2) 更新审核字段
        apply.setStatus(newStatus);
        apply.setAuditReason(reqVO.getAuditReason());
        apply.setAuditTime(LocalDateTime.now());
        apply.setAuditUser(SecurityFrameworkUtils.getLoginUserId());
        ywMemberApplyMapper.updateById(apply);

        // 3) 只有“非通过 -> 通过”时，同步组织信息到 member_user
        if (!Objects.equals(oldStatus, STATUS_PASS) && Objects.equals(newStatus, STATUS_PASS)) {
            Long memberId = apply.getMemberId();
            Long orgId = apply.getOrgId();

            if (memberId == null || orgId == null) {
                log.warn("[auditYwMemberApply] applyId={} 缺少 memberId/orgId，跳过同步 orgName", apply.getId());
                return;
            }

            YwOrganizationInfoDO org = ywOrganizationInfoMapper.selectById(orgId);
            if (org == null) {
                log.warn("[auditYwMemberApply] orgId={} 不存在，跳过同步 orgName", orgId);
                return;
            }

            // 组织显示名：你库里是 username 还是 name？按实际字段选择一个
            String orgDisplayName = org.getUsername(); // 或 org.getName()
            if (orgDisplayName == null) {
                orgDisplayName = "";
            }

            // 4) 只更新需要的字段，避免覆盖其它列
            memberUserMapper.update(
                    null,
                    Wrappers.<MemberUserDO>lambdaUpdate()
                            .set(MemberUserDO::getOrgName, orgDisplayName)
                            .set(MemberUserDO::getIzinvited,orgDisplayName)
                            // 可选：如果 member_user 也有 orgId 字段，一并同步
                            // .set(MemberUserDO::getOrgId, orgId)
                            .eq(MemberUserDO::getId, memberId)
            );

            log.info("[auditYwMemberApply] 审核通过，同步用户({}) orgName='{}' 完成", memberId, orgDisplayName);
        }
    }

    @Override
    public void batchAuditYwMemberApply(YwMemberApplyBatchAuditReqVO reqVO) {
        List<YwMemberApplyDO> list = ywMemberApplyMapper.selectBatchIds(reqVO.getIds());
        Long auditor = SecurityFrameworkUtils.getLoginUserId();
        Integer newStatus = reqVO.getStatus();

        for (YwMemberApplyDO apply : list) {
            Integer oldStatus = apply.getStatus();

            // 1) 更新审核字段
            apply.setStatus(newStatus);
            apply.setAuditReason(reqVO.getAuditReason());
            apply.setAuditTime(LocalDateTime.now());
            apply.setAuditUser(auditor);
            ywMemberApplyMapper.updateById(apply);

            // 2) 只有“非通过 -> 通过”时，同步组织信息
            if (!Objects.equals(oldStatus, STATUS_PASS) && Objects.equals(newStatus, STATUS_PASS)) {
                Long memberId = apply.getMemberId();
                Long orgId = apply.getOrgId();
                if (memberId == null || orgId == null) {
                    log.warn("[batchAudit] applyId={} 缺少 memberId/orgId，跳过同步", apply.getId());
                    continue;
                }
                YwOrganizationInfoDO org = ywOrganizationInfoMapper.selectById(orgId);
                if (org == null) {
                    log.warn("[batchAudit] orgId={} 不存在，跳过同步", orgId);
                    continue;
                }

                String orgDisplayName = org.getUsername(); // 或 org.getName()
                if (orgDisplayName == null) orgDisplayName = "";

                memberUserMapper.update(
                        null,
                        Wrappers.<MemberUserDO>lambdaUpdate()
                                .set(MemberUserDO::getOrgName, orgDisplayName)
                                .set(MemberUserDO::getIzinvited,orgDisplayName)
                                // 可选：同步 orgId
                                // .set(MemberUserDO::getOrgId, orgId)
                                .eq(MemberUserDO::getId, memberId)
                );
            }
        }
    }

}
