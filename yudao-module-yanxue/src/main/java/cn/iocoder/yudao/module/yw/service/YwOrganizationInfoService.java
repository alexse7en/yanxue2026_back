package cn.iocoder.yudao.module.yw.service;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationInfoSubmitReqVO;

import java.util.List;

public interface YwOrganizationInfoService {
    void submitOrganizationInfo(YwOrganizationInfoSubmitReqVO reqVO, String username);
    boolean checkExistByUsernameAndStatus(String username, int status);
    List<YwOrganizationInfoDO> getPassedOrgsByUserId(String username);
    List<YwOrganizationInfoDO> listActive();
    YwOrganizationInfoDO getById(Long id);
    YwOrganizationInfoDO getByName(String name);
    Long apply(Long memberId, Long orgId);
    YwMemberApplyDO getMyLatestApply(Long memberId);
}

