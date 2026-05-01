package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwYanxueArticleDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwYanxueArticleSaveReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwYanxueArticleConvert {

    YwYanxueArticleConvert INSTANCE = Mappers.getMapper(YwYanxueArticleConvert.class);

    YwYanxueArticleDO convert(YwYanxueArticleSaveReqVO bean);

    YwYanxueArticleRespVO convert(YwYanxueArticleDO bean);

    List<YwYanxueArticleRespVO> convertList(List<YwYanxueArticleDO> list);

    default PageResult<YwYanxueArticleRespVO> convertPage(PageResult<YwYanxueArticleDO> pageResult) {
        return new PageResult<>(convertList(pageResult.getList()), pageResult.getTotal());
    }
}
