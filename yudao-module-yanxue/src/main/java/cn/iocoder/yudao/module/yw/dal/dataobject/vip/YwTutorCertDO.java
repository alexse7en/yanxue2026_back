package cn.iocoder.yudao.module.yw.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 导师证书检索 DO
 */
@TableName("yx_certificate_retrieval1")
@KeySequence("yx_certificate_retrieval1_seq")
@Data
public class YwTutorCertDO {

    @TableId
    private Long id;
    private String name;
    private String peopleId;
    private String certificateNo;
    private String sex;
    private String post;
    private Double score;
    private String grade;
    private String qrCode;
    private String avatar;
    private String effectiveData;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
