package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuDO;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperQuOptionDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 试卷题目 Response VO")
@Data
@ExcelIgnoreUnannotated
public class YwPaperQuWithOptionVo extends YwPaperQuDO {

    List<YwPaperQuOptionDO> optionList=new ArrayList<>();

}