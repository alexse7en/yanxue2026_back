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
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_AUDIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_NOT_EXISTS;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_EMPTY;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_FILE_TYPE_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_PARSE_REQUIRED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_STATUS_NOT_PENDING;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_CERT_STUDENT_APPLY_SUBMIT_STATUS_INVALID;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

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
        batch.setAuditRemark(null);
        batch.setAuditTime(null);
        batch.setAuditorId(null);

        List<YwStudentApplyDO> details;
        try {
            details = certStudentExcelParser.parse(reqVO.getFilePath());
            if (details.isEmpty()) {
                throw exception(YW_CERT_STUDENT_APPLY_PARSE_EMPTY);
            }
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
        overwriteDetails(batch.getId(), batch.getUserId(), batch.getVipinfoId(), reqVO.getDetails());
        validateTokenBalance(batch);
        batch.setApplyStatus(APPLY_STATUS_PENDING);
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
        studentApplyBatchMapper.updateById(batch);

        if (Objects.equals(reqVO.getApplyStatus(), APPLY_STATUS_APPROVED)) {
            certStudentGenerator.generate(batch.getId());
        }
    }

    private void overwriteDetails(Long batchId, Long userId, Long vipinfoId, List<YwStudentApplyDetailSaveReqVO> details) {
        if (details == null || details.isEmpty()) {
            return;
        }
        studentApplyMapper.deleteByBatchId(batchId);
        for (YwStudentApplyDetailSaveReqVO item : details) {
            YwStudentApplyDO detail = new YwStudentApplyDO();
            detail.setApplyBatchId(batchId);
            detail.setUserId(userId);
            detail.setVipinfoId(vipinfoId);
            detail.setStudentName(item.getStudentName());
            detail.setIdCard(item.getIdCard());
            detail.setSchoolName(item.getSchoolName());
            detail.setClassName(item.getClassName());
            detail.setCourseName(item.getCourseName());
            detail.setCourseHours(item.getCourseHours());
            detail.setCourseProvider(item.getCourseProvider());
            detail.setCertDate(item.getCertDate());
            detail.setStampUnit(item.getStampUnit());
            studentApplyMapper.insert(detail);
        }
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
        respVO.setDetails(YwCertStudentApplyConvert.INSTANCE.convertDetailList(details));
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
