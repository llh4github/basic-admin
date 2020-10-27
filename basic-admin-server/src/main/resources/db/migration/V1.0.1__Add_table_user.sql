use `basic_admin`;
CREATE TABLE `sys_user`
(
    id            int auto_increment,
    `username`    varchar(100) NULL,
    `password`    varchar(100) NULL,
    `create_time` datetime     NULL ON UPDATE CURRENT_TIMESTAMP,
    `update_time` datetime     NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) COMMENT ='系统用户表' ENGINE = InnoDB
                   DEFAULT CHARSET = utf8mb4;