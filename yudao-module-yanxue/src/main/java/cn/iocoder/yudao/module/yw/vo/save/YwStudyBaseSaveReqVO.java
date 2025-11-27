package cn.iocoder.yudao.module.yw.vo.save;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 研学基地/营地新增/修改 Request VO")
@Data
public class YwStudyBaseSaveReqVO {

    @Schema(description = "编号（主键）", requiredMode = Schema.RequiredMode.REQUIRED, example = "14439")
    private Long id;

    @Schema(description = "基地（营地）全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "基地（营地）全称不能为空")
    private String name;

    @Schema(description = "地图短名称/按钮名称", example = "王五")
    private String shortName;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "区/县")
    private String district;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "主题类型（如红色教育、科技创新等）", example = "2")
    private String themeType;

    @Schema(description = "适配年龄段（如8-12岁、小学高年级等）")
    private String ageRange;

    @Schema(description = "研学基地（营地）简介（摘要）")
    private String intro;

    @Schema(description = "基地详情富文本（可选）")
    private String detailContent;

    @Schema(description = "主图1张", example = "https://www.iocoder.cn")
    private String mainImageUrl;

    @Schema(description = "园区图片1", example = "https://www.iocoder.cn")
    private String parkImage1Url;

    @Schema(description = "园区图片2", example = "https://www.iocoder.cn")
    private String parkImage2Url;

    @Schema(description = "研学活动图片1", example = "https://www.iocoder.cn")
    private String activityImage1Url;

    @Schema(description = "研学活动图片2", example = "https://www.iocoder.cn")
    private String activityImage2Url;

    @Schema(description = "课程1简介（文本大致简介）")
    private String course1Desc;

    @Schema(description = "课程2简介（文本大致简介）")
    private String course2Desc;

    @Schema(description = "地图 X 坐标（0-100 百分比）")
    private BigDecimal xPercent;

    @Schema(description = "地图 Y 坐标（0-100 百分比）")
    private BigDecimal yPercent;

    @Schema(description = "是否推荐：0=否 1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否推荐：0=否 1=是不能为空")
    private Boolean isRecommend;

    @Schema(description = "状态：0=禁用 1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态：0=禁用 1=启用不能为空")
    private Boolean status;

    @Schema(description = "显示排序（越大越前）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示排序（越大越前）不能为空")
    private Integer sort;

}
