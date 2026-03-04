package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.*;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.*;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.GenericApplyReqVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class YwVipFacadeServiceImpl implements YwVipFacadeService {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private YwVipApplyMapper vipApplyMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private YwVipInfoApplyMapper vipInfoApplyMapper;
    @Resource
    private YwOrgApplyRecordMapper orgApplyRecordMapper;
    @Resource
    private YwOrgInfoMapper orgInfoMapper;
    @Resource
    private YwOrgInfoApplyMapper orgInfoApplyMapper;

    @Override
    public Map<String, Object> getMyVipApply() {
        return toResp(vipApplyMapper.selectLatestByMemberId(getLoginUserId()));
    }

    @Override
    public Long createVipApply(GenericApplyReqVO reqVO) {
        YwVipApplyDO data = new YwVipApplyDO();
        data.setMemberId(getLoginUserId());
        data.setApplyStatus(0);
        data.setPayloadJson(toJson(reqVO.getFormData()));
        vipApplyMapper.insert(data);
        return data.getId();
    }

    @Override
    public void updateVipApply(GenericApplyReqVO reqVO) {
        YwVipApplyDO exists = requireVipApply(reqVO.getId());
        if (!Objects.equals(exists.getMemberId(), getLoginUserId())) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        exists.setPayloadJson(toJson(reqVO.getFormData()));
        vipApplyMapper.updateById(exists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveVipApplyDraft(GenericApplyReqVO reqVO) {
        if (reqVO.getId() == null) {
            YwVipApplyDO data = new YwVipApplyDO();
            data.setMemberId(getLoginUserId());
            data.setApplyStatus(0);
            data.setPayloadJson(toJson(reqVO.getFormData()));
            vipApplyMapper.insert(data);
            return data.getId();
        }
        YwVipApplyDO exists = requireVipApply(reqVO.getId());
        exists.setApplyStatus(0);
        exists.setPayloadJson(toJson(reqVO.getFormData()));
        vipApplyMapper.updateById(exists);
        return exists.getId();
    }

    @Override
    public boolean submitVipApply(GenericApplyReqVO reqVO) {
        Long id = saveVipApplyDraft(reqVO);
        YwVipApplyDO exists = requireVipApply(id);
        exists.setApplyStatus(1);
        exists.setSubmitTime(LocalDateTime.now());
        vipApplyMapper.updateById(exists);
        return true;
    }

    @Override
    public Map<String, Object> getMyVipInfo() {
        YwVipInfoDO info = vipInfoMapper.selectMyActive(getLoginUserId());
        return toResp(info);
    }

    @Override
    public Long createVipInfoApply(GenericApplyReqVO reqVO) {
        YwVipInfoApplyDO data = new YwVipInfoApplyDO();
        data.setMemberId(getLoginUserId());
        data.setVipinfoId(reqVO.getVipinfoId());
        data.setApplyStatus(1);
        data.setSubmitTime(LocalDateTime.now());
        data.setPayloadJson(toJson(reqVO.getFormData()));
        vipInfoApplyMapper.insert(data);
        return data.getId();
    }

    @Override
    public void updateVipInfoApply(GenericApplyReqVO reqVO) {
        YwVipInfoApplyDO exists = requireVipInfoApply(reqVO.getId());
        if (!Objects.equals(exists.getApplyStatus(), 1)) {
            throw new ServiceException(400, "仅待审核申请允许修改");
        }
        if (!Objects.equals(exists.getMemberId(), getLoginUserId())) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        exists.setPayloadJson(toJson(reqVO.getFormData()));
        vipInfoApplyMapper.updateById(exists);
    }

    @Override
    public Map<String, Object> getLatestMyVipInfoApply() {
        return toResp(vipInfoApplyMapper.selectLatestByMemberId(getLoginUserId()));
    }

    @Override
    public Map<String, Object> getMyOrgApply(String applyType) {
        return toResp(orgApplyRecordMapper.selectLatestByMemberAndType(getLoginUserId(), applyType));
    }

    @Override
    public Long saveOrgApplyDraft(GenericApplyReqVO reqVO) {
        if (reqVO.getId() == null) {
            YwOrgApplyRecordDO data = new YwOrgApplyRecordDO();
            data.setMemberId(getLoginUserId());
            data.setApplyType(reqVO.getApplyType());
            data.setApplyStatus(0);
            data.setPayloadJson(toJson(reqVO.getFormData()));
            orgApplyRecordMapper.insert(data);
            return data.getId();
        }
        YwOrgApplyRecordDO exists = requireOrgApply(reqVO.getId());
        if (!Objects.equals(exists.getMemberId(), getLoginUserId())) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        exists.setApplyType(reqVO.getApplyType());
        exists.setApplyStatus(0);
        exists.setPayloadJson(toJson(reqVO.getFormData()));
        orgApplyRecordMapper.updateById(exists);
        return exists.getId();
    }

    @Override
    public boolean submitOrgApply(GenericApplyReqVO reqVO) {
        Long id = saveOrgApplyDraft(reqVO);
        YwOrgApplyRecordDO exists = requireOrgApply(id);
        exists.setApplyStatus(1);
        exists.setSubmitTime(LocalDateTime.now());
        orgApplyRecordMapper.updateById(exists);
        return true;
    }

    @Override
    public List<Map<String, Object>> listMyOrgInfo() {
        List<YwOrgInfoDO> list = orgInfoMapper.selectMyPassedList(getLoginUserId());
        List<Map<String, Object>> result = new ArrayList<>();
        for (YwOrgInfoDO item : list) {
            result.add(toResp(item));
        }
        return result;
    }

    @Override
    public Map<String, Object> getLatestMyOrgInfoApply(Long orginfoId) {
        return toResp(orgInfoApplyMapper.selectLatestByMemberAndOrginfoId(getLoginUserId(), orginfoId));
    }

    @Override
    public Long createOrgInfoApply(GenericApplyReqVO reqVO) {
        YwOrgInfoApplyDO data = new YwOrgInfoApplyDO();
        data.setMemberId(getLoginUserId());
        data.setOrginfoId(reqVO.getOrginfoId());
        data.setApplyStatus(1);
        data.setSubmitTime(LocalDateTime.now());
        data.setPayloadJson(toJson(reqVO.getFormData()));
        orgInfoApplyMapper.insert(data);
        return data.getId();
    }

    @Override
    public void updateOrgInfoApply(GenericApplyReqVO reqVO) {
        YwOrgInfoApplyDO exists = requireOrgInfoApply(reqVO.getId());
        if (!Objects.equals(exists.getApplyStatus(), 1)) {
            throw new ServiceException(400, "仅待审核申请允许修改");
        }
        if (!Objects.equals(exists.getMemberId(), getLoginUserId())) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        exists.setPayloadJson(toJson(reqVO.getFormData()));
        orgInfoApplyMapper.updateById(exists);
    }

    @Override
    public Map<String, Object> parseFile(String scene, String applyType, String filePath, String fileType) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("scene", scene);
        result.put("applyType", applyType);
        result.put("filePath", filePath);
        result.put("fileType", fileType);
        result.put("parsedAt", LocalDateTime.now().toString());
        result.put("message", "已完成占位解析，可在此扩展 docx/xlsx 真正解析逻辑");
        return result;
    }

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }

    private String toJson(Map<String, Object> formData) {
        try {
            return objectMapper.writeValueAsString(formData == null ? Collections.emptyMap() : formData);
        } catch (Exception e) {
            throw new ServiceException(400, "申请数据序列化失败");
        }
    }

    private Map<String, Object> toMap(String json) {
        if (json == null || json.isEmpty()) {
            return new LinkedHashMap<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return new LinkedHashMap<>();
        }
    }

    private Map<String, Object> toResp(Object data) {
        if (data == null) {
            return null;
        }
        Map<String, Object> map = objectMapper.convertValue(data, new TypeReference<Map<String, Object>>() {});
        Object payload = map.remove("payloadJson");
        if (payload instanceof String) {
            map.putAll(toMap((String) payload));
        }
        return map;
    }

    private YwVipApplyDO requireVipApply(Long id) {
        YwVipApplyDO data = vipApplyMapper.selectById(id);
        if (data == null) {
            throw new ServiceException(404, "会员申请不存在");
        }
        return data;
    }

    private YwVipInfoApplyDO requireVipInfoApply(Long id) {
        YwVipInfoApplyDO data = vipInfoApplyMapper.selectById(id);
        if (data == null) {
            throw new ServiceException(404, "会员信息编辑申请不存在");
        }
        return data;
    }

    private YwOrgApplyRecordDO requireOrgApply(Long id) {
        YwOrgApplyRecordDO data = orgApplyRecordMapper.selectById(id);
        if (data == null) {
            throw new ServiceException(404, "二级认证申请不存在");
        }
        return data;
    }

    private YwOrgInfoApplyDO requireOrgInfoApply(Long id) {
        YwOrgInfoApplyDO data = orgInfoApplyMapper.selectById(id);
        if (data == null) {
            throw new ServiceException(404, "展示资料编辑申请不存在");
        }
        return data;
    }
}
