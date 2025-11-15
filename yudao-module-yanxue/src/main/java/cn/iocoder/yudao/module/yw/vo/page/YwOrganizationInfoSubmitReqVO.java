package cn.iocoder.yudao.module.yw.vo.page;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 组织信息页 Request VO")
@Data
public class YwOrganizationInfoSubmitReqVO {
    @NotBlank(message = "组织简介不能为空")
    private String intro;

    @NotBlank(message = "资质材料不能为空")
    private String material;
}

