// cn/iocoder/yudao/module/yw/ps/service/impl/YwAuthProductServiceImpl.java
package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.system.service.notify.NotifySendService;
import cn.iocoder.yudao.module.yw.base.Constants;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwAuthProductDO;
import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwAuthProductMapper;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthProductService;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthProductPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthProductRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthProductSaveReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.AUTH_PRODUCT_NOT_EXISTS;

@Service
@Validated
public class YwAuthProductServiceImpl implements YwAuthProductService {

    @Resource
    private YwAuthProductMapper authProductMapper;

    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    public Long createAuthProduct(YwAuthProductSaveReqVO createReqVO) {
        YwAuthProductDO authProduct = BeanUtils.toBean(createReqVO, YwAuthProductDO.class);
        authProductMapper.insert(authProduct);
        return authProduct.getId();
    }
    @Resource
    private NotifySendService sendService;
    @Override
    public void updateAuthProduct(YwAuthProductSaveReqVO updateReqVO) {
        validateAuthProductExists(updateReqVO.getId());
        YwAuthProductDO updateObj = BeanUtils.toBean(updateReqVO, YwAuthProductDO.class);
        authProductMapper.updateById(updateObj);
        if(updateReqVO.getStatus().equals(Constants.AuthStatus.FAIL.getValue())){
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("name", updateReqVO.getName());
            sendService.sendSingleNotifyToMember(updateReqVO.getMemberId(),"zp_fail",templateParams);
        }else if(updateReqVO.getStatus().equals(Constants.AuthStatus.SUCC.getValue())){
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("name", updateReqVO.getName());
            sendService.sendSingleNotifyToMember(updateReqVO.getMemberId(),"zp_success",templateParams);
        }
    }

    @Override
    public int submitProduct(YwAuthProductDO updateReqVO){
        validateAuthProductExists(updateReqVO.getId());
        return authProductMapper.submitProduct(updateReqVO.getId());
    }

    @Override
    public int updateAuthProductStatus(YwAuthProductDO updateReqVO){
        validateAuthProductExists(updateReqVO.getId());
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        return authProductMapper.updateAuthProductStatus(updateReqVO.getId(), userId, updateReqVO.getStatus());
    }

    @Override
    public void deleteAuthProduct(Long id) {
        validateAuthProductExists(id);
        authProductMapper.deleteById(id);
    }

    @Override
    public void deleteAuthProductListByIds(List<Long> ids) {
        authProductMapper.deleteByIds(ids);
    }

    private void validateAuthProductExists(Long id) {
        if (authProductMapper.selectById(id) == null) {
            throw exception(AUTH_PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public YwAuthProductDO getAuthProduct(Long id) {
        return authProductMapper.selectById(id);
    }

    @Override
    public PageResult<YwAuthProductDO> my(YwAuthProductPageReqVO reqVO) {
        return authProductMapper.selectPage(reqVO);
    }

    @Override
    public PageResult<YwAuthProductRespVO> getAuthProductPage(YwAuthProductPageReqVO reqVO) {
        // 如果传了 memberName，则先把名字查成一批 memberIds
        Collection<Long> memberIdsFilter = null;
        if (reqVO.getMemberName() != null && !reqVO.getMemberName().trim().isEmpty()) {
            List<MemberUserDO> matched = memberUserMapper.selectList(
                    new LambdaQueryWrapper<MemberUserDO>()
                            .like(MemberUserDO::getName, reqVO.getMemberName().trim())
                            .select(MemberUserDO::getId, MemberUserDO::getName)
            );
            if (CollUtil.isEmpty(matched)) {
                return PageResult.empty();
            }
            memberIdsFilter = matched.stream().map(MemberUserDO::getId).collect(Collectors.toSet());
        }

        // 分页查主表
        PageResult<YwAuthProductDO> page = (memberIdsFilter == null)
                ? authProductMapper.selectPage(reqVO)
                : authProductMapper.selectPage(reqVO, memberIdsFilter);

        if (page == null || CollUtil.isEmpty(page.getList())) {
            return PageResult.empty();
        }

        // 批量取 memberName
        List<Long> memberIds = page.getList().stream()
                .map(YwAuthProductDO::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        final Map<Long, MemberUserDO> memberMap;
        if (memberIds.isEmpty()) {
            memberMap = Collections.emptyMap();
        } else {
            List<MemberUserDO> members = memberUserMapper.selectList(
                    new LambdaQueryWrapper<MemberUserDO>()
                            .in(MemberUserDO::getId, memberIds)
                            .select(MemberUserDO::getId, MemberUserDO::getName)
            );
            memberMap = members.stream()
                    .collect(Collectors.toMap(MemberUserDO::getId, x -> x, (a, b) -> a));
        }

        List<YwAuthProductRespVO> voList = page.getList().stream().map(po -> {
            YwAuthProductRespVO vo = BeanUtils.toBean(po, YwAuthProductRespVO.class);
            MemberUserDO mu = memberMap.get(po.getMemberId());
            vo.setMemberName(mu != null ? mu.getName() : null);
            return vo;
        }).collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public List<YwAuthProductRespVO> getNeedSpuList(){
        return  authProductMapper.getNeedSpuList();
    }

}
