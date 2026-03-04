-- 仅补充/修正真实库表结构（不再创建临时表名）

-- 1) VIP 信息扩展字段
ALTER TABLE `yw_yanxue_vipinfo`
    ADD COLUMN IF NOT EXISTS `membership_start_date` date DEFAULT NULL COMMENT '会员有效期开始日期',
    ADD COLUMN IF NOT EXISTS `membership_end_date` date DEFAULT NULL COMMENT '会员有效期结束日期',
    ADD COLUMN IF NOT EXISTS `token_balance` decimal(10,2) DEFAULT '0.00' COMMENT '会员代币余额';

-- 2) orginfo 表重复列修正：若存在第二个 fulltime_tutor_count，请重命名为 cert_fulltime_tutor_count
-- 注意：以下语句需要根据线上实际列名执行；若已修正可跳过。
-- ALTER TABLE `yw_yanxue_orginfo` RENAME COLUMN `fulltime_tutor_count` TO `cert_fulltime_tutor_count`;

-- 3) 如不存在则创建 yw_yanxue_orginfo_apply
CREATE TABLE IF NOT EXISTS `yw_yanxue_orginfo_apply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `orginfo_id` bigint(20) NOT NULL COMMENT '关联二级认证信息表ID',
    `user_id` bigint(20) NOT NULL COMMENT '提交申请的用户ID',
    `apply_status` tinyint(4) DEFAULT '0' COMMENT '申请状态：0-待审核 1-审核通过 2-审核拒绝',
    `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注/拒绝原因',
    `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
    `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
    `unit_name` varchar(200) DEFAULT NULL COMMENT '单位名称',
    `destination_name` varchar(200) DEFAULT NULL COMMENT '目的地名称',
    `base_theme` varchar(100) DEFAULT NULL COMMENT '基地主题',
    `unit_type` varchar(50) DEFAULT NULL COMMENT '单位性质',
    `address` varchar(200) DEFAULT NULL COMMENT '通讯地址',
    `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',
    `contact_phone` varchar(20) DEFAULT NULL COMMENT '电话',
    `contact_email` varchar(100) DEFAULT NULL COMMENT '电子信箱',
    `fulltime_tutor_count` int(11) DEFAULT '0' COMMENT '专职研学指导师人数',
    `cert_fulltime_tutor_count` int(11) DEFAULT NULL COMMENT '持证专职导师人数',
    `parttime_tutor_count` int(11) DEFAULT '0' COMMENT '兼职研学指导师人数',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    KEY `idx_orginfo_id` (`orginfo_id`),
    KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='二级认证信息编辑申请表';
