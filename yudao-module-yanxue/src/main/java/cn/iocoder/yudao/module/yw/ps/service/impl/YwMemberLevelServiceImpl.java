package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.ps.service.YwMemberLevelService;
import cn.iocoder.yudao.module.yw.ps.vo.YwMemberLevelWithAddressVo;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwMemberLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwMemberLevelSaveReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.yw.ps.dal.dataobject.YwMemberLevelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.ps.dal.mysql.YwMemberAuthLevelMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 用户等级 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwMemberLevelServiceImpl implements YwMemberLevelService {

    @Resource
    private YwMemberAuthLevelMapper ywMemberAuthLevelMapper;

    @Override
    public Long createMemberLevel(YwMemberLevelSaveReqVO createReqVO) {
        // 插入
        YwMemberLevelDO memberLevel = BeanUtils.toBean(createReqVO, YwMemberLevelDO.class);
        ywMemberAuthLevelMapper.insert(memberLevel);

        // 返回
        return memberLevel.getId();
    }

    @Override
    public void updateMemberLevel(YwMemberLevelSaveReqVO updateReqVO) {
        // 校验存在
        validateMemberLevelExists(updateReqVO.getId());
        // 更新
        YwMemberLevelDO updateObj = BeanUtils.toBean(updateReqVO, YwMemberLevelDO.class);
        ywMemberAuthLevelMapper.updateById(updateObj);
    }

    @Override
    public void deleteMemberLevel(Long id) {
        // 校验存在
        validateMemberLevelExists(id);
        // 删除
        ywMemberAuthLevelMapper.deleteById(id);
    }

    @Override
        public void deleteMemberLevelListByIds(List<Long> ids) {
        // 删除
        ywMemberAuthLevelMapper.deleteByIds(ids);
        }


    private void validateMemberLevelExists(Long id) {
        if (ywMemberAuthLevelMapper.selectById(id) == null) {
            throw exception(MEMBER_LEVEL_NOT_EXISTS);
        }
    }

    @Override
    public YwMemberLevelDO getMemberLevel(Long id) {
        return ywMemberAuthLevelMapper.selectById(id);
    }

    @Override
    public PageResult<YwMemberLevelDO> getMemberLevelPage(YwMemberLevelPageReqVO pageReqVO) {
        return ywMemberAuthLevelMapper.selectPage(pageReqVO);
    }

    @Override
    public List<YwMemberLevelDO> selectCanAuthMemberLevel() {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        return ywMemberAuthLevelMapper.selectCanAuthMemberLevel(memberId);
    }
    @Override
    public YwMemberLevelWithAddressVo selectMemberLevel(){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        List<YwMemberLevelWithAddressVo> result= ywMemberAuthLevelMapper.selectMemberLevel(memberId);
        if(result==null || result.size()==0){
            return null;
        }
        return result.get(0);
    }
    @Override
    public YwMemberLevelWithAddressVo selectMemberLevelById(Long id){
        YwMemberLevelWithAddressVo result= ywMemberAuthLevelMapper.selectMemberLevelById(id);

        return result;
    }

    @Override
    public boolean updateOrInsertAuthMemberLevel(Long levelId) {
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        int result=0;
        LambdaQueryWrapper<YwMemberLevelDO> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(YwMemberLevelDO::getMemberId,memberId);
        YwMemberLevelDO existVo=ywMemberAuthLevelMapper.selectOne(lambdaQueryWrapper);
        if(existVo!=null){
            if(existVo.getLevelId()==null || existVo.getLevelId()<levelId ){
                existVo.setLevelId(levelId);
                result=ywMemberAuthLevelMapper.updateById(existVo);
            }

        }else{
            YwMemberLevelDO levelDO=new YwMemberLevelDO();
            levelDO.setMemberId(memberId);
            levelDO.setLevelId(levelId);
            result=ywMemberAuthLevelMapper.insert(levelDO);
        }
        return result>0;
    }


}