package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.service.YwMemberLevelAddressService;
import cn.iocoder.yudao.module.yw.vo.YwMemberLevelAddressUpdateDeliveryReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberLevelAddressPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberLevelAddressSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberLevelAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwMemberLevelAddressMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 用户收件地址 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwMemberLevelAddressServiceImpl implements YwMemberLevelAddressService {

    @Resource
    private YwMemberLevelAddressMapper memberLevelAddressMapper;

    @Override
    public Long createMemberLevelAddress(YwMemberLevelAddressSaveReqVO createReqVO) {
        // 插入
        YwMemberLevelAddressDO memberLevelAddress = BeanUtils.toBean(createReqVO, YwMemberLevelAddressDO.class);
        memberLevelAddressMapper.insert(memberLevelAddress);

        // 返回
        return memberLevelAddress.getId();
    }

    @Override
    public void updateMemberLevelAddress(YwMemberLevelAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateMemberLevelAddressExists(updateReqVO.getId());
        // 更新
        YwMemberLevelAddressDO updateObj = BeanUtils.toBean(updateReqVO, YwMemberLevelAddressDO.class);
        memberLevelAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteMemberLevelAddress(Long id) {
        // 校验存在
        validateMemberLevelAddressExists(id);
        // 删除
        memberLevelAddressMapper.deleteById(id);
    }

    @Override
        public void deleteMemberLevelAddressListByIds(List<Long> ids) {
        // 删除
        memberLevelAddressMapper.deleteByIds(ids);
        }


    private void validateMemberLevelAddressExists(Long id) {
        if (memberLevelAddressMapper.selectById(id) == null) {
            throw exception(MEMBER_LEVEL_ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public YwMemberLevelAddressDO getMemberLevelAddress(Long id) {
        return memberLevelAddressMapper.selectById(id);
    }
    @Override
    public YwMemberLevelAddressDO getByMemberIdAndLevelId(Long memberId,Long levelId) {
        return memberLevelAddressMapper.selectByMemberIdAndLevelId(memberId,levelId);
    }

    @Override
    public void updateDeliveryByMemberIdAndLevelId(YwMemberLevelAddressUpdateDeliveryReqVO reqVO) {
        YwMemberLevelAddressDO exists = memberLevelAddressMapper.selectByMemberIdAndLevelId(reqVO.getMemberId(), reqVO.getLevelId());
        if (exists == null) {
            throw new IllegalArgumentException("收件地址不存在，memberId=" + reqVO.getMemberId() + ", levelId=" + reqVO.getLevelId());
        }
        memberLevelAddressMapper.updateDeliveryByMemberIdAndLevelId(reqVO.getMemberId(), reqVO.getLevelId(),
                reqVO.getCompany(), reqVO.getDeliveryNo());
    }

    @Override
    public PageResult<YwMemberLevelAddressDO> getMemberLevelAddressPage(YwMemberLevelAddressPageReqVO pageReqVO) {
        return memberLevelAddressMapper.selectPage(pageReqVO);
    }

}
