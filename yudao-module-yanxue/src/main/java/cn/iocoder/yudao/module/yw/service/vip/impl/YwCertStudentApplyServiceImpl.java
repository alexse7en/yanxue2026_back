package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwCertStudentApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwCertStudentApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwCertStudentMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwCertStudentApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplySubmitReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_EMPTY;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_FILE_TYPE_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_REQUIRED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_SUBMIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_TEMPLATE_NOT_CONFIG;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

@Service
@Validated
public class YwCertStudentApplyServiceImpl implements YwCertStudentApplyService {

    private static final Integer PARSE_STATUS_SUCCESS = 1;
    private static final Integer PARSE_STATUS_FAIL = 2;
    private static final Integer CERT_STATUS_PENDING = 0;
    private static final Integer CERT_STATUS_GENERATING = 1;
    private static final Integer CERT_STATUS_SUCCESS = 2;
    private static final Integer CERT_STATUS_FAIL = 3;
    private static final Integer DETAIL_STATUS_PENDING = 0;

    @Resource
    private YwCertStudentApplyMapper certStudentApplyMapper;
    @Resource
    private YwCertStudentMapper certStudentMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private YwCertStudentExcelParser certStudentExcelParser;
    @Resource
    private YwCertStudentGenerator certStudentGenerator;

    @Override
    public PageResult<YwCertStudentApplyRespVO> getApplyPageMy(YwCertStudentApplyPageReqVO reqVO) {
        return YwCertStudentApplyConvert.INSTANCE.convertPage(certStudentApplyMapper.selectPageMy(getLoginUserId(), reqVO));
    }

    @Override
    public YwCertStudentApplyRespVO getApply(Long id) {
        return YwCertStudentApplyConvert.INSTANCE.convert(requireOwnedApply(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YwCertStudentApplyRespVO parseApply(YwCertStudentApplyParseReqVO reqVO) {
        validateExcelFileType(reqVO.getFileType(), reqVO.getFilePath());
        Long userId = getLoginUserId();
        YwVipInfoDO vipInfo = requireVipInfo(userId);
        List<YwCertStudentDO> details;

        YwCertStudentApplyDO apply = new YwCertStudentApplyDO();
        apply.setUserId(userId);
        apply.setVipinfoId(vipInfo.getId());
        apply.setApplyNo(buildApplyNo());
        apply.setFilePath(reqVO.getFilePath());
        apply.setFileType(resolveFileType(reqVO.getFileType(), reqVO.getFilePath()));
        apply.setCertStatus(CERT_STATUS_PENDING);
        apply.setCertName("研学旅行实践活动证书");

        try {
            details = certStudentExcelParser.parse(reqVO.getFilePath());
            if (details.isEmpty()) {
                throw exception(YW_CERT_STUDENT_APPLY_PARSE_EMPTY);
            }
        } catch (ServiceException ex) {
            apply.setParseStatus(PARSE_STATUS_FAIL);
            apply.setParseError(ex.getMessage());
            apply.setParseCount(0);
            certStudentApplyMapper.insert(apply);
            return YwCertStudentApplyConvert.INSTANCE.convert(apply);
        } catch (Exception ex) {
            apply.setParseStatus(PARSE_STATUS_FAIL);
            apply.setParseError(limitMsg(ex.getMessage()));
            apply.setParseCount(0);
            certStudentApplyMapper.insert(apply);
            return YwCertStudentApplyConvert.INSTANCE.convert(apply);
        }

        apply.setParseStatus(PARSE_STATUS_SUCCESS);
        apply.setParseError(null);
        apply.setParseCount(details.size());
        certStudentApplyMapper.insert(apply);

        certStudentMapper.deleteByUserAndFilePath(userId, reqVO.getFilePath());
        for (YwCertStudentDO detail : details) {
            detail.setUserId(userId);
            detail.setVipinfoId(vipInfo.getId());
            detail.setUploadFilePath(reqVO.getFilePath());
            detail.setStatus(DETAIL_STATUS_PENDING);
            certStudentMapper.insert(detail);
        }
        return YwCertStudentApplyConvert.INSTANCE.convert(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitApply(YwCertStudentApplySubmitReqVO reqVO) {
        if (!certStudentGenerator.hasConfig()) {
            throw exception(YW_CERT_STUDENT_APPLY_TEMPLATE_NOT_CONFIG);
        }
        Long userId = getLoginUserId();
        YwCertStudentApplyDO apply = requireOwnedApply(reqVO.getId());
        if (!Objects.equals(apply.getParseStatus(), PARSE_STATUS_SUCCESS)) {
            throw exception(YW_CERT_STUDENT_APPLY_PARSE_REQUIRED);
        }
        if (Objects.equals(apply.getCertStatus(), CERT_STATUS_GENERATING) || Objects.equals(apply.getCertStatus(), CERT_STATUS_SUCCESS)) {
            throw exception(YW_CERT_STUDENT_APPLY_SUBMIT_STATUS_INVALID);
        }

        List<YwCertStudentDO> details = certStudentMapper.selectListByUserAndFilePath(userId, apply.getFilePath());
        if (details.isEmpty()) {
            throw exception(YW_CERT_STUDENT_APPLY_PARSE_EMPTY);
        }
        YwVipInfoDO vipInfo = requireVipInfo(userId);
        BigDecimal needToken = BigDecimal.valueOf(details.size());
        BigDecimal balance = vipInfo.getTokenBalance() == null ? BigDecimal.ZERO : vipInfo.getTokenBalance();
        if (balance.compareTo(needToken) < 0) {
            throw exception(YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH);
        }
        vipInfo.setTokenBalance(balance.subtract(needToken));
        vipInfoMapper.updateById(vipInfo);

        apply.setCertStatus(CERT_STATUS_GENERATING);
        apply.setParseError(null);
        apply.setFinishTime(null);
        apply.setCertUrl(null);
        apply.setDownloadUrl(null);
        certStudentApplyMapper.updateById(apply);
        certStudentGenerator.generateAsync(apply.getId());
        return apply.getId();
    }

    private YwCertStudentApplyDO requireOwnedApply(Long id) {
        YwCertStudentApplyDO apply = certStudentApplyMapper.selectById(id);
        if (apply == null || !Objects.equals(apply.getUserId(), getLoginUserId())) {
            throw exception(YW_CERT_STUDENT_APPLY_NOT_EXISTS);
        }
        return apply;
    }

    private YwVipInfoDO requireVipInfo(Long userId) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(userId);
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        return vipInfo;
    }

    private void validateExcelFileType(String fileType, String filePath) {
        String resolvedType = resolveFileType(fileType, filePath);
        if (!"xls".equalsIgnoreCase(resolvedType) && !"xlsx".equalsIgnoreCase(resolvedType)) {
            throw exception(YW_CERT_STUDENT_APPLY_PARSE_FILE_TYPE_INVALID);
        }
    }

    private String resolveFileType(String fileType, String filePath) {
        if (StringUtils.hasText(fileType)) {
            return fileType.trim().toLowerCase();
        }
        int index = filePath.lastIndexOf('.');
        if (index < 0 || index == filePath.length() - 1) {
            return "";
        }
        return filePath.substring(index + 1).toLowerCase();
    }

    private String buildApplyNo() {
        return "CERTAPPLY" + System.currentTimeMillis();
    }

    private String limitMsg(String msg) {
        if (!StringUtils.hasText(msg)) {
            return "文件解析失败";
        }
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
    }

    private Long getLoginUserId() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (userId == null) {
            throw new ServiceException(401, "未登录");
        }
        return userId;
    }
}
