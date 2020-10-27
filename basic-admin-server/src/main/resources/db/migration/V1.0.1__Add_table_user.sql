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