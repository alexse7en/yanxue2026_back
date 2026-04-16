package cn.iocoder.yudao.module.yw.convert.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyBatchDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.vip.YwStudentApplyDO;
import cn.iocoder.yudao.module.yw.vo.vip.YwCertStudentApplyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwStudentApplyDetailRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface YwCertStudentApplyConvert {

    YwCertStudentApplyConvert INSTANCE = Mappers.getMapper(YwCertStudentApplyConvert.class);

    YwCertStudentApplyRespVO convert(YwStudentApplyBatchDO bean);

    YwStudentApplyDetailRespVO convertDetail(YwStudentApplyDO bean);

    List<YwCertStudentApplyRespVO> convertList(List<YwStudentApplyBatchDO> list);

    List<YwStudentApplyDetailRespVO> convertDetailList(List<YwStudentApplyDO> list);

    default PageResult<YwCertStudentApplyRespVO> convertPage(PageResult<YwStudentApplyBatchDO> pageResult) {
        return new PageResult<>(convertList(pageResult.getList()), pageResult.getTotal());
    }
}
