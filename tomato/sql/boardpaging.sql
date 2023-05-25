SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT * FROM BOARD
             ORDER BY board.board_num ASC
           ) m  
      )  
WHERE page = 1;