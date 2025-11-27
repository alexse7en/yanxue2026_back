package cn.iocoder.yudao.module.yw.service;


import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwStudyBaseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.page.YwStudyBasePageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwStudyBaseSaveReqVO;

/**
 * 研学基地/营地 Service 接口
 *
 * @author 科协超级管理员
 */
public interface YwStudyBaseService {

    /**
     * 创建研学基地/营地
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStudyBase(@Valid YwStudyBaseSaveReqVO createReqVO);

    /**
     * 更新研学基地/营地
     *
     * @param updateReqVO 更新信息
     */
    void updateStudyBase(@Valid YwStudyBaseSaveReqVO updateReqVO);

    /**
     * 删除研学基地/营地
     *
     * @param id 编号
     */
    void deleteStudyBase(Long id);

    /**
     * 批量删除研学基地/营地
     *
     * @param ids 编号
     */
    void deleteStudyBaseListByIds(List<Long> ids);

    /**
     * 获得研学基地/营地
     *
     * @param id 编号
     * @return 研学基地/营地
     */
    YwStudyBaseDO getStudyBase(Long id);

    /**
     * 获得研学基地/营地分页
     *
     * @param pageReqVO 分页查询
     * @return 研学基地/营地分页
     */
    PageResult<YwStudyBaseDO> getStudyBasePage(YwStudyBasePageReqVO pageReqVO);

    // ========= 新增：给前台地图用的查询 =========

    /**
     * 获取用于地图展示的基地列表
     *
     * 约定：
     * - 只返回启用状态（status = 1）
     * - 且坐标不为空（xPercent / yPercent 非空）
     *
     * @param city 城市（可选）
     * @param themeType 主题类型（可选）
     * @param isRecommend 是否推荐（可选）
     * @return 符合条件的基地列表
     */
    List<YwStudyBaseDO> getStudyBaseListForMap(String city, String themeType, Boolean isRecommend);

}