-- 数据库:  demo
-- 字符集:  ut8mb4
-- 排序规则：utf8mb4_general_ci

create table `demo`
(
    `id`              bigint(20)  not null COMMENT '主键',
    `phone`           varchar(32) not null comment '手机号，唯一索引',
    `unit_price`      bigint(20)     default 0 comment '金额',
    `enum_type`       varchar(32)    default '' comment '枚举类型[cn.springlet.core.enums.YesNoEnum]',
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


-- 系统日志sql
create table `sys_opt_log`
(
    `id`              bigint(20) not null COMMENT '主键',
    `opt_id`          varchar(25)   DEFAULT NULL COMMENT '操作人id',
    `opt_account`     varchar(128)  DEFAULT NULL COMMENT '操作人账号',
    `opt_name`        varchar(128)  DEFAULT NULL COMMENT '操作人名称',
    `opt_ip`          varchar(20)   DEFAULT NULL COMMENT '操作人ip',
    `request_url`     varchar(1000) DEFAULT NULL COMMENT '请求地址',
    `request_method`  varchar(20)   DEFAULT NULL COMMENT '请求方式',
    `request_params`  longtext COMMENT '请求参数',
    `response_params` longtext COMMENT '返回参数',
    `exec_time`       bigint(20)    DEFAULT NULL COMMENT '执行时长 ms',

    `remote_addr`     varchar(20)   DEFAULT NULL COMMENT '服务器ip',
    `request_class`   varchar(255)  DEFAULT NULL COMMENT '请求类方法',
    `opt_desc`        varchar(255)  DEFAULT NULL COMMENT '操作描述',
    `opt_type`        varchar(10)   DEFAULT NULL COMMENT '操作类型',
    `gmt_create`      datetime      default null comment '创建时间',
    primary key (`id`),
    index `idx_opt_name` (`opt_name`)
) comment = 'sys_opt_log' collate = 'utf8mb4_general_ci'
                          engine = innodb;