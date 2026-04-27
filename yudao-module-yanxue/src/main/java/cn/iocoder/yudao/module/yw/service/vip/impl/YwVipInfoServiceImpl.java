package cn.iocoder.yudao.module.yw.service.vip.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.convert.vip.YwVipInfoApplyConvert;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.vip.YwVipInfoMapper;
import cn.iocoder.yudao.module.yw.service.vip.YwVipInfoService;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_NOT_EXISTS;

@Service
@Validated
public class YwVipInfoServiceImpl implements YwVipInfoService {

    @Resource
    private YwVipInfoMapper vipInfoMapper;

    @Override
    public PageResult<YwVipInfoRespVO> getVipInfoPage(YwVipInfoPageReqVO pageReqVO) {
        PageResult<YwVipInfoDO> pageResult = vipInfoMapper.selectPage(pageReqVO);
        return YwVipInfoApplyConvert.INSTANCE.convertVipInfoPage(pageResult);
    }

    @Override
    public YwVipInfoRespVO getVipInfo(Long id) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectById(id);
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        return YwVipInfoApplyConvert.INSTANCE.convertVipInfo(vipInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVipInfo(YwVipInfoSaveReqVO reqVO) {
        YwVipInfoDO vipInfo = vipInfoMapper.selectById(reqVO.getId());
        if (vipInfo == null) {
            throw exception(YW_VIPINFO_NOT_EXISTS);
        }
        mergeEditableFields(vipInfo, reqVO);
        vipInfoMapper.updateById(vipInfo);
    }

    private void mergeEditableFields(YwVipInfoDO vipInfo, YwVipInfoSaveReqVO reqVO) {
        vipInfo.setMemberNo(reqVO.getMemberNo());
        vipInfo.setCompanyName(reqVO.getCompanyName());
        vipInfo.setCompanyAddress(reqVO.getCompanyAddress());
        vipInfo.setCompanyPhone(reqVO.getCompanyPhone());
        vipInfo.setWebsite(reqVO.getWebsite());
        vipInfo.setEstablishedDate(reqVO.getEstablishedDate());
        vipInfo.setBusinessScope(reqVO.getBusinessScope());
        vipInfo.setCompanyIntro(reqVO.getCompanyIntro());
        vipInfo.setLogo(reqVO.getLogo());
        vipInfo.setCompanyType(reqVO.getCompanyType());
        vipInfo.setMemberLevel(reqVO.getMemberLevel());
        vipInfo.setContactName(reqVO.getContactName());
        vipInfo.setContactPhone(reqVO.getContactPhone());
        vipInfo.setRepName(reqVO.getRepName());
        vipInfo.setRepPosition(reqVO.getRepPosition());
        vipInfo.setRepPhone(reqVO.getRepPhone());
        vipInfo.setRepEmail(reqVO.getRepEmail());
        vipInfo.setMembershipStartDate(reqVO.getMembershipStartDate());
        vipInfo.setMembershipEndDate(reqVO.getMembershipEndDate());
        vipInfo.setHonor(reqVO.getHonor());
        vipInfo.setStar(reqVO.getStar());
        vipInfo.setGallery(reqVO.getGallery());
        vipInfo.setTokenBalance(reqVO.getTokenBalance());
    }
}
