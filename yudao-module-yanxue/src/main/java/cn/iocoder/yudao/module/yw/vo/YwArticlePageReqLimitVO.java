package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 文章管理分页 Request VO")
@Data
public class YwArticlePageReqLimitVO extends YwArticlePageReqVO {

    Integer limit;
    String classification;
}