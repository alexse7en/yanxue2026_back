package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwVipApplyAuditRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwVipApplyConvert {

    YwVipApplyConvert INSTANCE = Mappers.getMapper(YwVipApplyConvert.class);

    YwVipApplyAuditRespVO convertAudit(YwVipApplyDO bean);

    List<YwVipApplyAuditRespVO> convertAuditList(List<YwVipApplyDO> list);

    default PageResult<YwVipApplyAuditRespVO> convertAuditPage(PageResult<YwVipApplyDO> pageResult) {
        return new PageResult<>(convertAuditList(pageResult.getList()), pageResult.getTotal());
    }
}
