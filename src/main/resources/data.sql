insert into USER_BASIC(user_name, user_email, user_password, user_country_code, user_phone, user_salt_key)
values ('tom','tom@test.com','ff04884c6bf2fd3c7967d6e64e4d550e6196a521a31e59f516de4bca6f13d94c8fb447f5f8048f8803fe422e1e9c67cc262253d910ece2b0abf79073e74a60a0','+82','01012345678','salt'); --tom123
insert into USER_BASIC(user_name, user_email, user_password, user_country_code, user_phone, user_salt_key)
values ('sara','sara@test.com','e178d3c2105f4f8d9ee4ef183b4d5e2e36502285b940934b35e5b4c188dbbedfafe26fed6a26263e4ac8f8f076f67b31bdda22eed3f0a2f597cc4f664d59dc43','+82','01087654321','salt'); --sara123

insert into USER_ROLE(user_id, role_name, description) values (1, 'ROLE_ADMIN', 'ADMIN');
insert into USER_ROLE(user_id, role_name, description) values (1, 'ROLE_USER', 'USER');
insert into USER_ROLE(user_id, role_name, description) values (2, 'ROLE_USER', 'USER');

insert into ROLES(role_name) values ('ROLE_ADMIN');
insert into ROLES(role_name) values ('ROLE_USER');