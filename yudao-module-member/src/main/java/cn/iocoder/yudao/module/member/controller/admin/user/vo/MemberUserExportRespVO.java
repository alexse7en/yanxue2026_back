package cn.iocoder.yudao.module.member.controller.admin.user.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
//import cn.iocoder.yudao.framework.excel.core.convert.LocalDateTimeExcelConverter;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberUserExportRespVO {

    @ExcelProperty("用户编号")
    private Long id;

    @ExcelProperty("手机号")
    private String mobile;

    @ExcelProperty("昵称")
    private String nickname;

    @ExcelProperty("等级")
    private String levelName;

    @ExcelProperty("实名")
    private String name;

    @ExcelProperty("身份证")
    private String idcard;

    @ExcelProperty("所属组织")
    private String orgName;

    @ExcelProperty("所属院校")
    private String orgSchool;

    @ExcelProperty("所属专业")
    private String orgProfession;

    @ExcelProperty("所属地区")
    private String areaName;

    @ExcelProperty(value = "是否评审，1为是，0为否")
    private Integer izteacher; // 0/1

/*    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;*/


}
