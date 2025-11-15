package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwChapterDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 章节 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwChapterMemberVO extends YwChapterDO {

    private String memberId;
    /**
     * 小节id
     */
    private String chapterId;
    /**
     * 已学习时长（s）
     */
    private Double learningTime;
    /**
     * 小节总时长（s）
     */
    private Double duration;
    /**
     * 小节是否学习完成，1（true）已完成， 0（false）未完成，默认0
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Integer accomplishFlag;
    /**
     * 上次观看视频时长记录（s）
     */
    private Double lastTime;

}