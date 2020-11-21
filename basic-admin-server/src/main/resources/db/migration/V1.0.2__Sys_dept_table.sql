drop table if exists `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `name`         varchar(30) NOT NULL COMMENT '部门名',
    `remark`       varchar(250) DEFAULT NULL COMMENT '备注',
    `parent_id`    int(11)      DEFAULT '0' COMMENT '父部门id，默认为0',
    `sort_no`      tinyint(3)   DEFAULT '0' COMMENT '显示顺序',
    `created_time` datetime     DEFAULT NULL,
    `created_by`   int(11)      DEFAULT NULL,
    `remove_flag`  tinyint(1)   DEFAULT '0',
    `updated_time` datetime     DEFAULT NULL,
    `updated_by`   int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_dept` (`name`, `remove_flag`) COMMENT '部门名唯一'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门信息表';

CREATE TABLE `sys_dept_user`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `dept_id` int(11) NOT NULL COMMENT '部门id',
    `user_id` int(11) NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门-用户信息关联表';
