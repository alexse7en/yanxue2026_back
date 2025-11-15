package cn.iocoder.yudao.module.yw.controller.app;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.service.YwPaperService;
import cn.iocoder.yudao.module.yw.vo.YwExamTotalVo;
import cn.iocoder.yudao.module.yw.vo.YwPaperEntity;
import cn.iocoder.yudao.module.yw.vo.YwPaperQuEntity;
import cn.iocoder.yudao.module.yw.vo.page.YwPaperPageReqVO;
import cn.iocoder.yudao.module.yw.vo.resp.YwPaperRespVO;
import cn.iocoder.yudao.module.yw.vo.save.YwPaperSaveReqVO;
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

@Tag(name = "管理后台 - 试卷")
@RestController
@RequestMapping("/yw/paper")
@Validated
public class YwPaperAppController {

    @Resource
    private YwPaperService paperService;


    @PostMapping("/beginPaper")
    @Operation(summary = "开始考试")
    public CommonResult<YwPaperEntity> beginPaper(@RequestParam("courseId") Long courseId, @RequestParam("examId") Long examId) {
        return success(paperService.beginPaper(courseId, examId));
    }

    @GetMapping("/beginPaperSimple")
    @Operation(summary = "开始考试")
    public CommonResult<YwExamTotalVo> beginPaperSimple(@RequestParam("examId") Long examId) {
        return success(paperService.beginPaperSimple(examId));
    }

    @PostMapping("/submitPaperSimple")
    @Operation(summary = "开始考试")
    public CommonResult<Boolean> submitPaperSimple(@RequestBody YwExamTotalVo exam) {
        return success(paperService.submitPaperSimple(exam));
    }

    @PostMapping("/updatePaperQu")
    @Operation(summary = "答题")
    public CommonResult<Boolean> updatePaperQu(@RequestBody YwPaperQuEntity entity) {
        return success(paperService.submitPaperQu(entity));
    }

    @PostMapping("/submit")
    @Operation(summary = "交卷")
    public CommonResult<YwPaperDO> submit(@RequestParam("paperId") Long paperId) {
        return success(paperService.submit(paperId));
    }
}