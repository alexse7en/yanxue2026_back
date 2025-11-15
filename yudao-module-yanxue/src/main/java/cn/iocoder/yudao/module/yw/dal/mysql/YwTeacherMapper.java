package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwTeacherDO;
import cn.iocoder.yudao.module.yw.vo.page.YwTeacherPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 教师 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwTeacherMapper extends BaseMapperX<YwTeacherDO> {

    default PageResult<YwTeacherDO> selectPage(YwTeacherPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwTeacherDO>()
                .likeIfPresent(YwTeacherDO::getName, reqVO.getName())
                .likeIfPresent(YwTeacherDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(YwTeacherDO::getAvatar, reqVO.getAvatar())
                .betweenIfPresent(YwTeacherDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(YwTeacherDO::getId));
    }

}