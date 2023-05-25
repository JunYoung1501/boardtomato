--검색
SELECT 
	n.board_num as boardNum,
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
         FLOOR((ROWNUM - 1) / 5 + 1) page  
         FROM (
                SELECT * FROM BOARD
                where
                    board_Re_Ref =0
                    AND board_id like '%test%'
                    ORDER BY board_num  DESC
                    
              ) m  
        )  n
    WHERE page =1 ;

--검색 게시글 수 
SELECT   COUNT(*)
FROM 
    (SELECT m.*  
     FROM (
            SELECT * FROM BOARD
            where
                board_Re_Ref =0
                AND  board_content LIKE '%' || '자바' || '%'
                ORDER BY board_num DESC
   
          ) m  
    )  n;
