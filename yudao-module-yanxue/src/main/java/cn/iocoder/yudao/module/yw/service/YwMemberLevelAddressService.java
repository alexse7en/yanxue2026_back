package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberLevelAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.YwMemberLevelAddressUpdateDeliveryReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberLevelAddressPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberLevelAddressSaveReqVO;

/**
 * 用户收件地址 Service 接口
 *
 * @author 芋道源码
 */
public interface YwMemberLevelAddressService {

    /**
     * 创建用户收件地址
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMemberLevelAddress(@Valid YwMemberLevelAddressSaveReqVO createReqVO);

    /**
     * 更新用户收件地址
     *
     * @param updateReqVO 更新信息
     */
    void updateMemberLevelAddress(@Valid YwMemberLevelAddressSaveReqVO updateReqVO);

    /**
     * 删除用户收件地址
     *
     * @param id 编号
     */
    void deleteMemberLevelAddress(Long id);

    /**
    * 批量删除用户收件地址
    *
    * @param ids 编号
    */
    void deleteMemberLevelAddressListByIds(List<Long> ids);

    /**
     * 获得用户收件地址
     *
     * @param id 编号
     * @return 用户收件地址
     */
    YwMemberLevelAddressDO getMemberLevelAddress(Long id);
    // 按 memberId 查
    YwMemberLevelAddressDO getByMemberIdAndLevelId(Long memberId, Long levelId);

    /**
     * 获得用户收件地址分页
     *
     * @param pageReqVO 分页查询
     * @return 用户收件地址分页
     */
    PageResult<YwMemberLevelAddressDO> getMemberLevelAddressPage(YwMemberLevelAddressPageReqVO pageReqVO);

    void updateDeliveryByMemberIdAndLevelId(YwMemberLevelAddressUpdateDeliveryReqVO reqVO);


}
