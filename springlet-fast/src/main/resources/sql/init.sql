-- 数据库:  demo
-- 字符集:  ut8mb4
-- 排序规则：utf8mb4_general_ci

create table `demo`
(
    `id`              bigint(20)  not null COMMENT '主键',
    `phone`           varchar(32) not null comment '手机号，唯一索引',
    `amount`          bigint(20)     default 0 comment '金额',
    `enum_type`       varchar(32)    default '' comment '枚举类型',
    `enum_type_value` varchar(68)    default '' comment '枚举类型值',

    `lng`             decimal(10, 6) DEFAULT NULL COMMENT '经度',
    `lat`             decimal(10, 6) DEFAULT NULL COMMENT '纬度',

    `remark`          varchar(1024)  default '' comment '备注',
    `version`         bigint(20)     default 0 comment '乐观锁',
    `gmt_create`      datetime       default null comment '创建时间',
    `gmt_modified`    datetime       default null comment '修改时间',
    `is_deleted`      bigint(20)     default 0 comment '逻辑是删除 0未删除 其它 已删除，默认删除后此值为主键',
    primary key (`id`),
    unique index `idx_phone_is_deleted` (`phone`, `is_deleted`)
) comment = 'demo' collate = 'utf8mb4_general_ci'
                   engine = innodb;