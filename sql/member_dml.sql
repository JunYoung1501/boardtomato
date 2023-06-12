

--  메일 중복점검 (회원수정) : 다른 회원과 메일 정보 중복(X)
SELECT COUNT(*) FROM (
        SELECT EMAIL FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE EMAIL='java@abcd.com';