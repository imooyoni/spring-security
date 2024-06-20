create table USER_BASIC
(
    user_id bigint not null auto_increment comment 'user key id',
    user_name varchar(255) not null,
    user_email varchar(255) not null,
    user_password varchar(255) not null,
    primary key (user_id)
);
insert into user_basic (user_name, user_email, user_password) values ('tom','tom@test.com','user1234');

create table USER_TOKEN
(
    token_id bigint not null auto_increment comment 'token key id',
    user_id bigint not null,
    access_token varchar(255) not null,
    refresh_token varchar(255) not null,
    expired_at datetime(6) not null,
    primary key (token_id)
);

create table USER_ROLE
(
    role_id bigint not null auto_increment comment 'role key id',
    role_name varchar(255) not null,
    description varchar(255) not null,
    created_at datetime(6) not null DEFAULT CURRENT_TIMESTAMP() ,
    primary key (role_id)
);
insert into USER_ROLE(role_name, description) values ('admin', 'admin account');