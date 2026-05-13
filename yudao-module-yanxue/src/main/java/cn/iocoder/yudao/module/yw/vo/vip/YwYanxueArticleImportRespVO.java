package cn.iocoder.yudao.module.yw.vo.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 研学首页文章导入 Response VO")
@Data
@Builder
public class YwYanxueArticleImportRespVO {

    @Schema(description = "新增成功的文章标题数组")
    private List<String> createTitles;

    @Schema(description = "更新成功的文章标题数组")
    private List<String> updateTitles;

    @Schema(description = "导入失败的文章集合，key 为文章标题或行号，value 为失败原因")
    private Map<String, String> failureTitles;

}
