package cn.iocoder.yudao.module.yw.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.yw.dal.dataobject.YwPaperDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 试卷分页 Request VO")
@Data
public class YwPaperWithQuVO extends YwPaperDO {

    List<YwPaperQuWithOptionVo> quList=new ArrayList<>();

}