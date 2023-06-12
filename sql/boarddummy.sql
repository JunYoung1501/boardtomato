CREATE OR REPLACE PROCEDURE dummy_member_gen_proc
IS
BEGIN
 
    FOR i IN 1..100 LOOP
        INSERT INTO BOARD VALUES
        (board_seq.NEXTVAL,
		'tester_'|| (100+i),
		'안녕하세요',
		'테스트입니다',
		null,
		null,
		0,
		0,
        0,
		0,
		sysdate);

     END LOOP;
 
    COMMIT;    
END;
exec dummy_member_gen_proc;