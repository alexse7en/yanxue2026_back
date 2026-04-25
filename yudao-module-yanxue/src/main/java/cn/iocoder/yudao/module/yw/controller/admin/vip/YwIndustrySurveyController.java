package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.service.vip.YwIndustrySurveyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveyRespVO;
import cn.iocoder.yudao.module.yw.vo.vip.YwIndustrySurveySaveReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 行业研究问卷")
@RestController
@RequestMapping("/yw/yw-industry-survey")
@Validated
public class YwIndustrySurveyController {

    @Resource
    private YwIndustrySurveyService industrySurveyService;

    @GetMapping("/get-my")
    @Operation(summary = "查询我的行业研究问卷")
    public CommonResult<YwIndustrySurveyRespVO> getMy(@RequestParam(value = "surveyType", required = false) String surveyType) {
        return success(industrySurveyService.getMyIndustrySurvey(surveyType));
    }

    @PostMapping("/create")
    @Operation(summary = "创建行业研究问卷")
    public CommonResult<Long> create(@Valid @RequestBody YwIndustrySurveySaveReqVO reqVO) {
        return success(industrySurveyService.createIndustrySurvey(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新行业研究问卷")
    public CommonResult<Boolean> update(@Valid @RequestBody YwIndustrySurveySaveReqVO reqVO) {
        industrySurveyService.updateIndustrySurvey(reqVO);
        return success(true);
    }
}
