package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwVipInfoApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwVipInfoApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplySaveReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_APPLY_AUDIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_APPLY_DUPLICATE_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_APPLY_QUERY_PARAM_REQUIRED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_APPLY_STATUS_NOT_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

@Service
@Validated
public class YwVipInfoApplyServiceImpl implements YwVipInfoApplyService {

    private static final Integer APPLY_STATUS_PENDING = 0;
    private static final Integer APPLY_STATUS_APPROVED = 1;
    private static final Integer APPLY_STATUS_REJECTED = 2;
    private static final int GALLERY_LIMIT = 3;

    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private YwVipInfoApplyMapper vipInfoApplyMapper;

    @Override
    public YwVipInfoRespVO getMyVipInfo() {
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(getLoginUserId());
        return vipInfo == null ? null : YwVipInfoApplyConvert.INSTANCE.convertVipInfo(vipInfo);
    }

    @Override
    public YwVipInfoApplyRespVO getVipInfoApply(Long id, Long userId) {
        YwVipInfoApplyDO apply;
        if (id != null) {
            apply = vipInfoApplyMapper.selectById(id);
        } else if (userId != null) {
            apply = vipInfoApplyMapper.selectByUserId(userId);
        } else {
            throw exception(YW_VIPINFO_APPLY_QUERY_PARAM_REQUIRED);
        }
        return enrichApplyResp(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createVipInfoApply(YwVipInfoApplySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwVipInfoDO vipInfo = requireOwnedVipInfo(reqVO.getVipinfoId(), userId);
        YwVipInfoApplyDO exists = vipInfoApplyMapper.selectByUserId(userId);
        if (exists != null && Objects.equals(exists.getApplyStatus(), APPLY_STATUS_PENDING)) {
            throw exception(YW_VIPINFO_APPLY_DUPLICATE_PENDING);
        }
        YwVipInfoApplyDO target = exists != null ? exists : new YwVipInfoApplyDO();
        fillApplySnapshotFromVipInfo(target, vipInfo);
        target.setUserId(userId);
        target.setVipinfoId(vipInfo.getId());
        target.setApplyStatus(APPLY_STATUS_PENDING);
        target.setAuditRemark(null);
        target.setAuditTime(null);
        target.setAuditorId(null);
        mergeApplyFields(target, reqVO);
        if (target.getId() == null) {
            vipInfoApplyMapper.insert(target);
        } else {
            vipInfoApplyMapper.updateById(target);
        }
        return target.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVipInfoApply(YwVipInfoApplySaveReqVO reqVO) {
        Long userId = getLoginUserId();
        YwVipInfoApplyDO target = reqVO.getId() != null ? vipInfoApplyMapper.selectById(reqVO.getId()) : vipInfoApplyMapper.selectByUserId(userId);
        if (target == null) {
            throw exception(YW_VIPINFO_APPLY_NOT_EXISTS);
        }
        if (!Objects.equals(target.getUserId(), userId)) {
            throw exception(YW_VIPINFO_APPLY_NOT_EXISTS);
        }
        YwVipInfoDO vipInfo = requireOwnedVipInfo(reqVO.getVipinfoId() != null ? reqVO.getVipinfoId() : target.getVipinfoId(), userId);
        if (!Objects.equals(target.getApplyStatus(), APPLY_STATUS_PENDING)) {
            fillApplySnapshotFromVipInfo(target, vipInfo);
        }
        target.setVipinfoId(vipInfo.getId());
        target.setApplyStatus(APPLY_STATUS_PENDING);
        target.setAuditRemark(null);
        target.setAuditTime(null);
        target.setAuditorId(null);
        mergeApplyFields(target, reqVO);
        vipInfoApplyMapper.updateById(target);
    }

    @Override
    public PageResult<YwVipInfoApplyRespVO> getVipInfoApplyPage(YwVipInfoApplyPageReqVO reqVO) {
        PageResult<YwVipInfoApplyDO> pageResult = vipInfoApplyMapper.selectPage(reqVO);
        List<YwVipInfoApplyRespVO> list = new ArrayList<>();
        for (YwVipInfoApplyDO item : pageResult.getList()) {
            list.add(enrichApplyResp(item));
        }
        return new PageResult<>(list, pageResult.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditVipInfoApply(YwVipInfoApplyAuditReqVO reqVO) {
        YwVipInfoApplyDO apply = vipInfoApplyMapper.selectById(reqVO.getId());
        if (apply == null) {
            throw exception(YW_VIPINFO_APPLY_NOT_EXISTS);
        }
        if (!Objects.equals(apply.getApplyStatus(), APPLY_STATUS_PENDING)) {
            throw exception(YW_VIPINFO_APPLY_STATUS_NOT_PENDING);
        }
        if (!Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)
                && !Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_REJECTED)) {
            throw exception(YW_VIPINFO_APPLY_AUDIT_STATUS_INVALID);
        }

        apply.setApplyStatus(reqVO.getApplyStatus());
        apply.setAuditRemark(reqVO.getAuditRemark());
        apply.setAuditTime(LocalDateTime.now());
        apply.setAuditorId(SecurityFrameworkUtils.getLoginUserId());
        vipInfoApplyMapper.updateById(apply);

        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            YwVipInfoDO vipInfo = vipInfoMapper.selectById(apply.getVipinfoId());
            if (vipInfo == null) {
                throw exception(YW_VIPINFO_NOT_EXISTS);
            }
            mergeApplyToVipInfo(vipInfo, apply);
            vipInfo.setApplyId(apply.getId());
            vipInfoMapper.updateById(vipInfo);
        }
    }

    private YwVipInfoDO requireOwnedVipInfo(Long vipinfoId, Long userId) {
        YwVipInfoDO vipInfo = vipinfoId != null ? vipInfoMapper.selectById(vipinfoId) : vipInfoMapper.selectByUserId(userId);
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        if (vipInfo.getUserId() != null && !Objects.equals(vipInfo.getUserId(), userId)) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        return vipInfo;
    }

    private YwVipInfoApplyRespVO enrichApplyResp(YwVipInfoApplyDO apply) {
        if (apply == null) {
            return null;
        }
        YwVipInfoApplyRespVO respVO = YwVipInfoApplyConvert.INSTANCE.convertApply(apply);
        YwVipInfoDO vipInfo = resolveVipInfo(apply);
        if (vipInfo != null) {
            respVO.setMemberNo(vipInfo.getMemberNo());
            respVO.setMemberLevel(vipInfo.getMemberLevel());
            respVO.setStar(vipInfo.getStar());
            respVO.setHonor(vipInfo.getHonor());
            if (!StringUtils.hasText(respVO.getCompanyName())) {
                respVO.setCompanyName(vipInfo.getCompanyName());
            }
            if (!StringUtils.hasText(respVO.getCompanyAddress())) {
                respVO.setCompanyAddress(vipInfo.getCompanyAddress());
            }
            if (!StringUtils.hasText(respVO.getCompanyPhone())) {
                respVO.setCompanyPhone(vipInfo.getCompanyPhone());
            }
            if (!StringUtils.hasText(respVO.getWebsite())) {
                respVO.setWebsite(vipInfo.getWebsite());
            }
            if (respVO.getEstablishedDate() == null) {
                respVO.setEstablishedDate(vipInfo.getEstablishedDate());
            }
            if (!StringUtils.hasText(respVO.getBusinessScope())) {
                respVO.setBusinessScope(vipInfo.getBusinessScope());
            }
            if (!StringUtils.hasText(respVO.getCompanyIntro())) {
                respVO.setCompanyIntro(vipInfo.getCompanyIntro());
            }
            if (!StringUtils.hasText(respVO.getLogo())) {
                respVO.setLogo(vipInfo.getLogo());
            }
            if (!StringUtils.hasText(respVO.getCompanyType())) {
                respVO.setCompanyType(vipInfo.getCompanyType());
            }
            if (!StringUtils.hasText(respVO.getContactName())) {
                respVO.setContactName(vipInfo.getContactName());
            }
            if (!StringUtils.hasText(respVO.getContactPhone())) {
                respVO.setContactPhone(vipInfo.getContactPhone());
            }
            if (!StringUtils.hasText(respVO.getRepName())) {
                respVO.setRepName(vipInfo.getRepName());
            }
            if (!StringUtils.hasText(respVO.getRepPosition())) {
                respVO.setRepPosition(vipInfo.getRepPosition());
            }
            if (!StringUtils.hasText(respVO.getRepPhone())) {
                respVO.setRepPhone(vipInfo.getRepPhone());
            }
            if (!StringUtils.hasText(respVO.getRepEmail())) {
                respVO.setRepEmail(vipInfo.getRepEmail());
            }
            if (!StringUtils.hasText(respVO.getGallery())) {
                respVO.setGallery(vipInfo.getGallery());
            }
        }
        respVO.setGallery(normalizeGallery(respVO.getGallery()));
        return respVO;
    }

    private YwVipInfoDO resolveVipInfo(YwVipInfoApplyDO apply) {
        if (apply.getVipinfoId() != null) {
            YwVipInfoDO vipInfo = vipInfoMapper.selectById(apply.getVipinfoId());
            if (vipInfo != null) {
                return vipInfo;
            }
        }
        return vipInfoMapper.selectByUserId(apply.getUserId());
    }

    private void fillApplySnapshotFromVipInfo(YwVipInfoApplyDO target, YwVipInfoDO vipInfo) {
        target.setCompanyName(vipInfo.getCompanyName());
        target.setCompanyAddress(vipInfo.getCompanyAddress());
        target.setCompanyPhone(vipInfo.getCompanyPhone());
        target.setWebsite(vipInfo.getWebsite());
        target.setEstablishedDate(vipInfo.getEstablishedDate());
        target.setBusinessScope(vipInfo.getBusinessScope());
        target.setCompanyIntro(vipInfo.getCompanyIntro());
        target.setLogo(vipInfo.getLogo());
        target.setCompanyType(vipInfo.getCompanyType());
        target.setContactName(vipInfo.getContactName());
        target.setContactPhone(vipInfo.getContactPhone());
        target.setRepName(vipInfo.getRepName());
        target.setRepPosition(vipInfo.getRepPosition());
        target.setRepPhone(vipInfo.getRepPhone());
        target.setRepEmail(vipInfo.getRepEmail());
        target.setGallery(normalizeGallery(vipInfo.getGallery()));
    }

    private void mergeApplyFields(YwVipInfoApplyDO target, YwVipInfoApplySaveReqVO reqVO) {
        if (reqVO.getCompanyName() != null) {
            target.setCompanyName(reqVO.getCompanyName());
        }
        if (reqVO.getCompanyAddress() != null) {
            target.setCompanyAddress(reqVO.getCompanyAddress());
        }
        if (reqVO.getCompanyPhone() != null) {
            target.setCompanyPhone(reqVO.getCompanyPhone());
        }
        if (reqVO.getWebsite() != null) {
            target.setWebsite(reqVO.getWebsite());
        }
        if (reqVO.getEstablishedDate() != null) {
            target.setEstablishedDate(reqVO.getEstablishedDate());
        }
        if (reqVO.getBusinessScope() != null) {
            target.setBusinessScope(reqVO.getBusinessScope());
        }
        if (reqVO.getCompanyIntro() != null) {
            target.setCompanyIntro(reqVO.getCompanyIntro());
        }
        if (reqVO.getLogo() != null) {
            target.setLogo(reqVO.getLogo());
        }
        if (reqVO.getCompanyType() != null) {
            target.setCompanyType(reqVO.getCompanyType());
        }
        if (reqVO.getContactName() != null) {
            target.setContactName(reqVO.getContactName());
        }
        if (reqVO.getContactPhone() != null) {
            target.setContactPhone(reqVO.getContactPhone());
        }
        if (reqVO.getRepName() != null) {
            target.setRepName(reqVO.getRepName());
        }
        if (reqVO.getRepPosition() != null) {
            target.setRepPosition(reqVO.getRepPosition());
        }
        if (reqVO.getRepPhone() != null) {
            target.setRepPhone(reqVO.getRepPhone());
        }
        if (reqVO.getRepEmail() != null) {
            target.setRepEmail(reqVO.getRepEmail());
        }
        if (reqVO.getGallery() != null) {
            target.setGallery(normalizeGallery(reqVO.getGallery()));
        }
    }

    private void mergeApplyToVipInfo(YwVipInfoDO vipInfo, YwVipInfoApplyDO apply) {
        if (apply.getCompanyName() != null) {
            vipInfo.setCompanyName(apply.getCompanyName());
        }
        if (apply.getCompanyAddress() != null) {
            vipInfo.setCompanyAddress(apply.getCompanyAddress());
        }
        if (apply.getCompanyPhone() != null) {
            vipInfo.setCompanyPhone(apply.getCompanyPhone());
        }
        if (apply.getWebsite() != null) {
            vipInfo.setWebsite(apply.getWebsite());
        }
        if (apply.getEstablishedDate() != null) {
            vipInfo.setEstablishedDate(apply.getEstablishedDate());
        }
        if (apply.getBusinessScope() != null) {
            vipInfo.setBusinessScope(apply.getBusinessScope());
        }
        if (apply.getCompanyIntro() != null) {
            vipInfo.setCompanyIntro(apply.getCompanyIntro());
        }
        if (apply.getLogo() != null) {
            vipInfo.setLogo(apply.getLogo());
        }
        if (apply.getCompanyType() != null) {
            vipInfo.setCompanyType(apply.getCompanyType());
        }
        if (apply.getContactName() != null) {
            vipInfo.setContactName(apply.getContactName());
        }
        if (apply.getContactPhone() != null) {
            vipInfo.setContactPhone(apply.getContactPhone());
        }
        if (apply.getRepName() != null) {
            vipInfo.setRepName(apply.getRepName());
        }
        if (apply.getRepPosition() != null) {
            vipInfo.setRepPosition(apply.getRepPosition());
        }
        if (apply.getRepPhone() != null) {
            vipInfo.setRepPhone(apply.getRepPhone());
        }
        if (apply.getRepEmail() != null) {
            vipInfo.setRepEmail(apply.getRepEmail());
        }
        if (apply.getGallery() != null) {
            vipInfo.setGallery(normalizeGallery(apply.getGallery()));
        }
    }

    private String normalizeGallery(String gallery) {
        if (gallery == null) {
            return null;
        }
        List<String> list = parseGallery(gallery);
        return String.join(",", list);
    }

    private List<String> parseGallery(String gallery) {
        if (!StringUtils.hasText(gallery)) {
            return Collections.emptyList();
        }
        String text = gallery.trim();
        List<String> result = new ArrayList<>();
        if (text.startsWith("[") && text.endsWith("]")) {
            try {
                List<String> array = JSONUtil.toList(text, String.class);
                for (String item : array) {
                    if (StringUtils.hasText(item)) {
                        result.add(item.trim());
                    }
                    if (result.size() >= GALLERY_LIMIT) {
                        break;
                    }
                }
                return result;
            } catch (Exception ignored) {
                // fallback to comma split
            }
        }
        String[] parts = text.split(",");
        for (String part : parts) {
            if (StringUtils.hasText(part)) {
                result.add(part.trim());
            }
            if (result.size() >= GALLERY_LIMIT) {
                break;
            }
        }
        if (result.isEmpty() && StringUtils.hasText(text)) {
            result.add(text);
        }
        return result.size() > GALLERY_LIMIT ? result.subList(0, GALLERY_LIMIT) : result;
    }

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }
}
