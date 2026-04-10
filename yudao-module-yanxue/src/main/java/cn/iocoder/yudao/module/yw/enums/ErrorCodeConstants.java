package cn.iocoder.yudao.module.yw.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode TEACHER_NOT_EXISTS = new ErrorCode(10001, "教师不存在");
    ErrorCode COURSE_NOT_EXISTS = new ErrorCode(10101, "课程不存在");
    ErrorCode CHAPTER_NOT_EXISTS = new ErrorCode(10201, "章节不存在");

    ErrorCode COURSE_CATEGORY_NOT_EXISTS = new ErrorCode(10301, "课程分类不存在");

    ErrorCode MEMBER_COURSE_NOT_EXISTS = new ErrorCode(10401, "课程进度不存在");

    ErrorCode MEMBER_CHAPTER_NOT_EXISTS = new ErrorCode(10501, "章节进度不存在");
    ErrorCode QU_NOT_EXISTS = new ErrorCode(10601, "试题不存在");
    ErrorCode QU_OPTION_NOT_EXISTS = new ErrorCode(10701, "试题选项不存在");
    ErrorCode EXAM_NOT_EXISTS = new ErrorCode(10801, "考卷设计不存在");
    ErrorCode PAPER_QU_NOT_EXISTS = new ErrorCode(10901, "试卷题目不存在");
    ErrorCode PAPER_QU_OPTION_NOT_EXISTS = new ErrorCode(101001, "试卷选项不存在");
    ErrorCode PAPER_NOT_EXISTS = new ErrorCode(101101, "试卷不存在");

    ErrorCode ARTICLE_NOT_EXISTS = new ErrorCode(101201, "文章管理不存在");

    ErrorCode YW_LEVEL_GROUP_NOT_EXISTS = new ErrorCode(101301, "评审种类不存在");

    ErrorCode YW_AUTH_COMMENT_STATUS_NOT_EXISTS = new ErrorCode(101401, "专家评审状态不存在");
    ErrorCode YW_AUTH_COMMENT_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_AUTH_CONDITION_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_AUTH_DETAIL_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_AUTH_LEVEL_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_LEVEL_GROUP_DETAIL_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_LEVEL_GROUP_NORM_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_LEVEL_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");
    ErrorCode YW_LEVEL_CONDITION_NOT_EXISTS = new ErrorCode(101501, "专家评审不存在");

    ErrorCode MEMBER_LEVEL_NOT_EXISTS = new ErrorCode(101601, "用户等级不存在");
    ErrorCode YW_MEMBER_APPLY_NOT_EXISTS = new ErrorCode(101801, "用户挂靠不存在");

    ErrorCode AUTH_PRODUCT_NOT_EXISTS = new ErrorCode(101701, "作品申请不存在");

    ErrorCode MEMBER_LEVEL_ADDRESS_NOT_EXISTS = new ErrorCode(101801, "用户收件地址不存在");

    ErrorCode YW_ARTICLE_UPVOTE_NOT_EXISTS = new ErrorCode(101901, "文章管理不存在");
    ErrorCode STUDY_BASE_NOT_EXISTS = new ErrorCode(102001, "基地组织不存在");
    ErrorCode YW_PORTAL_CERT_QUERY_CONDITION_REQUIRED = new ErrorCode(102101, "证书查询需提供证书编号，或同时提供姓名和身份证后缀");
    ErrorCode YW_VIP_APPLY_NOT_EXISTS = new ErrorCode(102201, "会员申请不存在");
    ErrorCode YW_VIP_APPLY_STATUS_NOT_SUBMITTED = new ErrorCode(102202, "仅已提交待审核的会员申请可审核");
    ErrorCode YW_VIP_APPLY_AUDIT_STATUS_INVALID = new ErrorCode(102203, "会员申请审核状态非法");
    ErrorCode YW_VIP_APPLY_MEMBER_NO_REQUIRED = new ErrorCode(102204, "审核通过时会员编号不能为空");
    ErrorCode YW_VIP_APPLY_MEMBER_NO_INVALID = new ErrorCode(102205, "会员编号格式不正确");
    ErrorCode YW_VIP_APPLY_MEMBER_NO_DUPLICATE = new ErrorCode(102206, "会员编号已存在");
    ErrorCode YW_VIPINFO_NOT_EXISTS = new ErrorCode(102301, "会员展示信息不存在");
    ErrorCode YW_VIPINFO_APPLY_NOT_EXISTS = new ErrorCode(102302, "会员展示信息编辑申请不存在");
    ErrorCode YW_VIPINFO_APPLY_DUPLICATE_PENDING = new ErrorCode(102303, "当前存在待审核中的展示信息申请，暂不可重复申请");
    ErrorCode YW_VIPINFO_APPLY_STATUS_NOT_PENDING = new ErrorCode(102304, "仅待审核的展示信息申请可审核");
    ErrorCode YW_VIPINFO_APPLY_AUDIT_STATUS_INVALID = new ErrorCode(102305, "会员展示信息申请审核状态非法");
    ErrorCode YW_VIPINFO_APPLY_QUERY_PARAM_REQUIRED = new ErrorCode(102306, "查询会员展示信息申请时必须提供 id 或 userId");
    ErrorCode YW_ORG_APPLY_NOT_EXISTS = new ErrorCode(102401, "机构认证申请不存在");
    ErrorCode YW_ORG_APPLY_DUPLICATE_PENDING = new ErrorCode(102402, "当前机构类型存在待审核申请，暂不可重复提交");
    ErrorCode YW_ORG_APPLY_STATUS_NOT_SUBMITTED = new ErrorCode(102403, "仅已提交待审核的机构认证申请可审核");
    ErrorCode YW_ORG_APPLY_AUDIT_STATUS_INVALID = new ErrorCode(102404, "机构认证申请审核状态非法");
    ErrorCode YW_ORG_APPLY_UNIT_NAME_DUPLICATE = new ErrorCode(102405, "相同单位名称仅可存在一条未通过申请");
    ErrorCode YW_ORGINFO_NOT_EXISTS = new ErrorCode(102451, "机构展示信息不存在");
    ErrorCode YW_ORGINFO_APPLY_NOT_EXISTS = new ErrorCode(102452, "机构展示信息编辑申请不存在");
    ErrorCode YW_ORGINFO_APPLY_DUPLICATE_PENDING = new ErrorCode(102453, "当前机构存在待审核中的展示信息申请，暂不可重复申请");
    ErrorCode YW_ORGINFO_APPLY_STATUS_NOT_PENDING = new ErrorCode(102454, "仅待审核的机构展示信息申请可审核");
    ErrorCode YW_ORGINFO_APPLY_AUDIT_STATUS_INVALID = new ErrorCode(102455, "机构展示信息申请审核状态非法");
    ErrorCode YW_CERT_STUDENT_APPLY_NOT_EXISTS = new ErrorCode(102501, "学生证书生成申请不存在");
    ErrorCode YW_CERT_STUDENT_APPLY_PARSE_FILE_TYPE_INVALID = new ErrorCode(102502, "学生证书仅支持上传 xls 或 xlsx 文件");
    ErrorCode YW_CERT_STUDENT_APPLY_PARSE_EMPTY = new ErrorCode(102503, "Excel 未解析出有效学生数据");
    ErrorCode YW_CERT_STUDENT_APPLY_SUBMIT_STATUS_INVALID = new ErrorCode(102504, "当前申请状态不允许提交证书生成");
    ErrorCode YW_CERT_STUDENT_APPLY_PARSE_REQUIRED = new ErrorCode(102505, "请先完成 Excel 解析后再提交生成");
    ErrorCode YW_CERT_STUDENT_APPLY_TOKEN_NOT_ENOUGH = new ErrorCode(102506, "会员单位证书生成次数不足");
    ErrorCode YW_CERT_STUDENT_APPLY_TEMPLATE_NOT_CONFIG = new ErrorCode(102507, "证书底版或电子章图片未配置");
}
