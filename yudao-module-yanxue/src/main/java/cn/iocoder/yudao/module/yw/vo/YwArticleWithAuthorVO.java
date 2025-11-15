package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwArticleDO;
import cn.iocoder.yudao.module.yw.vo.page.YwArticlePageReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 文章管理分页 Request VO")
@Data
public class YwArticleWithAuthorVO extends YwArticleDO {

    String name;
    String avatar;

}