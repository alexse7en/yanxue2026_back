package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoSaveReqVO;

public interface YwOrgInfoService {

    PageResult<YwOrgInfoRespVO> getOrgInfoPage(YwOrgInfoPageReqVO pageReqVO);

    YwOrgInfoRespVO getOrgInfo(Long id);

    void updateOrgInfo(YwOrgInfoSaveReqVO reqVO);
}
