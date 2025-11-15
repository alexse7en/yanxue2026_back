package cn.iocoder.yudao.module.yw.enums;// TODO 待办：请将下面的错误码复制到 yudao-module-yw 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！
// ========== 教师 TODO 补充编号 ==========


import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    public ErrorCode TEACHER_NOT_EXISTS = new ErrorCode(10001, "教师不存在");
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

}

