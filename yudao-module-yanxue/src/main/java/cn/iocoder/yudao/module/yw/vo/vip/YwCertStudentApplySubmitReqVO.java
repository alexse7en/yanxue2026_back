package cn.iocoder.yudao.module.yw.vo.vip;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YwCertStudentApplySubmitReqVO {

    @NotNull(message = "申请批次 ID 不能为空")
    private Long id;
}
