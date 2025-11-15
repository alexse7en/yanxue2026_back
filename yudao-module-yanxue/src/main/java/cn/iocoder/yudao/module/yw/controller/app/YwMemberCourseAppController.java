package cn.iocoder.yudao.module.yw.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwMemberCourseDO;
import cn.iocoder.yudao.module.yw.service.YwMemberCourseService;
import cn.iocoder.yudao.module.yw.vo.YwMemberCourseTotalVO;
import cn.iocoder.yudao.module.yw.vo.page.YwMemberCoursePageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwMemberCourseRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwMemberCourseSaveReqVO;
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

@Tag(name = "管理后台 - 课程进度")
@RestController
@RequestMapping("/yw/member-course")
@Validated
public class YwMemberCourseAppController {

    @Resource
    private YwMemberCourseService memberCourseService;


    @GetMapping("/my")
    @Operation(summary = "获得课程进度")
    public CommonResult<List<YwMemberCourseDO>> my() {
        List<YwMemberCourseDO> memberCourse=memberCourseService.my();
        return success(memberCourse);
    }

    @GetMapping("/myFinish")
    @Operation(summary = "获得课程进度")
    public CommonResult<List<YwMemberCourseTotalVO>> myFinish() {
        List<YwMemberCourseTotalVO> memberCourse=memberCourseService.myFinish();
        return success(memberCourse);
    }

}