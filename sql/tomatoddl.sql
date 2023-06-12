create table member(
id char(15) PRIMARY key,
pwd varchar2(60) not null,
name varchar2(30 char) not null,
phone varchar2(11) unique,
email  varchar2(150) unique,
birthday char(8) not null,
postadd char(5) not null,
address varchar2(120) not null,
detailaddr varchar2(120),
JOINDAY DATE,
status number(1) not null
); 



comment on COLUMN member.id  is '아이디' ; 
comment on COLUMN member.pwd is '비밀번호' ;
comment on COLUMN member.name  is '성명' ;
comment on COLUMN member.phone  is '휴대전화' ;
comment on COLUMN member.email  is '이메일' ;
comment on COLUMN member.birthday  is '생일' ;
comment on COLUMN member.postadd  is '우편번호' ;
comment on COLUMN member.address  is '주소' ;
comment on COLUMN member.detailaddr  is '상세주소' ; 
comment on COLUMN member.joinday  is '가입일' ; 
comment on COLUMN member.status  is '활동 여부 ex)1=활동,2=휴면,3=탈퇴';