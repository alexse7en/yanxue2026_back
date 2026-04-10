package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgApplyRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwOrgApplyConvert {

    YwOrgApplyConvert INSTANCE = Mappers.getMapper(YwOrgApplyConvert.class);

    YwOrgApplyRespVO convert(YwOrgApplyDO bean);

    List<YwOrgApplyRespVO> convertList(List<YwOrgApplyDO> list);

    default PageResult<YwOrgApplyRespVO> convertPage(PageResult<YwOrgApplyDO> pageResult) {
        return new PageResult<>(convertList(pageResult.getList()), pageResult.getTotal());
    }
}

