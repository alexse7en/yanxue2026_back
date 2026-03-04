package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("yw_orginfo")
@KeySequence("yw_orginfo_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class YwOrgInfoDO extends BaseDO {

    @TableId
    private Long id;
    private Long memberId;
    private String orgName;
    private String payloadJson;
    private Integer status;
}
