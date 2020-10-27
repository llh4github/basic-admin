use `basic_admin`;
ALTER TABLE `sys_user`
    ADD COLUMN `status` tinyint(3) NULL DEFAULT NULL COMMENT '用户状态' AFTER `update_time`;