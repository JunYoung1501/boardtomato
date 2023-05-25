package com.tomato;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.tomato.board.memberdao.MemberDAO;
import com.tomato.board.membervo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class SelectTest {

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	MemberDAO dao;
	
	MemberVO memberVO;
	
	@Transactional
	@Rollback(false)
	@Test
	public void test() {
		
		log.info("insert Test");
		
		log.info("sqlSession : " + sqlSession.toString());
		
		memberVO=dao.select("test3");
		//assertEquals("테스트3", memberVO.getName());
		assertEquals("01022345678", memberVO.getPhone());
		log.info("=================================");
	}

	
}
