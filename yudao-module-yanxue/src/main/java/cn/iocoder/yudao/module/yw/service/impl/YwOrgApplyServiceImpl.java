package cn.iocoder.yudao.module.yw.service.impl;

import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwOrganizationInfoDO;
import cn.iocoder.yudao.module.yw.dal.mysql.YwOrganizationInfoMapper;
import cn.iocoder.yudao.module.yw.service.YwOrgApplyService;
import cn.iocoder.yudao.module.yw.vo.page.YwOrgApplyPageReqVO;
import cn.iocoder.yudao.module.yw.vo.page.YwOrganizationApplyAuditReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwOrgApplyRespVO;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class YwOrgApplyServiceImpl implements YwOrgApplyService
{
    @Resource
    private YwOrganizationInfoMapper mapper;

    public PageResult<YwOrgApplyRespVO> getPage(YwOrgApplyPageReqVO reqVO) {
        // 使用MyBatis-Plus分页查询
        Page<YwOrganizationInfoDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        LambdaQueryWrapper<YwOrganizationInfoDO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(reqVO.getUsername())) {
            wrapper.like(YwOrganizationInfoDO::getUsername, reqVO.getUsername());
        }
        if (reqVO.getStatus() != null) {
            wrapper.eq(YwOrganizationInfoDO::getStatus, reqVO.getStatus());
        }

        // ... 其他条件

        Page<YwOrganizationInfoDO> result = mapper.selectPage(page, wrapper);
        List<YwOrgApplyRespVO> voList = result.getRecords().stream().map(this::toVO).collect(Collectors.toList());
        return new PageResult<>(voList, result.getTotal());
    }

    @Override
    public List<YwOrgApplyRespVO> getList() {
        List<YwOrganizationInfoDO> list = mapper.selectList(null);
        return list.stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public YwOrgApplyRespVO getDetail(Long id) {
        YwOrganizationInfoDO entity = mapper.selectById(id);
        Assert.notNull(entity, "记录不存在");
        return toVO(entity);
    }


    public void audit(YwOrganizationApplyAuditReqVO reqVO) {
        YwOrganizationInfoDO entity = mapper.selectById(reqVO.getId());
        Assert.notNull(entity, "记录不存在");
        entity.setStatus(reqVO.getStatus());
        entity.setAuditReason(reqVO.getAuditReason());
        entity.setAuditTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    private YwOrgApplyRespVO toVO(YwOrganizationInfoDO entity) {
        YwOrgApplyRespVO vo = new YwOrgApplyRespVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
