package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwCertStudentApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwCertStudentApplyConvert {

    YwCertStudentApplyConvert INSTANCE = Mappers.getMapper(YwCertStudentApplyConvert.class);

    YwCertStudentApplyRespVO convert(YwCertStudentApplyDO bean);

    List<YwCertStudentApplyRespVO> convertList(List<YwCertStudentApplyDO> list);

    default PageResult<YwCertStudentApplyRespVO> convertPage(PageResult<YwCertStudentApplyDO> pageResult) {
        return new PageResult<>(convertList(pageResult.getList()), pageResult.getTotal());
    }
}
