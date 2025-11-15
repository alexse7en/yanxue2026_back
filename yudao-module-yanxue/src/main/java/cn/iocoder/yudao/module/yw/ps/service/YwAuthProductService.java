package cn.iocoder.yudao.module.yw.ps.service;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthProductPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthProductRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthProductSaveReqVO;

/**
 * 作品申请 Service 接口
 *
 * @author 芋道源码
 */
public interface YwAuthProductService {

    /**
     * 创建作品申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAuthProduct(@Valid YwAuthProductSaveReqVO createReqVO);

    /**
     * 更新作品申请
     *
     * @param updateReqVO 更新信息
     */
    void updateAuthProduct(@Valid YwAuthProductSaveReqVO updateReqVO);
    int submitProduct(YwAuthProductDO updateReqVO);
    int updateAuthProductStatus(YwAuthProductDO authProductDO);

    /**
     * 删除作品申请
     *
     * @param id 编号
     */
    void deleteAuthProduct(Long id);

    /**
    * 批量删除作品申请
    *
    * @param ids 编号
    */
    void deleteAuthProductListByIds(List<Long> ids);

    /**
     * 获得作品申请
     *
     * @param id 编号
     * @return 作品申请
     */
    YwAuthProductDO getAuthProduct(Long id);

    /**
     * 获得作品申请分页
     *
     * @param pageReqVO 分页查询
     * @return 作品申请分页
     */
    PageResult<YwAuthProductRespVO> getAuthProductPage(YwAuthProductPageReqVO pageReqVO);
    PageResult<YwAuthProductDO> my(YwAuthProductPageReqVO pageReqVO);

    List<YwAuthProductRespVO> getNeedSpuList();

}
