package cn.iocoder.yudao.module.yw.vo.portal.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 首页资讯中心 Response VO")
@Data
public class YwPortalArticleRespVO {

    private Long id;
    private String category;
    private String title;
    private String author;
    private String content;
    private String picUrl;
    private String introduction;
    private LocalDateTime createTime;
    private Integer status;
    private String downloadurl;
}
