package cn.iocoder.yudao.module.yw.ps.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.member.service.level.MemberLevelService;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.service.notify.NotifyMessageService;
import cn.iocoder.yudao.module.system.service.notify.NotifySendService;
import cn.iocoder.yudao.module.yw.base.Constants;
import cn.iocoder.yudao.module.yw.ps.dal.dataobject.*;
import cn.iocoder.yudao.module.yw.ps.dal.mysql.*;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthCommentService;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthCommentStatusService;
import cn.iocoder.yudao.module.yw.ps.service.YwAuthLevelService;
import cn.iocoder.yudao.module.yw.ps.service.YwMemberLevelService;
import cn.iocoder.yudao.module.yw.ps.vo.*;
import cn.iocoder.yudao.module.yw.ps.vo.page.YwAuthLevelPageReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.resp.YwAuthLevelRespVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentSaveReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthCommentStatusSaveReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwAuthLevelSaveReqVO;
import cn.iocoder.yudao.module.yw.ps.vo.save.YwMemberLevelSaveReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.*;

/**
 * 评审申请 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class YwAuthLevelServiceImpl implements YwAuthLevelService {

    @Resource
    private YwAuthLevelMapper ywAuthLevelMapper;
    @Resource
    private MemberUserMapper memberUserMapper;
    @Resource
    private YwMemberLevelService memberLevelService;

    @Override
    public Long createYwAuthLevel(YwAuthLevelSaveReqVO createReqVO) {
        // 插入
        YwAuthLevelDO ywAuthLevel = BeanUtils.toBean(createReqVO, YwAuthLevelDO.class);
        ywAuthLevelMapper.insert(ywAuthLevel);

        // 返回
        return ywAuthLevel.getId();
    }

    @Override
    public void updateYwAuthLevel(YwAuthLevelSaveReqVO updateReqVO) {
        // 校验存在
        validateYwAuthLevelExists(updateReqVO.getId());
        // 更新
        YwAuthLevelDO updateObj = BeanUtils.toBean(updateReqVO, YwAuthLevelDO.class);
        ywAuthLevelMapper.updateById(updateObj);
    }

    @Override
    public void deleteYwAuthLevel(Long id) {
        // 校验存在
        validateYwAuthLevelExists(id);
        // 删除
        ywAuthLevelMapper.deleteById(id);
    }

    @Override
        public void deleteYwAuthLevelListByIds(List<Long> ids) {
        // 删除
        ywAuthLevelMapper.deleteByIds(ids);
        }


    private void validateYwAuthLevelExists(Long id) {
        if (ywAuthLevelMapper.selectById(id) == null) {
            throw exception(YW_AUTH_LEVEL_NOT_EXISTS);
        }
    }

    @Override
    public YwAuthLevelDO getYwAuthLevel(Long id) {
        return ywAuthLevelMapper.selectById(id);
    }
    @Override
    public List<YwAuthLevelDO> my(Long  memberId) {
        return ywAuthLevelMapper.my(memberId);
    }

    @Override
    public PageResult<YwAuthLevelRespVO> getYwAuthLevelPage(YwAuthLevelPageReqVO reqVO) {

        // 1) 会员筛选：根据 memberName / idCard 反查一批 memberId
        List<Long> memberIdsFilter = null;
        if (StrUtil.isNotBlank(reqVO.getMemberName()) || StrUtil.isNotBlank(reqVO.getIdCard())) {
            List<MemberUserDO> ms = memberUserMapper.selectList(
                    new LambdaQueryWrapper<MemberUserDO>()
                            .like(StrUtil.isNotBlank(reqVO.getMemberName()), MemberUserDO::getName, reqVO.getMemberName())
                            .like(StrUtil.isNotBlank(reqVO.getIdCard()),   MemberUserDO::getIdcard, reqVO.getIdCard())
                            .select(MemberUserDO::getId)
            );
            memberIdsFilter = ms.stream().map(MemberUserDO::getId).collect(Collectors.toList());
            if (memberIdsFilter.isEmpty()) {
                return PageResult.empty(); // 直接空页
            }
        }

        // 2) 导师筛选：根据 teacherName 反查一批会员 ID，然后用 FIND_IN_SET 过滤
        List<Long> teacherIdsFilter = null;
        if (StrUtil.isNotBlank(reqVO.getTeacherName())) {
            List<MemberUserDO> ts = memberUserMapper.selectList(
                    new LambdaQueryWrapper<MemberUserDO>()
                            .like(MemberUserDO::getName, reqVO.getTeacherName())
                            .select(MemberUserDO::getId)
            );
            teacherIdsFilter = ts.stream().map(MemberUserDO::getId).collect(Collectors.toList());
            if (teacherIdsFilter.isEmpty()) {
                return PageResult.empty();
            }
        }

        // 3) 分页查本表
        PageResult<YwAuthLevelDO> page = ywAuthLevelMapper.selectPage(reqVO, memberIdsFilter, teacherIdsFilter);
        if (CollUtil.isEmpty(page.getList())) {
            return PageResult.empty(page.getTotal());
        }

        // 4) 批量补齐：把 memberId + teacher(拆成ID集合) 全部查出姓名&身份证
        Set<Long> needIds = new HashSet<>();
        page.getList().forEach(it -> {
            if (it.getMemberId() != null) needIds.add(it.getMemberId());
            if (StrUtil.isNotBlank(it.getTeacher())) {
                for (String s : StrUtil.splitTrim(it.getTeacher(), ',')) {
                    if (NumberUtil.isLong(s)) needIds.add(Long.parseLong(s));
                }
            }
        });

        Map<Long, MemberUserDO> userMap = needIds.isEmpty() ? Collections.emptyMap()
                : memberUserMapper.selectBatchIds(needIds).stream()
                .collect(Collectors.toMap(MemberUserDO::getId, e -> e));

        // 5) 组装 VO
        List<YwAuthLevelRespVO> voList = page.getList().stream().map(po -> {
            YwAuthLevelRespVO vo = BeanUtils.toBean(po, YwAuthLevelRespVO.class);
            // 会员姓名 & 身份证
            MemberUserDO m = userMap.get(po.getMemberId());
            if (m != null) {
                vo.setMemberName(m.getName());
                vo.setIdCard(m.getIdcard());
            }
            // 导师姓名（把 "1,2,250" → "张三,李四,王五"）
            if (StrUtil.isNotBlank(po.getTeacher())) {
                List<String> names = StrUtil.splitTrim(po.getTeacher(), ',').stream()
                        .map(idStr -> {
                            if (NumberUtil.isLong(idStr)) {
                                MemberUserDO u = userMap.get(Long.parseLong(idStr));
                                return u != null ? u.getName() : idStr;
                            }
                            return idStr; // 防御：非数字
                        })
                        .collect(Collectors.toList());
                vo.setTeacherName(String.join(",", names));
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public YwAuthLevelTotalVo beginAuth(Long levelId){
        //创建主题
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        Map<String, Object> params = new HashMap<>();
        params.put("levelId", levelId);
        params.put("memberId", memberId);
// 执行插入
        ywAuthLevelMapper.beginAuth(params);
//        BigInteger  id= (BigInteger) params.get("id");
        Long authId = ((BigInteger) params.get("id")).longValue();
        //创建对应的硬性条件
        ywAuthLevelMapper.beginAuthCondition(levelId, memberId, authId);
        //创建对应的积分条件
        ywAuthLevelMapper.beginAuthDetail(levelId, memberId, authId);
        //返回结果
        YwAuthLevelRequestVo ywAuthLevelRequestVo=new YwAuthLevelRequestVo();
        ywAuthLevelRequestVo.setAuthId(authId);
        ywAuthLevelRequestVo.setIzTest("1");
        YwAuthLevelTotalVo authLevelMemberVo=getAuthTotal(ywAuthLevelRequestVo);
        authLevelMemberVo.setStatus(Constants.AuthStatus.Init.getValue());
        return authLevelMemberVo;
    }

    YwAuthLevelMemberVo selectMemberAuth(Long authId){
        YwAuthLevelMemberVo memberVo=new YwAuthLevelMemberVo();
        YwAuthLevelDO levelDO=ywAuthLevelMapper.selectById(authId);
        BeanUtils.copyProperties(levelDO,memberVo);
        if(levelDO==null || StringUtils.isEmpty(levelDO.getId())){
            return null;
        }
        memberVo.setCondiList(ywAuthLevelMapper.SelectAuthCondition(authId));
        memberVo.setDetailList(ywAuthLevelMapper.SelectAuthDetail(authId));
        return memberVo;
    }

    @Override
    public YwAuthLevelMemberVo updateAuth(YwAuthLevelMemberVo authLevelMemberVo){
        if(authLevelMemberVo==null || StringUtils.isEmpty(authLevelMemberVo.getId())){
            throw new IllegalArgumentException("题目参数有错误");
        }
        for(YwAuthConditionMemberVo condi : authLevelMemberVo.getCondiList()){
            ywAuthLevelMapper.updateAuthCondition(condi.getId(),condi.getIzSelected(), condi.getUrls());
        }
        for(YwAuthDetailMemberVo detail : authLevelMemberVo.getDetailList()){
            ywAuthLevelMapper.updateAuthDetail(detail.getId(), detail.getIzSelected(), detail.getUrls());
        }
        //这里要重置一下三位老师的评价状态。
        return selectMemberAuth(authLevelMemberVo.getId());
    }

    @Override
    public YwAuthLevelTotalVo updateAuthTotal(YwAuthLevelTotalVo authLevelMemberVo){
        if(authLevelMemberVo==null || StringUtils.isEmpty(authLevelMemberVo.getId())){
            throw new IllegalArgumentException("题目参数有错误");
        }
        //提交修改detailIsSelected和url两个参数
        for(YwLevelCondilWithCommentVo condi : authLevelMemberVo.getCondiList()){
            ywAuthLevelMapper.updateAuthCondition(condi.getId(),condi.getDetailIsSelected(), condi.getUrls());
        }
        List<YwLevelDetailWithCommentVo> details=new ArrayList<>();
        for(YwLevelGroupWithNormVo gropu : authLevelMemberVo.getDetailList()){
            for(YwLevelNormWithDetailVo norm : gropu.getList()){
                details.addAll(norm.getList());
            }
        }
        for(YwLevelDetailWithCommentVo detail : details){
            ywAuthLevelMapper.updateAuthDetail(detail.getId(), detail.getDetailIsSelected(), detail.getDetailUrls());
        }
        //这里要把状态改成1，
        ywAuthLevelMapper.updateToSave(authLevelMemberVo.getId());
//        //这里要重置一下三位老师的评价状态。
        YwAuthLevelRequestVo total=new YwAuthLevelRequestVo();
        total.setAuthId(authLevelMemberVo.getId());
        total.setIzTest("1");
        return getAuthTotal(total);
//        return null;
    }

    @Override
    public boolean submitAuth(Long authId){
        int result=ywAuthLevelMapper.submitAuth(authId);
        return result>0;
    }
    @Override
    public boolean updateAuthTeacher(YwAuthLevelMemberVo authLevelMemberVo){

        if(authLevelMemberVo==null || StringUtils.isEmpty(authLevelMemberVo.getId())){
            throw new IllegalArgumentException("题目参数有错误");
        }
        if(StringUtils.isEmpty(authLevelMemberVo.getTeacher())){
            throw new IllegalArgumentException("未设置老师");
        }
        YwAuthLevelDO levelDO=ywAuthLevelMapper.selectById(authLevelMemberVo.getId());
        if(levelDO==null ){
            throw new IllegalArgumentException("题目参数有错误");
        }
        if(!levelDO.getStatus().equals(Constants.AuthStatus.SUBMITED.getValue())){
            throw new IllegalArgumentException("认证状态异常，不允许分配");
        }

        int result=ywAuthLevelMapper.updateAuthTeacher(authLevelMemberVo.getId(),authLevelMemberVo.getTeacher());
        String[] teachers=authLevelMemberVo.getTeacher().split(",");
        if(teachers!=null && teachers.length>0){
            for(String teacher: teachers){
                try{
                    Map<String, Object> templateParams = new HashMap<>();
                    templateParams.put("name", authLevelMemberVo.getName());
                    sendService.sendSingleNotifyToMember(Long.parseLong(teacher),"new_teacher_job",templateParams);
                }catch (Exception e ){
                    continue;
                }
            }
        }
        return result>0;
    }
    @Override
    public List<YwAuthLevelForTeacherVo> getTeacherJob(String teacherStatus){
        Long teacher= SecurityFrameworkUtils.getLoginUserId();
        return ywAuthLevelMapper.getTeacherJob(teacher,teacherStatus);
    }
    @Override
    public YwAuthLevelMemberVo getAuthForTeacherComment(YwAuthLevelMemberVo authLevelMemberVo){
        if(authLevelMemberVo==null || StringUtils.isEmpty(authLevelMemberVo.getId())){
            throw new IllegalArgumentException("题目参数有错误");
        }
        YwAuthLevelDO levelDO=ywAuthLevelMapper.selectById(authLevelMemberVo.getId());
        if(levelDO==null ){
            throw new IllegalArgumentException("题目参数有错误");
        }
//        if(!levelDO.getStatus().equals(Constants.AuthStatus.COMMENT.getValue())){
//            throw new IllegalArgumentException("认证状态异常，不允许批改");
//        }
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        YwAuthLevelMemberVo memberVo=new YwAuthLevelMemberVo();
        BeanUtils.copyProperties(levelDO,memberVo);
        if(levelDO==null || StringUtils.isEmpty(levelDO.getId())){
            return null;
        }
        memberVo.setCondiList(ywAuthLevelMapper.SelectAuthConditionForTeacherComment(authLevelMemberVo.getId(),memberId));
        memberVo.setDetailList(ywAuthLevelMapper.SelectAuthDetailForTeacherComment(authLevelMemberVo.getId(),memberId));
        //设置一下teacherStatus
        String teacherStatus=authCommentStatusService.getTeacherStatusByMemberId(levelDO.getId(), memberId);
        memberVo.setTeacherStatus(teacherStatus);
        return memberVo;
    }
    @Resource
    private YwAuthCommentService authCommentService;
    @Resource
    private YwAuthCommentStatusService authCommentStatusService;
    @Resource
    private NotifySendService sendService;
    @Override
    public YwAuthLevelTotalVo updateAuthComment(YwAuthLevelMemberVo authLevelMemberVo){
        if(authLevelMemberVo==null || StringUtils.isEmpty(authLevelMemberVo.getId())){
            throw new IllegalArgumentException("题目参数有错误");
        }
        Long memberId= SecurityFrameworkUtils.getLoginUserId();
        //先把原来的全都删了，然后重新插入？
        ywAuthLevelMapper.deleteAuthComment(authLevelMemberVo.getId(),memberId);
        ywAuthLevelMapper.deleteAuthCommentStatus(authLevelMemberVo.getId(),memberId);
        //全部重新插入
        for(YwAuthConditionMemberVo condi : authLevelMemberVo.getCondiList()){
            YwAuthCommentSaveReqVO commentDO=new YwAuthCommentSaveReqVO();
            commentDO.setAuthId(authLevelMemberVo.getId());
            commentDO.setTeacherId(memberId);
            commentDO.setCommentType(Constants.AuthCommentType.CONDI.getValue());
            commentDO.setDetailId(condi.getId());
            commentDO.setStatus(condi.getCommentStatus());
            commentDO.setBz(condi.getBz());
            authCommentService.createYwAuthComment(commentDO);
//            ywAuthLevelMapper.updateAuthCondition(condi.getId(), condi.getUrls());
        }
        for(YwAuthDetailMemberVo detail : authLevelMemberVo.getDetailList()){
            YwAuthCommentSaveReqVO commentDO = new YwAuthCommentSaveReqVO();
            commentDO.setAuthId(authLevelMemberVo.getId());
            commentDO.setTeacherId(memberId);
            commentDO.setCommentType(Constants.AuthCommentType.DETAIL.getValue());
            commentDO.setDetailId(detail.getId());
            commentDO.setStatus(detail.getCommentStatus());
            commentDO.setBz(detail.getBz());
            authCommentService.createYwAuthComment(commentDO);
        }
        //给自己插入一条完成的记录
        YwAuthCommentStatusSaveReqVO commentStatusDO = new YwAuthCommentStatusSaveReqVO();
        commentStatusDO.setAuthId(authLevelMemberVo.getId());
        commentStatusDO.setTeacherId(memberId);
        commentStatusDO.setStatus("1");
        authCommentStatusService.createYwAuthCommentStatus(commentStatusDO);
        //插入之后，判断一下,有没有用户选了，但是没有comment的
        int result=authCommentStatusService.updateTeacherCommentStatus(authLevelMemberVo.getId(),memberId);
        if(result>=3){
            YwAuthLevelRequestVo requestVo=new YwAuthLevelRequestVo();
            requestVo.setAuthId(authLevelMemberVo.getId());
            requestVo.setIzTest("0");
            YwAuthLevelTotalVo newTotalVo= getAuthTotal(requestVo);
            //如果通过了，要更新结果，同时插入一个新的member lever进去

//            ywAuthLevelMapper.updateById(newTotalVo);
            if(newTotalVo.getStatus().equals(Constants.AuthStatus.SUCC.getValue())){
                ywAuthLevelMapper.updateStatus(authLevelMemberVo.getId(),"10");
                //更新登记
                YwMemberLevelSaveReqVO memberLevelDO=new YwMemberLevelSaveReqVO();
                memberLevelDO.setLevelId(newTotalVo.getLevelId());
                memberLevelDO.setMemberId(newTotalVo.getMemberId());
                memberLevelService.createMemberLevel(memberLevelDO);
                memberUserMapper.updateMemberLevelId(newTotalVo.getMemberId(), newTotalVo.getLevelId());
                Map<String, Object> templateParams = new HashMap<>();
                templateParams.put("name", authLevelMemberVo.getName());
                sendService.sendSingleNotifyToMember(authLevelMemberVo.getMemberId(),"zyzrz_success",templateParams);
            }else{
                ywAuthLevelMapper.updateStatus(authLevelMemberVo.getId(),"9");
                Map<String, Object> templateParams = new HashMap<>();
                templateParams.put("name", authLevelMemberVo.getName());
                templateParams.put("id", authLevelMemberVo.getId());
                sendService.sendSingleNotifyToMember(authLevelMemberVo.getMemberId(),"zyzrz_fail",templateParams);
            }
            //否则更新结果，为不通过
            return newTotalVo;
        }
        return null;
    }
    @Autowired
    private YwLevelGroupMapper groupMapper;
    @Autowired
    private YwLevelConditionMapper condiMapper;
    @Override
    public YwAuthLevelTotalVo getAuthTotal(YwAuthLevelRequestVo authLevelRequestVo) {
        YwAuthLevelTotalVo totalVo=new YwAuthLevelTotalVo();
        YwAuthLevelDO levelDO=getYwAuthLevel(authLevelRequestVo.getAuthId());
        if(levelDO==null || StringUtils.isEmpty(levelDO.getLevelId())){
            return null;
        }
        BeanUtils.copyProperties(levelDO,totalVo);
        // 1. 查询所有评审组
        List<YwLevelGroupWithNormVo> result = groupMapper.selectAllGroupsWithDetails(authLevelRequestVo.getAuthId());
        AtomicBoolean izExclude = new AtomicBoolean(false);
        StringBuffer errorMsgBuffer = new StringBuffer();
        long resultScore=result.stream().mapToLong(
                group -> {
                    // 先计算 group 下所有 norm 的总分
                    long groupTotalScore = group.getList().stream()
                            .mapToLong(norm -> {
                                // 计算当前 norm 的总分（基于 detail 的 score）
                                long normTotalScore = norm.getList().stream()
                                        .mapToLong(detail -> {
                                            boolean passed =false;
                                            if("1".equals(detail.getDetailIsSelected())){
                                                if("1".equals(authLevelRequestVo.getIzTest())){
                                                    passed=true;
                                                }else{
                                                    passed = calculatePassStatus(detail.getComments());
                                                }
                                            }
                                            detail.setPassed(passed);

                                            if (!passed) {
                                                detail.setScore(0L);
                                                if("1".equals(detail.getDetailIsSelected())){
                                                    synchronized (errorMsgBuffer) {
                                                        errorMsgBuffer.append("积分条件").append(group.getName())
                                                                .append("--")
                                                                .append(norm.getName())
                                                                .append("--")
                                                                .append(detail.getName())
                                                                .append("评审不通过\n");
                                                }

                                                }
                                            }else{
                                                detail.setScore(detail.getDetailScore());
                                            }

                                            if(!StringUtils.isEmpty(detail.getExcludeLevelId()) && detail.getExcludeLevelId().equals(levelDO.getLevelId())) {
                                                izExclude.set(true);
                                            }
                                            return detail.getScore() != null ? detail.getScore() : 0L;
                                        })
                                        .sum();

                                // 限制 norm 的 score 不超过 maxScore
                                norm.setScore(Math.min( normTotalScore, norm.getMaxScore()));
                                return norm.getScore(); // 返回当前 norm 的 score（用于计算 group 总分）
                            })
                            .sum();

                    // 设置 group 的 score（也可以限制不超过 group.getMaxScore()，如果有的话）
                    group.setScore(Math.min(groupTotalScore, group.getMaxScore()));
                    return group.getScore();
                }).sum();

        // 2. 处理每个详情项的评审结果
        String condiErrorMsg="";
        totalVo.setDetailList(result);
        totalVo.setIzExcludePass(izExclude.get()?"1":"0");
        totalVo.setRealScore(resultScore);
        totalVo.setOriginStatus(totalVo.getStatus());
        totalVo.setCondiList(condiMapper.selectAllCondiWithDetails(authLevelRequestVo.getAuthId()));
        boolean izCondiPass=true;
        for(YwLevelCondilWithCommentVo condil: totalVo.getCondiList()){
            boolean passed =false;
            if("1".equals(authLevelRequestVo.getIzTest())){
                passed=true;
            }else{
                passed = calculatePassStatus(condil.getComments());
            }
            condil.setPassed(passed);
            if(!passed){
                izCondiPass=false;
                condiErrorMsg+="必要条件："+condil.getName()+"评审不通过；\n";
            }
        }
        condiErrorMsg+=errorMsgBuffer.toString();
        totalVo.setIzCondiPass(izCondiPass?"1":"0");
        if("1".equals(totalVo.getIzExcludePass())){
            totalVo.setStatus(Constants.AuthStatus.SUCC.getValue());

            //更新状态，再更新
        }else{
            if("1".equals(totalVo.getIzCondiPass())){
                if(totalVo.getScore()<=totalVo.getRealScore()){
                    totalVo.setStatus(Constants.AuthStatus.SUCC.getValue());
                }else {
                    totalVo.setStatus(Constants.AuthStatus.FAIL.getValue());
                }
            }else{
                totalVo.setStatus(Constants.AuthStatus.FAIL.getValue());
            }
        }
        totalVo.setErrorMsg(condiErrorMsg);
        return totalVo;
    }

    /**
     * 计算评审是否通过 (至少2个老师通过)
     * @param comments 评审意见列表
     * @return 是否通过
     */
    private boolean calculatePassStatus(List<YwAuthCommentDO> comments) {
        if (comments == null || comments.isEmpty()) {
            return false;
        }

        // 假设"通过"的状态值为"PASS" (根据实际业务调整)
        long passCount = comments.stream()
                .filter(comment -> "1".equals(comment.getStatus()))
                .count();

        return passCount >= 2;
    }
}
