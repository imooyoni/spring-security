create table USER_BASIC
(
    user_id bigint not null auto_increment comment 'user key id',
    user_name varchar(255) not null,
    user_email varchar(255) not null,
    primary key (user_id)
);

create table USER_TOKEN
(
    token_id bigint not null auto_increment comment 'token key id',
    user_id bigint not null,
    access_token varchar(255) not null,
    refresh_token varchar(255) not null,
    expired_at datetime(6) not null,
    primary key (token_id),
);