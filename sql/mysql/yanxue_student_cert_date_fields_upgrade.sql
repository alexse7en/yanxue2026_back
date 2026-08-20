ALTER TABLE `yw_yanxue_student_apply`
    ADD COLUMN `course_date` date DEFAULT NULL COMMENT '课程日期' AFTER `cert_date`,
    ADD COLUMN `stamp_date` date DEFAULT NULL COMMENT '盖章日期' AFTER `course_date`;

ALTER TABLE `yw_yanxue_cert_student`
    ADD COLUMN `course_date` date DEFAULT NULL COMMENT '课程日期' AFTER `cert_date`,
    ADD COLUMN `stamp_date` date DEFAULT NULL COMMENT '盖章日期' AFTER `course_date`;
