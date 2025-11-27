package cn.iocoder.yudao.module.yw.controller.admin;


import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.yw.vo.resp.AppStudyBaseDetailRespVO;
import cn.iocoder.yudao.module.yw.vo.resp.AppStudyBaseMapRespVO;
import cn.iocoder.yudao.module.yw.service.YwStudyBaseService;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwStudyBaseDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 研学基地/营地")
@RestController
@RequestMapping("/app/study-base")
@Validated
public class H5StudyBaseController {

    @Resource
    private YwStudyBaseService studyBaseService;

    @GetMapping("/map-list")
    @Operation(summary = "获取地图点位列表（前台）")
    public CommonResult<List<AppStudyBaseMapRespVO>> getMapList(
            @Parameter(description = "城市", required = false) @RequestParam(value = "city", required = false) String city,
            @Parameter(description = "主题类型", required = false) @RequestParam(value = "themeType", required = false) String themeType,
            @Parameter(description = "是否推荐", required = false) @RequestParam(value = "isRecommend", required = false) Boolean isRecommend
    ) {
        // 建议在 Service 里只返回：status=1 && xPercent/yPercent 非空 的记录
        List<YwStudyBaseDO> list = studyBaseService.getStudyBaseListForMap(city, themeType, isRecommend);
        List<AppStudyBaseMapRespVO> result = list.stream().map(this::convertToMapVO).collect(Collectors.toList());
        return success(result);
    }

    @GetMapping("/get")
    @Operation(summary = "获取研学基地详情（前台）")
    public CommonResult<AppStudyBaseDetailRespVO> getDetail(
            @Parameter(description = "基地编号", required = true)
            @RequestParam("id") @NotNull(message = "id 不能为空") Long id
    ) {
        YwStudyBaseDO base = studyBaseService.getStudyBase(id);
        if (base == null) {
            return success(null);
        }
        AppStudyBaseDetailRespVO vo = convertToDetailVO(base);
        return success(vo);
    }

    // ========== 简单转换方法（App 端只要部分字段，直接在这做转换即可） ==========

    private AppStudyBaseMapRespVO convertToMapVO(YwStudyBaseDO base) {
        AppStudyBaseMapRespVO vo = new AppStudyBaseMapRespVO();
        BeanUtils.copyProperties(base, vo);
        return vo;
    }

    private AppStudyBaseDetailRespVO convertToDetailVO(YwStudyBaseDO base) {
        AppStudyBaseDetailRespVO vo = new AppStudyBaseDetailRespVO();
        vo.setId(base.getId());
        vo.setName(base.getName());
        vo.setShortName(base.getShortName());
        vo.setProvince(base.getProvince());
        vo.setCity(base.getCity());
        vo.setDistrict(base.getDistrict());
        vo.setAddress(base.getAddress());
        vo.setThemeType(base.getThemeType());
        vo.setAgeRange(base.getAgeRange());
        vo.setIntro(base.getIntro());
        vo.setDetailContent(base.getDetailContent());
        vo.setMainImageUrl(base.getMainImageUrl());
        vo.setParkImage1Url(base.getParkImage1Url());
        vo.setParkImage2Url(base.getParkImage2Url());
        vo.setActivityImage1Url(base.getActivityImage1Url());
        vo.setActivityImage2Url(base.getActivityImage2Url());
        vo.setCourse1Desc(base.getCourse1Desc());
        vo.setCourse2Desc(base.getCourse2Desc());
        return vo;
    }

}

