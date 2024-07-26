create table USER_BASIC
(
    user_id bigint not null auto_increment comment 'user key id',
    user_name varchar(255) not null,
    user_email varchar(255) not null,
    user_password varchar(255) not null,
    primary key (user_id)
);

create table USER_TOKEN
(
    token_id bigint not null auto_increment comment 'token key id',
    user_id bigint not null,
    access_token varchar(255) not null,
    refresh_token varchar(255) not null,
    expired_at datetime(6) not null,
    primary key (token_id)
);

create table ROLES
(
    role_name varchar(255) not null,
    primary key (role_name)
);

create table USER_ROLE
(
    role_id bigint not null auto_increment comment 'role key id',
    user_id bigint not null,
    role_name varchar(255) not null,
    description varchar(255) not null,
    created_at datetime(6) not null DEFAULT CURRENT_TIMESTAMP() ,
    primary key (role_id)
);

create table LOGIN_HISTORY
(
    login_his_id bigint not null auto_increment comment 'user key id',
    user_id bigint not null comment 'user key id',
    device_info varchar(255),
    access_ip varchar(255),
    created_at datetime(6) not null DEFAULT CURRENT_TIMESTAMP() ,
    primary key (login_his_id)
);