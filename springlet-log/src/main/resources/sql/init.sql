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