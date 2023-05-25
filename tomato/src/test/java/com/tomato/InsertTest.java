package com.tomato;

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
public class InsertTest {

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	MemberDAO dao;
	
	MemberVO memberVO;
	
	@BeforeEach
	public void setup() {
		memberVO =new MemberVO();
		memberVO.setId("test3");
		memberVO.setPwd("@asdqre");
		memberVO.setName("테스트3");
		memberVO.setPhone("01022345678");
		memberVO.setEmail("asqew@awd.com");
		memberVO.setBirthday("20101111");
		memberVO.setPostadd("01254");
		memberVO.setAddress("서울시");
		memberVO.setDetailaddr("Asss");
	}
	@Transactional
	@Rollback(false)
	@Test
	public void test() {
		
		log.info("insert Test");
		
		log.info("sqlSession : " + sqlSession.toString());
		
		dao.insert(memberVO);
		
		log.info("=================================");
	}

	
}
