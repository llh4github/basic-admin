SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `name`         varchar(30) NOT NULL COMMENT '权限名。英文。用在代码中做权限判断。',
    `remark`       varchar(250) DEFAULT NULL COMMENT '备注',
    `created_time` datetime     DEFAULT NULL,
    `created_by`   int(11)      DEFAULT NULL,
    `remove_flag`  tinyint(1)   DEFAULT '0',
    `updated_time` datetime     DEFAULT NULL,
    `updated_by`   int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='权限表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `role_name`    varchar(30) NOT NULL COMMENT '角色名。英文。用在代码中做权限判断。',
    `display_name` varchar(50) NOT NULL COMMENT '显示名称',
    `remark`       varchar(250) DEFAULT NULL COMMENT '备注',
    `created_time` datetime     DEFAULT NULL,
    `created_by`   int(11)      DEFAULT NULL,
    `remove_flag`  tinyint(1)   DEFAULT '0',
    `updated_time` datetime     DEFAULT NULL,
    `updated_by`   int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `role_id`      int(11) NOT NULL,
    `authority_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_role_authority` (`role_id`, `authority_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色-权限关系表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `status`       tinyint(1)   DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `username`     varchar(30)  DEFAULT NULL,
    `created_time` datetime     DEFAULT NULL,
    `created_by`   int(11)      DEFAULT NULL,
    `remove_flag`  tinyint(1)   DEFAULT '0',
    `updated_time` datetime     DEFAULT NULL,
    `updated_by`   int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `role_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_role_id_user_id` (`role_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-角色关系表';
