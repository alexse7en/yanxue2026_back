package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwCourseDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 课程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwCourseMemberVO extends YwCourseDO {
    private String memberId;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 是否完成 1（true）已完成， 0（false）未完成，默认0
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Integer accomplishFlag;
    /**
     * 已学习小节数目，默认0
     */
    private Integer learningChapterNum;
    /**
     * 课程小节总数
     */
    private Integer allChapterNum;
    /**
     * 上次学习的小节id
     */
    private String lastChapterId;
    
    List<YwChapterMemberVO> list=new ArrayList<>();

}