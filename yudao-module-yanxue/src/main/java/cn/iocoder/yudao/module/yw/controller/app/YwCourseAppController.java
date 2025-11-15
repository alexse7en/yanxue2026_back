package cn.iocoder.yudao.module.yw.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseDO;
import cn.iocoder.yudao.module.yw.service.YwCourseService;
import cn.iocoder.yudao.module.yw.vo.MemberConditionStatusVo;
import cn.iocoder.yudao.module.yw.vo.YwCourseMemberVO;
import cn.iocoder.yudao.module.yw.vo.page.YwCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwCourseRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwCourseSaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 课程")
@RestController
@RequestMapping("/yw/course")
@Validated
public class YwCourseAppController {

    @Resource
    private YwCourseService courseService;


    @GetMapping("/selectCourse")
    @Operation(summary = "获得课程分页")
    public CommonResult<List<YwCourseMemberVO>> selectCourse() {
        List<YwCourseMemberVO> pageResult = courseService.selectCourse();
        return success(BeanUtils.toBean(pageResult, YwCourseMemberVO.class));
    }

    @GetMapping("/selectCourseId")
    @Operation(summary = "获得课程分页")
    public CommonResult<YwCourseMemberVO> selectCourseId(@RequestParam("courseId") Long courseId) {
        YwCourseMemberVO pageResult = courseService.selectCourseId(courseId);
        return success(BeanUtils.toBean(pageResult, YwCourseMemberVO.class));
    }

    @PostMapping("/beginCourseMember")
    @Operation(summary = "开始课程")
    public CommonResult<YwCourseMemberVO> beginCourseMember(@RequestParam("courseId") Long courseId) {
        return success(courseService.beginCourse(courseId));
    }

    @PostMapping("/updateChapterMember")
    @Operation(summary = "答题")
    public CommonResult<Integer> updateChapterMember(@RequestBody Long chapterId) {
        return success(courseService.updateChapterMember(chapterId));
    }

    @GetMapping("/selectMemberConditionStatus")
    @Operation(summary = "获得课程分页")
    public CommonResult<MemberConditionStatusVo> selectMemberConditionStatus() {
        MemberConditionStatusVo pageResult = courseService.selectMemberConditionStatus();
        return success(BeanUtils.toBean(pageResult, MemberConditionStatusVo.class));
    }
}