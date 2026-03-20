package cn.iocoder.yudao.module.yw.convert.portal;

import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwOrgInfoDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwVipInfoDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwYanxueArticleDO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalOrgInfoRespVO;
import cn.iocoder.yudao.module.yw.vo.portal.resp.YwPortalVipInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwPortalConvert {

    YwPortalConvert INSTANCE = Mappers.getMapper(YwPortalConvert.class);

    @Mapping(source = "image", target = "picUrl")
    @Mapping(source = "summary", target = "introduction")
    @Mapping(source = "publishTime", target = "createTime")
    YwPortalArticleRespVO convert(YwYanxueArticleDO bean);

    List<YwPortalArticleRespVO> convertArticleList(List<YwYanxueArticleDO> list);

    @Mapping(source = "membershipStartDate", target = "effectiveStartDate")
    @Mapping(source = "membershipEndDate", target = "effectiveEndDate")
    @Mapping(source = "companyType", target = "unitType")
    YwPortalVipInfoRespVO convertVip(YwVipInfoDO bean);

    List<YwPortalVipInfoRespVO> convertVipList(List<YwVipInfoDO> list);

    YwPortalOrgInfoRespVO convertOrg(YwOrgInfoDO bean);

    List<YwPortalOrgInfoRespVO> convertOrgList(List<YwOrgInfoDO> list);
}
