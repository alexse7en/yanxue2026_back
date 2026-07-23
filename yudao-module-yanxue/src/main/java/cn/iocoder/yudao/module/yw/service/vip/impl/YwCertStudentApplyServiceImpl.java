package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.convert.vip.YwCertStudentApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyBatchDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwStudentApplyBatchMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwStudentApplyMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwCertStudentApplyService;
import cn.iocoder.yudao.module.yw.service.vip.YwVipTokenService;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyParseReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplySubmitReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_AUDIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_DETAIL_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_EMPTY;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_FILE_TYPE_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_REQUIRED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_STATUS_NOT_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_SUBMIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.service.vip.impl.YwCertStudentGenerator.GENERATE_STATUS_NONE;
import static cn.iocoder.yudao.module.yw.service.vip.impl.YwCertStudentGenerator.GENERATE_STATUS_RUNNING;

@Service
@Validated
public class YwCertStudentApplyServiceImpl implements YwCertStudentApplyService {

    private static final Integer APPLY_STATUS_DRAFT = 0;
    private static final Integer APPLY_STATUS_PENDING = 1;
    private static final Integer APPLY_STATUS_APPROVED = 2;
    private static final Integer APPLY_STATUS_REJECTED = 3;
    private static final Integer PARSE_STATUS_SUCCESS = 1;
    private static final Integer PARSE_STATUS_FAIL = 2;

    @Resource
    private YwStudentApplyBatchMapper studentApplyBatchMapper;
    @Resource
    private YwStudentApplyMapper studentApplyMapper;
    @Resource
    private YwVipInfoMapper vipInfoMapper;
    @Resource
    private YwCertStudentExcelParser certStudentExcelParser;
    @Resource
    private YwCertStudentGenerator certStudentGenerator;
    @Resource
    private YwVipTokenService vipTokenService;

    @Override
    public PageResult<YwCertStudentApplyRespVO> getApplyPageMy(YwCertStudentApplyPageReqVO reqVO) {
        PageResult<YwStudentApplyBatchDO> pageResult = studentApplyBatchMapper.selectPageMy(getLoginUserId(), reqVO);
        return YwCertStudentApplyConvert.INSTANCE.convertPage(pageResult);
    }

    @Override
    public YwCertStudentApplyRespVO getApply(Long id) {
        return buildResp(requireOwnedBatch(id));
    }

