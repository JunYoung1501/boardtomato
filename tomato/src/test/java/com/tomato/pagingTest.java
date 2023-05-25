package com.tomato;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.tomato.board.boarddao.BoardDAO;
import com.tomato.board.boardvo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class pagingTest {

	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	BoardDAO dao;
	
	BoardVO boardVO;
	
	@Transactional
	@Rollback(false)
	@Test
	public void test() {
		
		log.info("paging Test:");
		
		log.info("sqlSession : " + sqlSession.toString());
		
		/* boardVO=dao.pagingBoard(""); */
		
		log.info("=================================");
	}

	
	
}
