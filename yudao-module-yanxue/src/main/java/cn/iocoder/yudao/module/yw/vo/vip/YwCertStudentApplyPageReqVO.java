package cn.iocoder.yudao.module.yw.vo.vip;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 学生证书生成申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwCertStudentApplyPageReqVO extends PageParam {

    @Schema(description = "搜索关键字，支持申请编号/证书编号/证书名称")
    private String keyword;
}
