package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.vo.*;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthLevelRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthLevelSaveReqVO;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthLevelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 评审申请 Service 接口
 *
 * @author 芋道源码
 */
public interface YwAuthLevelService {

    /**
     * 创建评审申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createYwAuthLevel(@Valid YwAuthLevelSaveReqVO createReqVO);

    /**
     * 更新评审申请
     *
     * @param updateReqVO 更新信息
     */
    void updateYwAuthLevel(@Valid YwAuthLevelSaveReqVO updateReqVO);

    /**
     * 删除评审申请
     *
     * @param id 编号
     */
    void deleteYwAuthLevel(Long id);

    /**
    * 批量删除评审申请
    *
    * @param ids 编号
    */
    void deleteYwAuthLevelListByIds(List<Long> ids);

    /**
     * 获得评审申请
     *
     * @param id 编号
     * @return 评审申请
     */
    YwAuthLevelDO getYwAuthLevel(Long id);

    /**
     * 获得评审申请分页
     *
     * @param pageReqVO 分页查询
     * @return 评审申请分页
     */
    PageResult<YwAuthLevelRespVO> getYwAuthLevelPage(YwAuthLevelPageReqVO pageReqVO);
    List<YwAuthLevelDO> my(Long memberId);

    YwAuthLevelTotalVo beginAuth(Long levelId);
    YwAuthLevelMemberVo updateAuth(YwAuthLevelMemberVo authLevelMemberVo);
    YwAuthLevelTotalVo updateAuthTotal(YwAuthLevelTotalVo totalVo);

    boolean submitAuth(Long authId);

    boolean updateAuthTeacher(YwAuthLevelMemberVo authLevelMemberVo);

    List<YwAuthLevelForTeacherVo> getTeacherJob(String teacherStatus);

    YwAuthLevelMemberVo getAuthForTeacherComment(YwAuthLevelMemberVo authLevelMemberVo);
    YwAuthLevelTotalVo updateAuthComment(YwAuthLevelMemberVo authLevelMemberVo);

    public YwAuthLevelTotalVo getAuthTotal(YwAuthLevelRequestVo authLevelRequestVo);
}