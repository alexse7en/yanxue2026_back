package cn.iocoder.yudao.module.yw.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwTeacherDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.page.YwTeacherPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwTeacherSaveReqVO;

/**
 * 教师 Service 接口
 *
 * @author 芋道源码
 */
public interface YwTeacherService {

    /**
     * 创建教师
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createTeacher(@Valid YwTeacherSaveReqVO createReqVO);

    /**
     * 更新教师
     *
     * @param updateReqVO 更新信息
     */
    void updateTeacher(@Valid YwTeacherSaveReqVO updateReqVO);

    /**
     * 删除教师
     *
     * @param id 编号
     */
    void deleteTeacher(String id);

    /**
    * 批量删除教师
    *
    * @param ids 编号
    */
    void deleteTeacherListByIds(List<String> ids);

    /**
     * 获得教师
     *
     * @param id 编号
     * @return 教师
     */
    YwTeacherDO getTeacher(String id);

    /**
     * 获得教师分页
     *
     * @param pageReqVO 分页查询
     * @return 教师分页
     */
    PageResult<YwTeacherDO> getTeacherPage(YwTeacherPageReqVO pageReqVO);

}