package cn.iocoder.yudao.module.yw.service.impl;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.*;
import cn.iocoder.yudao.module.yw.dal.mysql.*;
import cn.iocoder.yudao.module.yw.service.YwCourseService;
import cn.iocoder.yudao.module.yw.service.YwExamService;
import cn.iocoder.yudao.module.yw.service.YwPaperService;
import cn.iocoder.yudao.module.yw.vo.*;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperPageReqVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperSaveReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 试卷 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwPaperServiceImpl implements YwPaperService {

    @Resource
    private YwPaperMapper paperMapper;
    @Resource
    private YwPaperQuMapper quMapper;
    @Resource
    private YwQuMapper ywQuMapper;
    @Resource
    private YwQuOptionMapper ywQuOptionMapper;
    @Resource
    private YwExamService ywExamService;

    @Resource
    private YwPaperQuOptionMapper opthonMapper;
    @Resource
    private YwCourseService courseService;
    @Override
    public Long createPaper(YwPaperSaveReqVO createReqVO) {
        // 插入
        YwPaperDO paper = BeanUtils.toBean(createReqVO, YwPaperDO.class);
        paperMapper.insert(paper);

        // 返回
        return paper.getId();
    }

    @Override
    public void updatePaper(YwPaperSaveReqVO updateReqVO) {
        // 校验存在
        validatePaperExists(updateReqVO.getId());
        // 更新
        YwPaperDO updateObj = BeanUtils.toBean(updateReqVO, YwPaperDO.class);
        paperMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePaper(Long id) {
        // 校验存在
        validatePaperExists(id);
        // 删除
        paperMapper.deleteById(id);

        // 删除子表
        deletePaperQuByPaperId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deletePaperListByIds(List<Long> ids) {
        // 删除
        paperMapper.deleteByIds(ids);
    
    // 删除子表
            deletePaperQuByPaperIds(ids);
    }


    private void validatePaperExists(Long id) {
        if (paperMapper.selectById(id) == null) {
            throw exception(PAPER_NOT_EXISTS);
        }
    }

    @Override
    public YwPaperDO getPaper(Long id) {
        return paperMapper.selectById(id);
    }

    @Override
    public PageResult<YwPaperDO> getPaperPage(YwPaperPageReqVO pageReqVO) {
        return paperMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（试卷题目） ====================

    @Override
    public PageResult<YwPaperQuDO> getPaperQuPage(PageParam pageReqVO, Long paperId) {
        return quMapper.selectPage(pageReqVO, paperId);
    }

    @Override
    public Long createPaperQu(YwPaperQuDO paperQu) {
        paperQu.clean(); // 清理掉创建、更新时间等相关属性值
        quMapper.insert(paperQu);
        return paperQu.getId();
    }

    @Override
    public void updatePaperQu(YwPaperQuDO paperQu) {
        // 校验存在
        validatePaperQuExists(paperQu.getId());
        // 更新
        paperQu.clean(); // 解决更新情况下：updateTime 不更新
        quMapper.updateById(paperQu);
    }

    @Override
    public void deletePaperQu(Long id) {
        // 删除
        quMapper.deleteById(id);
    }

	@Override
	public void deletePaperQuListByIds(List<Long> ids) {
        // 删除
        quMapper.deleteByIds(ids);
	}

    @Override
    public YwPaperQuDO getPaperQu(Long id) {
        return quMapper.selectById(id);
    }

    private void validatePaperQuExists(Long id) {
        if (quMapper.selectById(id) == null) {
            throw exception(PAPER_QU_NOT_EXISTS);
        }
    }

    private void deletePaperQuByPaperId(Long paperId) {
        quMapper.deleteByPaperId(paperId);
    }

	private void deletePaperQuByPaperIds(List<Long> paperIds) {
        quMapper.deleteByPaperIds(paperIds);
	}

    @Override
    @Transactional
    public YwPaperEntity beginPaper(Long courseId, Long examId){
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("examId", examId);
        params.put("memberId", memberId);

// 执行插入
        paperMapper.beginPaper(params);
//        BigInteger  id= (BigInteger) params.get("id");
        Long paperId = ((BigInteger) params.get("id")).longValue();
        List<YwQuDO> quList = ywExamService.getQuList(examId);
        int j= paperMapper.beginPaperQu(paperId, quList);
        //要去拿到新的题目
        List<YwPaperQuDO> paperQuDOS=quMapper.selectList(paperId);
        List<YwQuOptionDO> optionList = new ArrayList<>();
        for (YwPaperQuDO qu : paperQuDOS) {
            List<YwQuOptionDO> options = ywQuOptionMapper.selectListByQuId(qu.getQuId());
            optionList.addAll(options);
            int k = paperMapper.beginPaperQuOption(paperId, qu.getId(), options);
        }

        // 5. 批量插入题目选项

        return selectPaperEntity(paperId);
    }
    @Autowired
    YwExamService examService;
    @Override
    @Transactional
    public YwExamTotalVo beginPaperSimple( Long examId){
        YwExamDO examDO=examService.getExam(examId);
        if(examDO==null || StringUtils.isEmpty(examDO.getId())){
            return null;
        }
        List<YwQuDO> quDOS=examService.getQuList(examId);
        List<YwQuOptionDO> optionDOS=opthonMapper.selecListByExamId(examId);
        List<YwQuVo> quEntities=new ArrayList<>();
        for (YwQuDO qu:quDOS
        ) {
            YwQuVo entity=new YwQuVo();
            BeanUtils.copyProperties(qu,entity);
            List<YwQuOptionDO> filteredOptions = optionDOS.stream()
                    .filter(option -> Objects.equals(option.getQuId(), qu.getId()))
                    .collect(Collectors.toList());
            entity.setOptions(filteredOptions);
            quEntities.add(entity);
        }
        YwExamTotalVo paperEntity=new YwExamTotalVo();
        BeanUtils.copyProperties(examDO,paperEntity);
        paperEntity.setQus(quEntities);
        return paperEntity;
    }
    @Override
    @Transactional
    public Boolean submitPaperSimple(YwExamTotalVo exam){
        int total=0;
        for (YwQuVo qu:exam.getQus()
        ) {
            if(qu.getRealAnswer()!=null && qu.getRealAnswer().equals(qu.getAnswer())){
                total+=qu.getScore();
            }
        }
        if(total>=exam.getQualifyScore()){
            //更新所有的课程和chapter
            Long memberId= SecurityFrameworkUtils.getLoginUserId();
            courseService.updateAllChapterMember(exam.getCourseId(), memberId);
            courseService.updateCourseMember(exam.getCourseId(), memberId);
            return true;
        }
        return false;
    }

    public YwPaperEntity selectPaperEntity(Long paperId){
        YwPaperDO paperDO=paperMapper.selectById(paperId);
        if(paperDO==null || StringUtils.isEmpty(paperDO.getId())){
            return null;
        }
        List<YwPaperQuDO> quDOS=quMapper.selectList(paperDO.getId());
        List<YwPaperQuOptionDO> optionDOS=opthonMapper.selecListByPaperId(paperDO.getId());
        List<YwPaperQuEntity> quEntities=new ArrayList<>();
        for (YwPaperQuDO qu:quDOS
             ) {
            YwPaperQuEntity entity=new YwPaperQuEntity();
            BeanUtils.copyProperties(qu,entity);
            List<YwPaperQuOptionDO> filteredOptions = optionDOS.stream()
                    .filter(option -> option.getQuId() != null && option.getQuId().equals(qu.getId()))
                    .collect(Collectors.toList());
            entity.setList(filteredOptions);
            quEntities.add(entity);
        }
        YwPaperEntity paperEntity=new YwPaperEntity();
        BeanUtils.copyProperties(paperDO,paperEntity);
        paperEntity.setList(quEntities);
        return paperEntity;
    }
    @Override
    public
    Boolean submitPaperQu(YwPaperQuEntity entity){
        if(entity==null || StringUtils.isEmpty(entity.getId())){
            throw new IllegalArgumentException("题目参数有错误");
        }
        if(entity.getList()==null || entity.getList().size()==0){
            throw new IllegalArgumentException("选项参数有错误");
        }
        String answer="";
        for(YwPaperQuOptionDO optionDO : entity.getList()){
            if(optionDO.getIzAnswered().equals("1")){
                answer+=optionDO.getAbcd();
            }
            if(optionDO.getIzAnswered().equals(optionDO.getIzRight())){
                optionDO.setRealRight("1");
            }else{
                optionDO.setRealRight("0");
            }
        }
        opthonMapper.updateBatch(entity.getList());
        entity.setIzAnswered("1");
        entity.setRealAnswer(answer);
        if(answer.equals(entity.getAnswer())){
            entity.setIzRight("1");
        }else{
            entity.setIzRight("0");
        }
        quMapper.updateById(entity);
        return true;
    }
    @Override
    public
    YwPaperDO submit(Long paperId){
        YwPaperEntity entity=selectPaperEntity(paperId);
        int score=0;
        for(YwPaperQuEntity qu : entity.getList()){
            if("1".equals(qu.getIzRight())){
                score+=qu.getScore();
            }
        }
        if(score>=entity.getQualifyScore()){
            //通过考试
            entity.setIzPass("1");
            entity.setStatus("10");
            //更新课程状态

            //更新积分状态
        }else{
            entity.setIzPass("0");
            entity.setStatus("9");
        }

        return null;
    }
}