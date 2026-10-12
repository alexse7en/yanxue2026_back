-- 会员证书生成次数修正脚本
-- 当前尚无实际用户使用时，可将四类会员的剩余次数统一重置为标准额度。
-- 如果上线后已有证书消耗记录，请勿直接重复执行本脚本，避免恢复已经使用的次数。

-- 1. 执行前核对各等级当前数据
SELECT TRIM(`member_level`) AS `member_level`,
       COUNT(*) AS `member_count`,
       MIN(`token_balance`) AS `min_token_balance`,
       MAX(`token_balance`) AS `max_token_balance`
FROM `yw_yanxue_vipinfo`
WHERE TRIM(`member_level`) IN ('会员单位', '理事单位', '常务理事单位', '副会长单位')
GROUP BY TRIM(`member_level`)
ORDER BY FIELD(TRIM(`member_level`), '会员单位', '理事单位', '常务理事单位', '副会长单位');

START TRANSACTION;

-- 2. 清理等级字段首尾空格，并按正式规则重置证书生成次数
UPDATE `yw_yanxue_vipinfo`
SET `member_level` = TRIM(`member_level`),
    `token_balance` = CASE TRIM(`member_level`)
        WHEN '会员单位' THEN 200
        WHEN '理事单位' THEN 500
        WHEN '常务理事单位' THEN 1000
        WHEN '副会长单位' THEN 2000
        ELSE `token_balance`
    END
WHERE TRIM(`member_level`) IN ('会员单位', '理事单位', '常务理事单位', '副会长单位');

-- 3. 执行后核对；确认无误后执行 COMMIT，发现异常则改为 ROLLBACK
SELECT TRIM(`member_level`) AS `member_level`,
       COUNT(*) AS `member_count`,
       MIN(`token_balance`) AS `min_token_balance`,
       MAX(`token_balance`) AS `max_token_balance`
FROM `yw_yanxue_vipinfo`
WHERE TRIM(`member_level`) IN ('会员单位', '理事单位', '常务理事单位', '副会长单位')
GROUP BY TRIM(`member_level`)
ORDER BY FIELD(TRIM(`member_level`), '会员单位', '理事单位', '常务理事单位', '副会长单位');

-- 核对无误后手动执行：
-- COMMIT;

-- 如核对结果异常，手动执行：
-- ROLLBACK;

