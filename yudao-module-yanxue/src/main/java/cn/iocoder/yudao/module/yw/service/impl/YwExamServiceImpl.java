package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuOptionDO;
import cn.iocoder.yudao.module.yw.dal.mysql.YwQuOptionMapper;
import cn.iocoder.yudao.module.yw.service.YwExamService;
import cn.iocoder.yudao.module.yw.vo.page.YwExamPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwExamSaveReqVO;
import cn.iocoder.yudao.module.yw.vo.YwQuTotalVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwExamDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwQuDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.yw.dal.mysql.YwExamMapper;
import cn.iocoder.yudao.module.yw.dal.mysql.YwQuMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 考卷设计 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwExamServiceImpl implements YwExamService {

    @Resource
    private YwExamMapper examMapper;
    @Resource
    private YwQuMapper quMapper;

    @Resource
    private YwQuOptionMapper optionMapper;

    @Override
    public Long createExam(YwExamSaveReqVO createReqVO) {
        // 插入
        YwExamDO exam = BeanUtils.toBean(createReqVO, YwExamDO.class);
        examMapper.insert(exam);

        // 返回
        return exam.getId();
    }

    @Override
    public void updateExam(YwExamSaveReqVO updateReqVO) {
        // 校验存在
        validateExamExists(updateReqVO.getId());
        // 更新
        YwExamDO updateObj = BeanUtils.toBean(updateReqVO, YwExamDO.class);
        examMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExam(Long id) {
        // 校验存在
        validateExamExists(id);
        // 删除
        examMapper.deleteById(id);

        // 删除子表
        deleteQuByQuType(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteExamListByIds(List<Long> ids) {
        // 删除
        examMapper.deleteByIds(ids);
    
    // 删除子表
            deleteQuByQuTypes(ids);
    }


    private void validateExamExists(Long id) {
        if (examMapper.selectById(id) == null) {
            throw exception(EXAM_NOT_EXISTS);
        }
    }

    @Override
    public YwExamDO getExam(Long id) {
        return examMapper.selectById(id);
    }

    @Override
    public PageResult<YwExamDO> getExamPage(YwExamPageReqVO pageReqVO) {
        return examMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（试题） ====================

    @Override
    public PageResult<YwQuDO> getQuPage(PageParam pageReqVO, Long examId) {
        return quMapper.selectPage(pageReqVO, examId);
    }

    @Override
    public List<YwQuDO> getQuList( Long examId) {
        return quMapper.selectList( examId);
    }

    @Override
    public Long createQu(YwQuDO qu) {
        qu.clean(); // 清理掉创建、更新时间等相关属性值
        quMapper.insert(qu);
        return qu.getId();
    }

    @Override
    public void updateQu(YwQuDO qu) {
        // 校验存在
        validateQuExists(qu.getId());
        // 更新
        qu.clean(); // 解决更新情况下：updateTime 不更新
        quMapper.updateById(qu);
    }

    @Override
    public void deleteQu(Long id) {
        // 删除
        quMapper.deleteById(id);
    }

	@Override
	public void deleteQuListByIds(List<Long> ids) {
        // 删除
        quMapper.deleteByIds(ids);
	}

    @Override
    public YwQuDO getQu(Long id) {
        return quMapper.selectById(id);
    }

    private void validateQuExists(Long id) {
        if (quMapper.selectById(id) == null) {
            throw exception(QU_NOT_EXISTS);
        }
    }

    private void deleteQuByQuType(Long examId) {
        quMapper.deleteByExamId(examId);
    }

	private void deleteQuByQuTypes(List<Long> examIds) {
        quMapper.deleteByExamIds(examIds);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importExam(List<YwQuTotalVo>  list, YwExamDO examDO){
        examMapper.insert(examDO);
        Integer totalScore=0;
        if(examDO!=null && examDO.getId()!=null){
            for(YwQuTotalVo quTotalVo : list){
                YwQuDO ywQuDO=new YwQuDO();
                BeanUtils.copyProperties(quTotalVo,ywQuDO);
                ywQuDO.setExamId(examDO.getId());
                quMapper.insert(ywQuDO);
                if (ywQuDO.getScore() != null) {
                    totalScore += ywQuDO.getScore();
                }
                Map<String, String> options = quTotalVo.getNonEmptyOptions();
//                Map<String, String> options = null;
                if (options != null) {
                    for (Map.Entry<String, String> entry : options.entrySet()) {
                        String optionChar = entry.getKey();
                        String optionValue = entry.getValue();
                        YwQuOptionDO optionDO = new YwQuOptionDO();
                        optionDO.setContent(optionValue);
                        optionDO.setQuId(ywQuDO.getId());
                        optionDO.setAbcd(optionChar);
                        if (ywQuDO.getAnswer().contains(optionChar)) {
                            optionDO.setIzRight("1");
                        } else {
                            optionDO.setIzRight("0");
                        }
                        optionMapper.insert(optionDO);
                    }
                }
            }
            examDO.setTotalScore(totalScore);
            examMapper.updateById(examDO);
            return true;
        }
        //全部插入后，更新exam的总分值。
        return false;
    }

}