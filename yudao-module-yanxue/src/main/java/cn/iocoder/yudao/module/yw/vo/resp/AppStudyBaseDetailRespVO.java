package cn.iocoder.yudao.module.yw.vo.resp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 研学基地/营地 详情 Response VO")
@Data
public class AppStudyBaseDetailRespVO {

    @Schema(description = "基地编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "基地（营地）全称", example = "珠海某研学营地")
    private String name;

    @Schema(description = "地图短名称/按钮名称", example = "珠海营地")
    private String shortName;

    @Schema(description = "省份", example = "广东省")
    private String province;

    @Schema(description = "城市", example = "珠海市")
    private String city;

    @Schema(description = "区/县", example = "香洲区")
    private String district;

    @Schema(description = "详细地址", example = "广东省珠海市香洲区某大道 88 号")
    private String address;

    @Schema(description = "主题类型", example = "科技创新")
    private String themeType;

    @Schema(description = "适配年龄段", example = "8-12岁")
    private String ageRange;

    @Schema(description = "研学基地（营地）简介（摘要）", example = "基地位于珠海市……")
    private String intro;

    @Schema(description = "基地详情富文本（HTML）")
    private String detailContent;

    @Schema(description = "主图1张")
    private String mainImageUrl;

    @Schema(description = "园区图片1")
    private String parkImage1Url;

    @Schema(description = "园区图片2")
    private String parkImage2Url;

    @Schema(description = "研学活动图片1")
    private String activityImage1Url;

    @Schema(description = "研学活动图片2")
    private String activityImage2Url;

    @Schema(description = "课程1简介（文本大致简介）")
    private String course1Desc;

    @Schema(description = "课程2简介（文本大致简介）")
    private String course2Desc;

}

