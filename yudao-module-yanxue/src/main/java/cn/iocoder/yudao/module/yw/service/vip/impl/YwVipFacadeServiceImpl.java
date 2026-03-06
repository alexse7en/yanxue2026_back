package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.*;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.*;
import cn.iocoder.yudao.module.yw.service.vip.YwVipFacadeService;
import cn.iocoder.yudao.module.yw.vo.vip.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class YwVipFacadeServiceImpl implements YwVipFacadeService {

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
    @Resource
    private YwVipApplyDocxParser ywVipApplyDocxParser;


    @Override
    public YwOrgApplyRecordDO getMyOrgApply(String applyType) {
        return orgApplyRecordMapper.selectLatestByUserAndType(getLoginUserId(), applyType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrgApplyDraft(YwOrgApplySaveReqVO reqVO) {
        YwOrgApplyRecordDO data = saveOrUpdateOrgApply(reqVO, 0);
        return data.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitOrgApply(YwOrgApplySaveReqVO reqVO) {
        if (reqVO.getUnitName() == null || reqVO.getUnitName().trim().isEmpty()) {
            throw new ServiceException(400, "unit_name不能为空");
        }
        saveOrUpdateOrgApply(reqVO, 1);
        return true;
    }

    @Override
    public List<YwOrgInfoDO> listMyOrgInfo() {
        return orgInfoMapper.selectListByUserId(getLoginUserId());
    }

    @Override
    public YwOrgInfoApplyDO getLatestMyOrgInfoApply(Long orginfoId) {
        return orgInfoApplyMapper.selectLatestByUserAndOrginfoId(getLoginUserId(), orginfoId);
    }

    @Override
    public Long createOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwOrgInfoDO orgInfo = requireOwnedOrgInfo(reqVO.getOrginfoId(), userId);
        YwOrgInfoApplyDO data = BeanUtils.toBean(reqVO, YwOrgInfoApplyDO.class);
        data.setOrginfoId(orgInfo.getId());
        data.setUserId(userId);
        data.setApplyStatus(0);
        orgInfoApplyMapper.insert(data);
        return data.getId();
    }

    @Override
    public void updateOrgInfoApply(YwOrgInfoApplySaveReqVO reqVO) {
        YwOrgInfoApplyDO exists = requireOrgInfoApply(reqVO.getId());
        Long userId = getLoginUserId();
        if (!Objects.equals(exists.getUserId(), userId)) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        requireOwnedOrgInfo(exists.getOrginfoId(), userId);
        if (!Objects.equals(exists.getApplyStatus(), 0)) {
            throw new ServiceException(400, "仅允许更新待审核记录");
        }
        YwOrgInfoApplyDO update = BeanUtils.toBean(reqVO, YwOrgInfoApplyDO.class);
        update.setId(exists.getId());
        update.setUserId(exists.getUserId());
        update.setOrginfoId(exists.getOrginfoId());
        update.setApplyStatus(exists.getApplyStatus());
        orgInfoApplyMapper.updateById(update);
    }

    @Override
    public YwVipApplyDO getMyVipApply() {
        return vipApplyMapper.selectLatestByUserId(getLoginUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveVipApplyDraft(YwVipApplySaveReqVO reqVO) {
        YwVipApplyDO data = saveOrUpdateVipApply(reqVO, 0);
        return data.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitVipApply(YwVipApplySaveReqVO reqVO) {
        saveOrUpdateVipApply(reqVO, 1);
        return true;
    }

    @Override
    public YwVipInfoDO getMyVipInfo() {
        return vipInfoMapper.selectByUserId(getLoginUserId());
    }

    @Override
    public YwVipInfoApplyDO getLatestMyVipInfoApply() {
        return vipInfoApplyMapper.selectLatestByUserId(getLoginUserId());
    }

    @Override
    public Long createVipInfoApply(YwVipInfoApplySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwVipInfoDO vipInfo = requireOwnedVipInfo(reqVO.getVipinfoId(), userId);
        YwVipInfoApplyDO data = BeanUtils.toBean(reqVO, YwVipInfoApplyDO.class);
        data.setVipinfoId(vipInfo.getId());
        data.setUserId(userId);
        data.setApplyStatus(0);
        vipInfoApplyMapper.insert(data);
        return data.getId();
    }

    @Override
    public void updateVipInfoApply(YwVipInfoApplySaveReqVO reqVO) {
        YwVipInfoApplyDO exists = requireVipInfoApply(reqVO.getId());
        Long userId = getLoginUserId();
        if (!Objects.equals(exists.getUserId(), userId)) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        requireOwnedVipInfo(exists.getVipinfoId(), userId);
        if (!Objects.equals(exists.getApplyStatus(), 0)) {
            throw new ServiceException(400, "仅允许更新待审核记录");
        }
        YwVipInfoApplyDO update = BeanUtils.toBean(reqVO, YwVipInfoApplyDO.class);
        update.setId(exists.getId());
        update.setUserId(exists.getUserId());
        update.setVipinfoId(exists.getVipinfoId());
        update.setApplyStatus(exists.getApplyStatus());
        vipInfoApplyMapper.updateById(update);
    }

    private YwOrgApplyRecordDO saveOrUpdateOrgApply(YwOrgApplySaveReqVO reqVO, Integer applyStatus) {
        Long userId = getLoginUserId();
        Long vipinfoId = resolveVipinfoId(reqVO.getVipinfoId(), userId);

        YwOrgApplyRecordDO data;
        if (reqVO.getId() == null) {
            data = BeanUtils.toBean(reqVO, YwOrgApplyRecordDO.class);
            data.setUserId(userId);
            data.setVipinfoId(vipinfoId);
            data.setApplyStatus(applyStatus);
            orgApplyRecordMapper.insert(data);
            return data;
        }

        data = requireOrgApply(reqVO.getId());
        if (!Objects.equals(data.getUserId(), userId)) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        YwOrgApplyRecordDO update = BeanUtils.toBean(reqVO, YwOrgApplyRecordDO.class);
        update.setId(data.getId());
        update.setUserId(data.getUserId());
        update.setVipinfoId(vipinfoId);
        update.setApplyStatus(applyStatus);
        orgApplyRecordMapper.updateById(update);
        return requireOrgApply(data.getId());
    }

    private YwVipApplyDO saveOrUpdateVipApply(YwVipApplySaveReqVO reqVO, Integer applyStatus) {
        Long userId = getLoginUserId();
        if (reqVO.getId() == null) {
            YwVipApplyDO data = BeanUtils.toBean(reqVO, YwVipApplyDO.class);
            data.setUserId(userId);
            data.setApplyStatus(applyStatus);
            vipApplyMapper.insert(data);
            return data;
        }
        YwVipApplyDO exists = requireVipApply(reqVO.getId());
        if (!Objects.equals(exists.getUserId(), userId)) {
            throw new ServiceException(403, "无权限更新该申请");
        }
        YwVipApplyDO update = BeanUtils.toBean(reqVO, YwVipApplyDO.class);
        update.setId(exists.getId());
        update.setUserId(exists.getUserId());
        update.setApplyStatus(applyStatus);
        vipApplyMapper.updateById(update);
        return requireVipApply(exists.getId());
    }

    private Long resolveVipinfoId(Long vipinfoId, Long userId) {
        if (vipinfoId != null) {
            requireOwnedVipInfo(vipinfoId, userId);
            return vipinfoId;
        }
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(userId);
        if (vipInfo == null) {
            throw new ServiceException(400, "请先成为会员单位");
        }
        return vipInfo.getId();
    }

    private YwVipInfoDO requireOwnedVipInfo(Long vipinfoId, Long userId) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectById(vipinfoId);
        if (vipInfo == null) {
            throw new ServiceException(404, "会员单位信息不存在");
        }
        if (!Objects.equals(vipInfo.getUserId(), userId)) {
            throw new ServiceException(403, "该会员单位不属于当前用户");
        }
        return vipInfo;
    }

    private YwOrgInfoDO requireOwnedOrgInfo(Long orginfoId, Long userId) {
        YwOrgInfoDO orgInfo = orgInfoMapper.selectById(orginfoId);
        if (orgInfo == null) {
            throw new ServiceException(404, "认证单位不存在");
        }
        if (!Objects.equals(orgInfo.getUserId(), userId)) {
            throw new ServiceException(403, "该认证单位不属于当前用户");
        }
        return orgInfo;
    }

    private YwVipApplyDO requireVipApply(Long id) {
        YwVipApplyDO data = vipApplyMapper.selectById(id);
        if (data == null) {
            throw new ServiceException(404, "会员申请不存在");
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

    private YwVipInfoApplyDO requireVipInfoApply(Long id) {
        YwVipInfoApplyDO data = vipInfoApplyMapper.selectById(id);
        if (data == null) {
            throw new ServiceException(404, "会员信息编辑申请不存在");
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

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }

    @Override
    public YwVipApplyDO parseVipApply(ParseReqVO reqVO) {
        Long userId = getLoginUserId();

        String fileType = resolveFileType(reqVO);
        if (!"docx".equalsIgnoreCase(fileType)) {
            throw new ServiceException(400, "目前仅支持上传 docx 模板");
        }

        YwVipApplyDO data = vipApplyMapper.selectLatestByUserId(userId);
        if (data == null) {
            data = new YwVipApplyDO();
            data.setUserId(userId);
            data.setApplyStatus(0);
        }

        data.setFilePath(reqVO.getFilePath());
        data.setFileType("docx");

        try {
            YwVipApplySaveReqVO parsed = ywVipApplyDocxParser.parse(reqVO.getFilePath());
            mergeParsedFields(data, parsed);
            data.setParseStatus(1);
            data.setParseError(null);
        } catch (Exception e) {
            data.setParseStatus(2);
            data.setParseError(limitMsg(e.getMessage()));
        }

        saveOrUpdateVipApplyRecord(data);
        return requireVipApply(data.getId());
    }

    private String resolveFileType(ParseReqVO reqVO) {
        if (reqVO.getFileType() != null && !reqVO.getFileType().trim().isEmpty()) {
            return reqVO.getFileType().trim().toLowerCase();
        }
        String filePath = reqVO.getFilePath();
        int index = filePath.lastIndexOf('.');
        if (index < 0 || index == filePath.length() - 1) {
            return "";
        }
        return filePath.substring(index + 1).toLowerCase();
    }

    private void saveOrUpdateVipApplyRecord(YwVipApplyDO data) {
        if (data.getId() == null) {
            vipApplyMapper.insert(data);
        } else {
            vipApplyMapper.updateById(data);
        }
    }

    private String limitMsg(String msg) {
        if (msg == null || msg.trim().isEmpty()) {
            return "文件解析失败";
        }
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
    }

    private void mergeParsedFields(YwVipApplyDO target, YwVipApplySaveReqVO source) {
        setIfHasText(source.getCompanyName(), target::setCompanyName);
        setIfHasText(source.getCompanyAddress(), target::setCompanyAddress);
        setIfHasText(source.getCompanyPhone(), target::setCompanyPhone);
        setIfHasText(source.getWebsite(), target::setWebsite);
        setIfNotNull(source.getEstablishedDate(), target::setEstablishedDate);
        setIfHasText(source.getBusinessScope(), target::setBusinessScope);
        setIfHasText(source.getCompanyIntro(), target::setCompanyIntro);

        setIfHasText(source.getRepName(), target::setRepName);
        setIfHasText(source.getRepPolitical(), target::setRepPolitical);
        setIfHasText(source.getRepGender(), target::setRepGender);
        setIfHasText(source.getRepEducation(), target::setRepEducation);
        setIfHasText(source.getRepPhone(), target::setRepPhone);
        setIfHasText(source.getRepPosition(), target::setRepPosition);
        setIfHasText(source.getRepEmail(), target::setRepEmail);
        setIfHasText(source.getRepIdcard(), target::setRepIdcard);

        setIfHasText(source.getContactName(), target::setContactName);
        setIfHasText(source.getContactPhone(), target::setContactPhone);
        setIfHasText(source.getCompanyType(), target::setCompanyType);
        setIfHasText(source.getApplyLevel(), target::setApplyLevel);
        setIfNotNull(source.getApplyDate(), target::setApplyDate);
        setIfHasText(source.getMemberNo(), target::setMemberNo);
    }

    private void setIfHasText(String value, java.util.function.Consumer<String> setter) {
        if (value != null && !value.trim().isEmpty()) {
            setter.accept(value.trim());
        }
    }

    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
