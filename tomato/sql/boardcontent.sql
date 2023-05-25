게시판 데이터 저장 여부
INSERT INTO BOARD VALUES(
		board_seq.NEXTVAL,
		'test1',
		'안녕하세요',
		'테스트입니다',
		null,
		null,
		0,
		0,
        0,
		0,
		sysdate)

-- 가장 최근의 게시판 시퀀스값 조회 
SELECT board_seq.nextval FROM dual;

-- 게시글 조회
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;

-- 전체 게시글 수 조회
SELECT count(*) FROM board;

-- 게시글 조회수 갱신
UPDATE board SET 
board_readcount=(SELECT board_readcount 
				 FROM board_tbl 
				 WHERE board_num=15) + 1
WHERE board_num=15;

-- 게시글 조회수 조회
SELECT board_readcount 
FROM board_tbl 
WHERE board_num=15;

--전체 게시글 페이징 조회
SELECT n.board_num as boardNum,
    n.board_id as boardId,
    n.board_title as boardTitle,
    n.board_content as boardContent,
    n.board_original_file as boardOriginalFile,
    n.board_file as boardFile,
    n.board_re_ref as boardReRef,
    n.board_re_lev as boardReLev,
    n.board_re_seq as boardReSeq,
    n.board_readcount as boardRecount,
    n.board_date as boardDate
FROM
    (SELECT m.*,  
     FLOOR((ROWNUM - 1) / 10 + 1) page  
     FROM (
            SELECT * FROM BOARD
            ORDER BY board.board_num ASC
          ) m  
    )  n
WHERE page =1;


-- 검색 : 게시글 조회
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회
-- 글 제목(키워드) 검색
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_subject LIKE '%날씨%'
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;


-- 글 내용(키워드) 검색
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_content LIKE '%과일%'
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;

-- 작성자(키워드) 검색
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_writer = 'abcd1234'
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;


