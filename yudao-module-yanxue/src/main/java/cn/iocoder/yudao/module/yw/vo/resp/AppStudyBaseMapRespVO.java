package cn.iocoder.yudao.module.yw.vo.resp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

@Schema(description = "用户 APP - 研学基地/营地 地图点位 Response VO")
@Data
public class AppStudyBaseMapRespVO {

    @Schema(description = "基地编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "基地（营地）全称", example = "珠海某研学营地")
    private String name;

    @Schema(description = "地图短名称/按钮名称", example = "珠海营地")
    private String shortName;

    @Schema(description = "城市", example = "珠海市")
    private String city;

    @Schema(description = "主题类型", example = "科技创新")
    private String themeType;

    @Schema(description = "地图 X 坐标（0-100 百分比）", example = "35.20")
    private BigDecimal xPercent;

    @Schema(description = "地图 Y 坐标（0-100 百分比）", example = "48.70")
    private BigDecimal yPercent;

    @Schema(description = "是否推荐：0=否 1=是", example = "true")
    private Boolean isRecommend;

}

