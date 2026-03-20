package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwVipInfoApplyConvert {

    YwVipInfoApplyConvert INSTANCE = Mappers.getMapper(YwVipInfoApplyConvert.class);

    YwVipInfoRespVO convertVipInfo(YwVipInfoDO bean);

    YwVipInfoApplyRespVO convertApply(YwVipInfoApplyDO bean);

    List<YwVipInfoApplyRespVO> convertApplyList(List<YwVipInfoApplyDO> list);

    default PageResult<YwVipInfoApplyRespVO> convertApplyPage(PageResult<YwVipInfoApplyDO> pageResult) {
        return new PageResult<>(convertApplyList(pageResult.getList()), pageResult.getTotal());
    }
}
