-- VIP / ORG apply related tables
CREATE TABLE IF NOT EXISTS `yw_vip_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `apply_status` tinyint NOT NULL DEFAULT 0 COMMENT '0草稿 1已提交',
  `payload_json` longtext NULL,
  `submit_time` datetime NULL,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `yw_yanxue_vipinfo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `membership_start_date` date NULL,
  `membership_end_date` date NULL,
  `token_balance` decimal(18,2) NOT NULL DEFAULT 0,
  `payload_json` longtext NULL,
  `status` tinyint NOT NULL DEFAULT 1,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `yw_vipinfo_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vipinfo_id` bigint NULL,
  `member_id` bigint NOT NULL,
  `apply_status` tinyint NOT NULL DEFAULT 1 COMMENT '1待审核 2通过 3驳回',
  `payload_json` longtext NULL,
  `submit_time` datetime NULL,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `yw_org_apply_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `apply_type` varchar(32) NOT NULL,
  `apply_status` tinyint NOT NULL DEFAULT 0 COMMENT '0草稿 1已提交',
  `payload_json` longtext NULL,
  `submit_time` datetime NULL,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_member_type` (`member_id`,`apply_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `yw_orginfo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `org_name` varchar(255) NULL,
  `payload_json` longtext NULL,
  `status` tinyint NOT NULL DEFAULT 1,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `yw_orginfo_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `orginfo_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  `apply_status` tinyint NOT NULL DEFAULT 1 COMMENT '1待审核 2通过 3驳回',
  `payload_json` longtext NULL,
  `submit_time` datetime NULL,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_member_org` (`member_id`,`orginfo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 增量变更：若已有表则补字段
ALTER TABLE `yw_yanxue_vipinfo` ADD COLUMN IF NOT EXISTS `membership_start_date` date NULL;
ALTER TABLE `yw_yanxue_vipinfo` ADD COLUMN IF NOT EXISTS `membership_end_date` date NULL;
ALTER TABLE `yw_yanxue_vipinfo` ADD COLUMN IF NOT EXISTS `token_balance` decimal(18,2) NOT NULL DEFAULT 0;
