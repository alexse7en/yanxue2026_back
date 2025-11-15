package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwMemberLevelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.ps.vo.YwMemberLevelWithAddressVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwMemberLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwMemberLevelSaveReqVO;

/**
 * 用户等级 Service 接口
 *
 * @author 芋道源码
 */
public interface YwMemberLevelService {

    /**
     * 创建用户等级
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMemberLevel(@Valid YwMemberLevelSaveReqVO createReqVO);

    /**
     * 更新用户等级
     *
     * @param updateReqVO 更新信息
     */
    void updateMemberLevel(@Valid YwMemberLevelSaveReqVO updateReqVO);

    /**
     * 删除用户等级
     *
     * @param id 编号
     */
    void deleteMemberLevel(Long id);

    /**
    * 批量删除用户等级
    *
    * @param ids 编号
    */
    void deleteMemberLevelListByIds(List<Long> ids);

    /**
     * 获得用户等级
     *
     * @param id 编号
     * @return 用户等级
     */
    YwMemberLevelDO getMemberLevel(Long id);

    /**
     * 获得用户等级分页
     *
     * @param pageReqVO 分页查询
     * @return 用户等级分页
     */
    PageResult<YwMemberLevelDO> getMemberLevelPage(YwMemberLevelPageReqVO pageReqVO);

    List<YwMemberLevelDO> selectCanAuthMemberLevel();
    YwMemberLevelWithAddressVo selectMemberLevel();
    YwMemberLevelWithAddressVo selectMemberLevelById(Long id);

    boolean updateOrInsertAuthMemberLevel(Long levelId);
}