    @Override
    public YwCertStudentApplyRespVO getApplyForAudit(Long id) {
        YwStudentApplyBatchDO batch = studentApplyBatchMapper.selectById(id);
        if (batch == null) {
            throw exception(YW_CERT_STUDENT_APPLY_NOT_EXISTS);
        }
        return buildResp(batch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YwCertStudentApplyRespVO parseApply(YwCertStudentApplyParseReqVO reqVO) {
        validateExcelFileType(reqVO.getFileType(), reqVO.getFilePath());
        Long userId = getLoginUserId();
        YwVipInfoDO vipInfo = requireVipInfo(userId);
        YwStudentApplyBatchDO batch = studentApplyBatchMapper.selectLatestDraftByUserId(userId);
        boolean isNewBatch = batch == null;
        if (isNewBatch) {
            batch = new YwStudentApplyBatchDO();
            batch.setUserId(userId);
            batch.setVipinfoId(vipInfo.getId());
            batch.setApplyNo(buildApplyNo());
            batch.setApplyStatus(APPLY_STATUS_DRAFT);
        }
        batch.setVipinfoId(vipInfo.getId());
        batch.setUploadFilePath(reqVO.getFilePath());
        batch.setFileType(resolveFileType(reqVO.getFileType(), reqVO.getFilePath()));
        batch.setDownloadUrl(null);
        batch.setGenerateStatus(GENERATE_STATUS_NONE);
        batch.setGenerateError(null);
        batch.setAuditRemark(null);
        batch.setAuditTime(null);
        batch.setAuditorId(null);

        List<YwStudentApplyDO> details;
        try {
            details = certStudentExcelParser.parse(reqVO.getFilePath());
            if (details.isEmpty()) {
                throw exception(YW_CERT_STUDENT_APPLY_PARSE_EMPTY);
            }
            normalizeParsedDetails(details, vipInfo.getCompanyName());
        } catch (ServiceException ex) {
            batch.setParseStatus(PARSE_STATUS_FAIL);
            batch.setParseError(ex.getMessage());
            batch.setParseCount(0);
            saveParsedBatch(batch, isNewBatch);
            if (!isNewBatch) {
                studentApplyMapper.deleteByBatchId(batch.getId());
            }
            return buildResp(batch);
        } catch (Exception ex) {
            batch.setParseStatus(PARSE_STATUS_FAIL);
            batch.setParseError(limitMsg(ex.getMessage()));
            batch.setParseCount(0);
            saveParsedBatch(batch, isNewBatch);
            if (!isNewBatch) {
                studentApplyMapper.deleteByBatchId(batch.getId());
            }
            return buildResp(batch);
        }

        batch.setParseStatus(PARSE_STATUS_SUCCESS);
        batch.setParseError(null);
        batch.setParseCount(details.size());
        saveParsedBatch(batch, isNewBatch);
        studentApplyMapper.deleteByBatchId(batch.getId());

        for (YwStudentApplyDO detail : details) {
            detail.setApplyBatchId(batch.getId());
            detail.setUserId(userId);
            detail.setVipinfoId(vipInfo.getId());
            studentApplyMapper.insert(detail);
        }
        return buildResp(batch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitApply(YwCertStudentApplySubmitReqVO reqVO) {
        YwStudentApplyBatchDO batch = requireOwnedBatch(reqVO.getId());
        if (!Objects.equals(batch.getParseStatus(), PARSE_STATUS_SUCCESS)) {
            throw exception(YW_CERT_STUDENT_APPLY_PARSE_REQUIRED);
        }
        if (Objects.equals(batch.getApplyStatus(), APPLY_STATUS_PENDING)) {
            throw exception(YW_CERT_STUDENT_APPLY_SUBMIT_STATUS_INVALID);
        }
        YwVipInfoDO vipInfo = requireVipInfo(batch.getUserId());
        if (reqVO.getDetails() != null && !reqVO.getDetails().isEmpty()) {
            validateSubmitDetails(reqVO.getDetails());
            overwriteDetails(batch.getId(), batch.getUserId(), batch.getVipinfoId(),
                    vipInfo.getCompanyName(), reqVO.getDetails());
        } else {
            validateParsedDetails(studentApplyMapper.selectListByBatchId(batch.getId()));
        }
        validateTokenBalance(batch);
        batch.setApplyStatus(APPLY_STATUS_PENDING);
        batch.setDownloadUrl(null);
        batch.setGenerateStatus(GENERATE_STATUS_NONE);
        batch.setGenerateError(null);
        batch.setAuditRemark(null);
        batch.setAuditTime(null);
        batch.setAuditorId(null);
        studentApplyBatchMapper.updateById(batch);
        return batch.getId();
    }

    @Override
    public PageResult<YwCertStudentApplyRespVO> getApplyAuditPage(YwCertStudentApplyAuditPageReqVO reqVO) {
        PageResult<YwStudentApplyBatchDO> pageResult = studentApplyBatchMapper.selectAuditPage(reqVO);
        return YwCertStudentApplyConvert.INSTANCE.convertPage(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditApply(YwCertStudentApplyAuditReqVO reqVO) {
        YwStudentApplyBatchDO batch = studentApplyBatchMapper.selectById(reqVO.getId());
        if (batch == null) {
            throw exception(YW_CERT_STUDENT_APPLY_NOT_EXISTS);
        }
        if (!Objects.equals(batch.getApplyStatus(), APPLY_STATUS_PENDING)) {
            throw exception(YW_CERT_STUDENT_APPLY_STATUS_NOT_PENDING);
        }
        if (!Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)
                && !Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_REJECTED)) {
            throw exception(YW_CERT_STUDENT_APPLY_AUDIT_STATUS_INVALID);
        }
        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            validateTokenBalance(batch);
        }
        batch.setApplyStatus(reqVO.getApplyStatus());
        batch.setAuditRemark(reqVO.getAuditRemark());
        batch.setAuditTime(LocalDateTime.now());
        batch.setAuditorId(SecurityFrameworkUtils.getLoginUserId());
        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            batch.setDownloadUrl(null);
            batch.setGenerateStatus(GENERATE_STATUS_RUNNING);
            batch.setGenerateError(null);
        }
        studentApplyBatchMapper.updateById(batch);

        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            runAfterCommit(() -> certStudentGenerator.generateAsync(batch.getId()));
        }
    }

    private void runAfterCommit(Runnable runnable) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            runnable.run();
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                runnable.run();
            }
        });
    }

    private void overwriteDetails(Long batchId, Long userId, Long vipinfoId, String courseProvider,
                                  List<YwStudentApplyDetailSaveReqVO> details) {
        if (details == null || details.isEmpty()) {
            return;
        }
        studentApplyMapper.deleteByBatchId(batchId);
        for (YwStudentApplyDetailSaveReqVO item : details) {
            YwStudentApplyDO detail = new YwStudentApplyDO();
            detail.setApplyBatchId(batchId);
            detail.setUserId(userId);
            detail.setVipinfoId(vipinfoId);
            detail.setStudentName(trimToNull(item.getStudentName()));
            detail.setIdCard(normalizeIdCard(item.getIdCard()));
            detail.setSchoolName(trimToNull(item.getSchoolName()));
            detail.setClassName(trimToNull(item.getClassName()));
            detail.setCourseName(trimToNull(item.getCourseName()));
            detail.setCourseHours(trimToNull(item.getCourseHours()));
            detail.setCourseProvider(trimToNull(courseProvider));
            detail.setCertDate(item.getCertDate());
            detail.setCourseDate(item.getCourseDate());
            detail.setStampDate(item.getStampDate());
            detail.setStampUnit(trimToNull(item.getStampUnit()));
            studentApplyMapper.insert(detail);
        }
    }

    private void validateParsedDetails(List<YwStudentApplyDO> details) {
        if (details == null || details.isEmpty()) {
            throw exception(YW_CERT_STUDENT_APPLY_PARSE_EMPTY);
        }
        List<String> validationErrors = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            YwStudentApplyDO detail = details.get(i);
            appendValidationErrors(validationErrors, i, collectDetailFieldErrors(
                    detail.getStudentName(), detail.getIdCard(), detail.getCourseName(), detail.getCourseHours(),
                    detail.getCourseDate(), detail.getStampDate()));
        }
        throwIfDetailInvalid(validationErrors);
    }

    private void validateSubmitDetails(List<YwStudentApplyDetailSaveReqVO> details) {
        List<String> validationErrors = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            YwStudentApplyDetailSaveReqVO detail = details.get(i);
            appendValidationErrors(validationErrors, i, collectDetailFieldErrors(
                    detail.getStudentName(), detail.getIdCard(), detail.getCourseName(), detail.getCourseHours(),
                    detail.getCourseDate(), detail.getStampDate()));
        }
        throwIfDetailInvalid(validationErrors);
    }

    private void normalizeParsedDetails(List<YwStudentApplyDO> details, String courseProvider) {
        for (YwStudentApplyDO detail : details) {
            detail.setStudentName(trimToEmpty(detail.getStudentName()));
            detail.setIdCard(normalizeIdCard(detail.getIdCard()));
            detail.setSchoolName(trimToNull(detail.getSchoolName()));
            detail.setClassName(trimToNull(detail.getClassName()));
            detail.setCourseName(trimToNull(detail.getCourseName()));
            detail.setCourseHours(trimToNull(detail.getCourseHours()));
            detail.setCourseProvider(trimToNull(courseProvider));
            detail.setStampUnit(trimToNull(detail.getStampUnit()));
        }
    }

    private Map<String, String> collectDetailFieldErrors(String studentName, String idCard,
                                                         String courseName, String courseHours,
                                                         LocalDate courseDate, LocalDate stampDate) {
        Map<String, String> errors = new LinkedHashMap<>();
        putRequiredTextError(errors, "studentName", studentName, "学生姓名不能为空");
        if (!StringUtils.hasText(idCard)) {
            errors.put("idCard", "身份证不能为空");
        } else if (!isValidIdCard(idCard)) {
            errors.put("idCard", "身份证号码格式不正确");
        }
        putRequiredTextError(errors, "courseName", courseName, "课程名称不能为空");
        putRequiredTextError(errors, "courseHours", courseHours, "课时不能为空");
        if (courseDate == null) {
            errors.put("courseDate", "课程日期不能为空");
        }
        if (stampDate == null) {
            errors.put("stampDate", "盖章日期不能为空");
        }
        return errors;
    }

    private void putRequiredTextError(Map<String, String> errors, String field, String value, String message) {
        if (!StringUtils.hasText(value)) {
            errors.put(field, message);
        }
    }

    private void appendValidationErrors(List<String> validationErrors, int rowIndex,
                                        Map<String, String> fieldErrors) {
        for (String message : fieldErrors.values()) {
            validationErrors.add("第" + (rowIndex + 1) + "条：" + message);
        }
    }

    private void throwIfDetailInvalid(List<String> validationErrors) {
        if (validationErrors.isEmpty()) {
            return;
        }
        int displayCount = Math.min(validationErrors.size(), 10);
        String message = String.join("；", validationErrors.subList(0, displayCount));
        if (validationErrors.size() > displayCount) {
            message += "；另有" + (validationErrors.size() - displayCount) + "处错误";
        }
        throwDetailInvalid(message);
    }

    private boolean isValidIdCard(String idCard) {
        String text = idCard.trim().toUpperCase();
        if (!text.matches("\\d{17}[0-9X]")) {
            return false;
        }
        try {
            LocalDate birthday = LocalDate.of(Integer.parseInt(text.substring(6, 10)),
                    Integer.parseInt(text.substring(10, 12)), Integer.parseInt(text.substring(12, 14)));
            if (birthday.isBefore(LocalDate.of(1900, 1, 1)) || birthday.isAfter(LocalDate.now())) {
                return false;
            }
        } catch (DateTimeException | NumberFormatException ex) {
            return false;
        }
        int[] weights = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] checks = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (text.charAt(i) - '0') * weights[i];
        }
        return checks[sum % 11] == text.charAt(17);
    }

    private String trimToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private String trimToEmpty(String value) {
        return StringUtils.hasText(value) ? value.trim() : "";
    }

    private String normalizeIdCard(String idCard) {
        String value = trimToNull(idCard);
        return value == null ? null : value.toUpperCase();
    }

    private void throwDetailInvalid(String message) {
        throw new ServiceException(YW_CERT_STUDENT_APPLY_DETAIL_INVALID.getCode(), message);
    }

    private void saveParsedBatch(YwStudentApplyBatchDO batch, boolean isNewBatch) {
        if (isNewBatch) {
            studentApplyBatchMapper.insert(batch);
            return;
        }
        studentApplyBatchMapper.updateById(batch);
    }

    private YwStudentApplyBatchDO requireOwnedBatch(Long id) {
        YwStudentApplyBatchDO batch = studentApplyBatchMapper.selectById(id);
        if (batch == null || !Objects.equals(batch.getUserId(), getLoginUserId())) {
            throw exception(YW_CERT_STUDENT_APPLY_NOT_EXISTS);
        }
        return batch;
    }

    private YwCertStudentApplyRespVO buildResp(YwStudentApplyBatchDO batch) {
        YwCertStudentApplyRespVO respVO = YwCertStudentApplyConvert.INSTANCE.convert(batch);
        List<YwStudentApplyDO> details = studentApplyMapper.selectListByBatchId(batch.getId());
        List<YwStudentApplyDetailRespVO> detailRespList =
                YwCertStudentApplyConvert.INSTANCE.convertDetailList(details);
        int validationErrorCount = 0;
        for (int i = 0; i < details.size(); i++) {
            YwStudentApplyDO detail = details.get(i);
            Map<String, String> fieldErrors = collectDetailFieldErrors(
                    detail.getStudentName(), detail.getIdCard(), detail.getCourseName(), detail.getCourseHours(),
                    detail.getCourseDate(), detail.getStampDate());
            detailRespList.get(i).setValid(fieldErrors.isEmpty());
            detailRespList.get(i).setFieldErrors(fieldErrors);
            validationErrorCount += fieldErrors.size();
        }
        respVO.setDetails(detailRespList);
        respVO.setValidationErrorCount(validationErrorCount);
        if (Objects.equals(batch.getParseStatus(), PARSE_STATUS_SUCCESS) && validationErrorCount > 0) {
            respVO.setParseError("解析完成，共发现" + validationErrorCount + "处内容错误，请根据红色提示修改");
        }
        return respVO;
    }

    private YwVipInfoDO requireVipInfo(Long userId) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectByUserId(userId);
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        return vipInfo;
    }

    private void validateTokenBalance(YwStudentApplyBatchDO batch) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectById(batch.getVipinfoId());
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        int detailCount = studentApplyMapper.selectListByBatchId(batch.getId()).size();
        vipTokenService.validateGenerateToken(vipInfo, detailCount);
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
