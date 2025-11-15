package cn.iocoder.yudao.module.yw.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberChapterDO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberChapterPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 章节进度 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface YwMemberChapterMapper extends BaseMapperX<YwMemberChapterDO> {

    default PageResult<YwMemberChapterDO> selectPage(YwMemberChapterPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YwMemberChapterDO>()
                .eqIfPresent(YwMemberChapterDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwMemberChapterDO::getChapterId, reqVO.getChapterId())
                .betweenIfPresent(YwMemberChapterDO::getLearningTime, reqVO.getLearningTime())
                .eqIfPresent(YwMemberChapterDO::getDuration, reqVO.getDuration())
                .eqIfPresent(YwMemberChapterDO::getAccomplishFlag, reqVO.getAccomplishFlag())
                .eqIfPresent(YwMemberChapterDO::getDelFlag, reqVO.getDelFlag())
                .betweenIfPresent(YwMemberChapterDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(YwMemberChapterDO::getLastTime, reqVO.getLastTime())
                .orderByDesc(YwMemberChapterDO::getId));
    }

}