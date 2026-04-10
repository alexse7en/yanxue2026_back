CREATE TABLE `yw_yanxue_cert_student_apply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL COMMENT '上传会员用户ID',
    `vipinfo_id` bigint(20) NOT NULL COMMENT '关联会员单位信息ID',
    `apply_no` varchar(50) NOT NULL COMMENT '申请编号',
    `file_path` varchar(500) DEFAULT NULL COMMENT '原始上传 Excel OSS 路径',
    `file_type` varchar(20) DEFAULT NULL COMMENT '文件类型',
    `parse_status` tinyint(4) DEFAULT '0' COMMENT '解析状态：0-未解析 1-解析成功 2-解析失败',
    `parse_error` varchar(500) DEFAULT NULL COMMENT '解析错误信息',
    `parse_count` int(11) DEFAULT '0' COMMENT '解析出的学生数量',
    `cert_status` tinyint(4) DEFAULT '0' COMMENT '证书状态：0-待生成 1-生成中 2-已生成 3-生成失败',
    `cert_no` varchar(50) DEFAULT NULL COMMENT '用于列表展示的预览证书编号（通常取首张）',
    `cert_name` varchar(100) DEFAULT NULL COMMENT '证书名称',
    `cert_url` varchar(500) DEFAULT NULL COMMENT '用于列表预览的证书图片地址（通常取首张）',
    `download_url` varchar(500) DEFAULT NULL COMMENT '批量下载 zip 地址',
    `finish_time` datetime DEFAULT NULL COMMENT '生成完成时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` tinyint(1) DEFAULT '0',
    `tenant_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_vipinfo_id` (`vipinfo_id`),
    KEY `idx_apply_no` (`apply_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生证书生成申请主表';

-- 说明：
-- 1. 明细表继续复用现有 yw_yanxue_cert_student
-- 2. 目前代码使用 yw_yanxue_cert_student.upload_file_path 与主表 file_path 关联同一批上传
-- 3. 若你后续愿意升级结构，建议再给明细表补一个 apply_id 字段，关联会更稳
