package cn.iocoder.yudao.module.member.controller.admin.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class MemberUserUpdateTeacherReqVO {
    @NotNull(message = "用户编号不能为空")
    private Long id;

    @NotNull(message = "是否导师不能为空")
    //@Range(min = 0, max = 1, message = "导师标记只能是 0 或 1")
    private Integer izteacher;
}
