package cn.iocoder.yudao.module.yw.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoPageReqVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoSaveReqVO;

public interface YwVipInfoService {

    PageResult<YwVipInfoRespVO> getVipInfoPage(YwVipInfoPageReqVO pageReqVO);

    YwVipInfoRespVO getVipInfo(Long id);

    void updateVipInfo(YwVipInfoSaveReqVO reqVO);
}
