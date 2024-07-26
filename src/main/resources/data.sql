insert into USER_BASIC(user_name, user_email, user_password) values ('tom','tom@test.com','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi');
insert into USER_ROLE(user_id, role_name, description) values (1, 'ROLE_ADMIN', 'ADMIN');

insert into ROLES(role_name) values ('ROLE_ADMIN')