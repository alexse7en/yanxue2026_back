package cn.iocoder.yudao.module.yw.controller.admin.vip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.yw.service.vip.YwOrgInfoApplyService;
import cn.iocoder.yudao.module.yw.vo.vip.YwOrgInfoRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二级认证展示资料")
@RestController
@RequestMapping("/yw/yw-orginfo")
public class YwOrgInfoController {

    @Resource
    private YwOrgInfoApplyService orgInfoApplyService;

    @GetMapping("/list-my")
    @Operation(summary = "查询我的已认证单位列表")
    //@PreAuthorize("@ss.hasPermission('yw:yw-orginfo:query')")
    public CommonResult<List<YwOrgInfoRespVO>> listMy() {
        return success(orgInfoApplyService.listMyOrgInfo());
    }
}
