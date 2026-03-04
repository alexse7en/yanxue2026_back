package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.module.yw.vo.vip.GenericApplyReqVO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;

import java.util.List;
import java.util.Map;

public interface YwVipFacadeService {

    Map<String, Object> getMyVipApply();

    Long createVipApply(GenericApplyReqVO reqVO);

    void updateVipApply(GenericApplyReqVO reqVO);

    Long saveVipApplyDraft(GenericApplyReqVO reqVO);

    boolean submitVipApply(GenericApplyReqVO reqVO);

    Map<String, Object> getMyVipInfo();

    Long createVipInfoApply(GenericApplyReqVO reqVO);

    void updateVipInfoApply(GenericApplyReqVO reqVO);

    Map<String, Object> getLatestMyVipInfoApply();

    Map<String, Object> getMyOrgApply(String applyType);

    Long saveOrgApplyDraft(GenericApplyReqVO reqVO);

    boolean submitOrgApply(GenericApplyReqVO reqVO);

    List<Map<String, Object>> listMyOrgInfo();

    Map<String, Object> getLatestMyOrgInfoApply(Long orginfoId);

    Long createOrgInfoApply(GenericApplyReqVO reqVO);

    void updateOrgInfoApply(GenericApplyReqVO reqVO);

    Map<String, Object> parseFile(String scene, String applyType, String filePath, String fileType);
}
