// cn/iocoder/yudao/module/yw/ps/dal/mysql/YwAuthProductMapper.java
package cn.iocoder.yudao.module.yw.ps.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthProductDO;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthProductPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthProductRespVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.List;

@Mapper
public interface YwAuthProductMapper extends BaseMapperX<YwAuthProductDO> {

    default PageResult<YwAuthProductDO> selectPage(YwAuthProductPageReqVO reqVO) {
        return selectPage(reqVO, buildWrapper(reqVO, null));
    }

    /** 新增：支持传入一批 memberIds 做过滤（用于“姓名→ID”预筛选） */
    default PageResult<YwAuthProductDO> selectPage(YwAuthProductPageReqVO reqVO, Collection<Long> memberIds) {
        return selectPage(reqVO, buildWrapper(reqVO, memberIds));
    }

    default LambdaQueryWrapper<YwAuthProductDO> buildWrapper(YwAuthProductPageReqVO reqVO, Collection<Long> memberIds) {
        return new LambdaQueryWrapperX<YwAuthProductDO>()
                .likeIfPresent(YwAuthProductDO::getName, reqVO.getName())
                .betweenIfPresent(YwAuthProductDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(YwAuthProductDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(YwAuthProductDO::getStatus, reqVO.getStatus())
                .inIfPresent(YwAuthProductDO::getMemberId, memberIds)
                .orderByDesc(YwAuthProductDO::getId);
    }

    @Update("UPDATE yw_auth_product SET status='1' WHERE id=#{id}")
    int submitProduct(@Param("id") Long id);

    /** 修正：第三个参数的 @Param 名称要叫 status（之前错误写成了 id） */
    @Update("UPDATE yw_auth_product SET status=#{status}, updator=#{userId} WHERE id=#{id}")
    int updateAuthProductStatus(@Param("id") Long id,
                                @Param("userId") Long userId,
                                @Param("status") String status);

    /** 修正：第二个参数是 articleId，不是 userId；SQL 里也要用 article_id=#{articleId} */
    @Update("UPDATE yw_auth_product SET article_id=#{articleId}, updator=#{userId} WHERE id=#{id}")
    int updateAuthProductArticle(@Param("id") Long id,
                                 @Param("userId") Long userId,
                                 @Param("articleId") Long articleId);

    @Select("SELECT COUNT(1) FROM yw_auth_product WHERE status = #{status}")
    Long selectCountByStatus(@Param("status") Integer status);


    List<YwAuthProductRespVO> getNeedSpuList();
}
