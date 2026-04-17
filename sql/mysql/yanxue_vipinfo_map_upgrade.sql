ALTER TABLE `yw_yanxue_vipinfo`
    ADD COLUMN `longitude` DECIMAL(10, 6) NULL COMMENT '经度' AFTER `rep_email`,
    ADD COLUMN `latitude` DECIMAL(10, 6) NULL COMMENT '纬度' AFTER `longitude`,
    ADD COLUMN `map_address` VARCHAR(255) NULL COMMENT '地图展示地址' AFTER `latitude`;

ALTER TABLE `yw_yanxue_vipinfo_apply`
    ADD COLUMN `longitude` DECIMAL(10, 6) NULL COMMENT '经度' AFTER `rep_email`,
    ADD COLUMN `latitude` DECIMAL(10, 6) NULL COMMENT '纬度' AFTER `longitude`,
    ADD COLUMN `map_address` VARCHAR(255) NULL COMMENT '地图展示地址' AFTER `latitude`;
