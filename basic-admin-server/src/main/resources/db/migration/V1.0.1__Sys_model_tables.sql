use `basic_admin`;
CREATE TABLE `sys_user`
(
    id             int auto_increment,

    `status`       tinyint(1)   DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `username`     varchar(30)  DEFAULT NULL,

    `created_time` datetime     DEFAULT NULL,
    `created_by`   int          DEFAULT NULL,
    `remove_flag`  tinyint(1)   DEFAULT 1,
    `updated_time` datetime     DEFAULT NULL,
    `updated_by`   int          DEFAULT NULL,
    PRIMARY KEY (`id`)
) COMMENT ='系统用户表' ENGINE = InnoDB
                   DEFAULT CHARSET = utf8mb4;

CREATE TABLE `sys_role`
(
    id             int auto_increment,

    `role_name`    varchar(30) not null comment '角色名。英文。用在代码中做权限判断。',
    `display_name` varchar(50) not null comment '显示名称',
    `remark`       varchar(250) DEFAULT NULL comment '备注',

    `created_time` datetime     DEFAULT NULL,
    `created_by`   int          DEFAULT NULL,
    `remove_flag`  tinyint(1)   DEFAULT 1,
    `updated_time` datetime     DEFAULT NULL,
    `updated_by`   int          DEFAULT NULL,
    PRIMARY KEY (`id`)
) COMMENT ='角色表' ENGINE = InnoDB
                 DEFAULT CHARSET = utf8mb4;