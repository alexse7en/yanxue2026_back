package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.*;
import cn.iocoder.yudao.module.yw.vo.vip.*;

import java.util.List;

public interface YwVipFacadeService {

    YwOrgApplyRecordDO getMyOrgApply(String applyType);

    Long saveOrgApplyDraft(YwOrgApplySaveReqVO reqVO);

    boolean submitOrgApply(YwOrgApplySaveReqVO reqVO);

    List<YwOrgInfoDO> listMyOrgInfo();

    YwOrgInfoApplyDO getLatestMyOrgInfoApply(Long orginfoId);

    Long createOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO);

    void updateOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO);

    YwVipApplyDO getMyVipApply();

    Long saveVipApplyDraft(YwVipApplySaveReqVO reqVO);

    boolean submitVipApply(YwVipApplySaveReqVO reqVO);

    YwVipInfoDO getMyVipInfo();

    YwVipInfoApplyDO getLatestMyVipInfoApply();

    Long createVipInfoApply(YwVipInfoApplySaveReqVO reqVO);

    void updateVipInfoApply(YwVipInfoApplySaveReqVO reqVO);
}
