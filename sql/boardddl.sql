CREATE SEQUENCE board_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;
create table board (
    board_num NUMBER DEFAULT 0,
	board_id VARCHAR2(20) NOT NULL,
	board_title VARCHAR2(100 char) NOT NULL,
	board_content CLOB NOT NULL,
	board_original_file VARCHAR2(200 char),
	board_file VARCHAR2(200 char),
	board_re_ref NUMBER NOT NULL,
	board_re_lev NUMBER NOT NULL,
	board_re_seq NUMBER NOT NULL,
	board_readcount NUMBER DEFAULT 0,
	board_date DATE,
	PRIMARY KEY(board_num)

);

comment on  column board.board_num is '게시판번호';
comment on column board.board_id is '작성자아이디';
comment on column board.board_title is '게시글제목';
comment on column board.board_content is '게시글내용';
comment on column board.board_original_file is '게시글 첨부 파일';
comment on column board.board_file is '게시글 첨부 파일(암호화)';
comment on column board.board_re_ref is '게시글 답글의 원 게시글';
comment on column board.board_re_lev is '게시글 답글 레벨';
comment on column board.board_re_seq is '게시글 답글 순서';
comment on column board.board_readcount is'게시글 조회수';
comment on column board.board_date is '게시글 작성일자';


create table boardfile (
     boardfile_num  NUMBER DEFAULT 0 primary key,
        board_original_file VARCHAR2(200 char),
	board_file VARCHAR2(200 char)

);


comment on column boardfile.boardfile_num is '게시판 번호';
comment on column boardfile.board_original_file is '게시글 첨부 파일';
comment on column boardfile.board_file is '게시글 첨부 파일(암호화)';