
-- 创建用户
create user stori_accting_app_dev
    identified by 'admin';

-- 创建schema并附权
create schema stori_accting;
grant alter, create, event, execute, insert, select, update on stori_accting.* to stori_accting_app_dev;

-- 建表

# 序列生成表
create table if not exists SYS_SEQUENCES
(
    ID            bigint auto_increment comment '自增主键'
    primary key,
    SEQUENCE_NAME varchar(64) not null comment '序列名',
    START_BY      bigint      not null comment 'SEQUENCE的最开始值',
    INCREMENT_BY  bigint(10)  not null comment 'SEQUENCE的自增长度',
    LAST_NUMBER   bigint      not null comment 'SEQUENCE的当前值初始化为开始值',
    constraint UK_SEQUENCE_NAME unique (SEQUENCE_NAME)
    comment '序列生成表'
    );

-- 初始化
INSERT INTO SYS_SEQUENCES (ID, SEQUENCE_NAME, START_BY, INCREMENT_BY, LAST_NUMBER)
VALUES (1, 'STORI_ACC_SEQ', 1, 50, 1);

# 账户表
create table if not exists TB_ACCOUNT
(
    ID          bigint auto_increment comment '自增主键' primary key,
    ACCOUNT_ID  varchar(32)                              not null comment '账户ID',
    NAME_ENC    varchar(512)                             not null comment '姓名密文(SM4)',
    NAME_INDEX  varchar(256)                             not null comment '姓名索引(SM3)',
    STATUS      varchar(16)                              not null comment '账户状态。NORMAL:正常；PAUSE:暂停；CANCELED:注销',
    CREATE_TIME datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    UPDATE_TIME datetime(3) default CURRENT_TIMESTAMP(3) null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    constraint UK_USER_ID unique (ACCOUNT_ID)
    ) comment '账户表';

# 账户额度表
create table if not exists TB_ACCOUNT_LIMIT
(
    ID           bigint auto_increment comment '自增主键' primary key,
    LIMIT_ID     varchar(32)                              not null comment '额度ID',
    ACCOUNT_ID   varchar(32)                              not null comment '账户ID',
    LIMIT_TYPE   varchar(32)                              not null comment '额度类型',
    LIMIT_AMOUNT decimal(12, 2)                           not null comment '额度',
    STATUS       varchar(16)                              not null comment '额度状态。INIT:初始化；ACTIVE:生效；PAUSE:暂停；INACTIVE:失效',
    CURRENCY     varchar(16)                              not null comment '币种。CNY:人民币',
    START_DATE   datetime(3) default CURRENT_TIMESTAMP(3) not null comment '生效时间',
    END_DATE     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '失效时间，默认为9999-12-31日',
    VERSION      bigint(12)  default 0                    not null comment '版本号',
    CREATE_TIME  datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    UPDATE_TIME  datetime(3) default CURRENT_TIMESTAMP(3) null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    constraint UK_LIMIT_ID unique (LIMIT_ID),
    index IDX_ACCOUNT_ID_LIMIT_TYPE_CURRENCY (ACCOUNT_ID, LIMIT_TYPE, CURRENCY)
    ) comment '账户额度表';


# 账户额度记录表
create table if not exists TB_ACCOUNT_LIMIT_RECORD
(
    ID               bigint auto_increment comment '自增主键' primary key,
    RECORD_ID        varchar(32)                              not null comment '额度记录ID',
    LIMIT_ID         varchar(32)                              not null comment '额度ID',
    ACCOUNT_ID       varchar(32)                              not null comment '账户ID',
    LIMIT_TYPE       varchar(16)                              not null comment '额度类型',
    OUT_BIZ_NO       varchar(20)                              not null comment '业务方业务号',
    OUT_BIZ_DATE     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '业务方操作时间',
    OPERATION_TYPE   varchar(16)                              not null comment '操作类型。INIT:初始化；ADD:增加；MINUS:减少',
    OPERATION_AMOUNT decimal(12, 2)                           not null comment '操作金额',
    CREATE_TIME      datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    UPDATE_TIME      datetime(3) default CURRENT_TIMESTAMP(3) null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    constraint UK_RECORD_ID unique (RECORD_ID),
    index IDX_LIMIT_ID (LIMIT_ID),
    index IDX_OUT_BIZ_NO (OUT_BIZ_NO)
    ) comment '账户额度记录表';