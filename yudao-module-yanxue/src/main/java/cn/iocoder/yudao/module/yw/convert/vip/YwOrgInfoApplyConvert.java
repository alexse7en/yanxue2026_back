package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoApplyDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwOrgInfoApplyConvert {

    YwOrgInfoApplyConvert INSTANCE = Mappers.getMapper(YwOrgInfoApplyConvert.class);

    @Mapping(source = "certTutorCount", target = "fulltimeTutorCount")
    YwOrgInfoRespVO convertOrgInfo(YwOrgInfoDO bean);

    YwOrgInfoApplyRespVO convertApply(YwOrgInfoApplyDO bean);

    List<YwOrgInfoRespVO> convertOrgInfoList(List<YwOrgInfoDO> list);

    List<YwOrgInfoApplyRespVO> convertApplyList(List<YwOrgInfoApplyDO> list);

    default PageResult<YwOrgInfoApplyRespVO> convertApplyPage(PageResult<YwOrgInfoApplyDO> pageResult) {
        return new PageResult<>(convertApplyList(pageResult.getList()), pageResult.getTotal());
    }
}



