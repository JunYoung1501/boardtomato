-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT * FROM member      
             ORDER BY id ASC
           ) m  
      )  
WHERE page = 1;

-- 검색 : 이름(name), 유사 검색(like) 
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회
-- member, member_roles : 조인(join)

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT m.id, m.name, r.role,m.phone,
             m.email,m.postadd,m.address,m.detailaddr,
             m.birthday, m.joinday ,m.status
			 FROM member m, user_roles r
			  WHERE m.id = r.username(+)
             AND name LIKE '%이%'
             ORDER BY m.id ASC
           ) m  
      )  
WHERE page = 3;




--특정아이디의 ROLE 가져오기 
-- ROLE이 복수가 될수 있다.

---------------------------

SELECT LISTAGG(r2.role) WITHIN GROUP (ORDER BY r2.username) 
                    FROM user_roles r2, member m2 
                    WHERE r2.username = m2.id  AND 
                    m2.id = 'administrator';

-------------------------

-- 롤 조회
SELECT * FROM user_roles WHERE username = 'project10' ;

--롤 추가
INSERT INTO user_roles VALUES ( USER_ROLES_SEQ.nextval,
								'project10',
								'ROLE_ADMIN');
--롤 삭제
	DELETE user_roles
	WHERE username = 'administrator' AND role = 'ROLE_ADMIN' ;

-- 개인당 2개의 role(등급) 자료 => 1개의 자료(축약) : listagg ~ widthin group
-- 검색 : 이름(name), 유사 검색(like) 
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT distinct m.id, m.name, (
			    SELECT LISTAGG(r2.role, ',') WITHIN GROUP (ORDER BY r2.username) 
			    FROM user_roles r2, member m2 
			    WHERE r2.username = m2.id
			    AND r2.username = m.id
			) AS "ROLE",
            m.phone,
             m.email,m.postadd,m.address,m.detailaddr,
             m.birthday, m.joinday ,m.status
			 FROM member m, user_roles r
			 WHERE m.id = r.username(+)
             AND detailaddr LIKE '%나%'
             ORDER BY m.id ASC
           ) m  
      )  
WHERE page = 1;


---

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT distinct m.id, m.name, (
			    SELECT LISTAGG(r2.role, ',') WITHIN GROUP (ORDER BY r2.username) 
			    FROM user_roles r2, member m2 
			    WHERE r2.username = m2.id
			    AND r2.username = m.id
			) AS "ROLE",
            m.phone,
             m.email,m.postadd,m.address,m.detailaddr,
             m.birthday, m.joinday ,m.status
			 FROM member m, user_roles r
			 WHERE m.id = r.username(+)
             ORDER BY m.id ASC
           ) m  
      )  
WHERE page = 1;

-- 검색 레코드 수 

SELECT count(*)
FROM (
         SELECT
         DISTINCT m.id,  (
            SELECT LISTAGG(r2.role, ',') WITHIN GROUP (ORDER BY r2.username) 
            FROM user_roles r2, member m2 
            WHERE r2.username = m2.id
            AND r2.username = m.id
        ) AS "ROLE"
        FROM member m, user_roles r 
        WHERE 
         name LIKE '%나1%'
        ORDER BY id ASC
); 

