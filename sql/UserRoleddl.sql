CREATE TABLE user_roles (
  user_role_id number(11) NOT NULL,
  username varchar2(20) NOT NULL,
  role varchar2(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  CONSTRAINT fk_id FOREIGN KEY (username) 
     REFERENCES member (id)
);

comment on COLUMN user_roles.user_role_id is '롤아이디';
comment on COLUMN user_roles.username is '유저아이디';
comment on COLUMN user_roles.role is '유저롤';

create sequence user_roles_seq
start with 1
increment by 1
MAXVALUE 999999
nocycle;
