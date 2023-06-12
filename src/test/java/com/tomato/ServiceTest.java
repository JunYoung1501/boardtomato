package com.tomato;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.tomato.board.boarddao.BoardDAO;
import com.tomato.board.boardvo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ServiceTest {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	BoardDAO dao;
	
	BoardVO boardVO;
	
	@BeforeEach
	public void setup() {
		boardVO = new BoardVO();
		boardVO.setBoardId("tester001");
		boardVO.setBoardTitle("test12");
		boardVO.setBoardContent("테스트입니다다");
		boardVO.setBoardOriginalFile(null);
		boardVO.setBoardFile(null);
		boardVO.setBoardReRef(0);
		boardVO.setBoardReLev(0);
		boardVO.setBoardReSeq(0);
		boardVO.setBoardReadcount(0);
		boardVO.setBoardDate(null);
	}
	
	@Transactional
	@Rollback(false)
	@Test
	public void testServiceboard() {
		log.info("------insertBoardTest:");
		boolean flag = false;
		
		try {
			dao.insertBoard(boardVO);
			log.info("글이 정상 등록 되었습니다");
		    flag = true;
		}catch (Exception e) {
			log.error("insertBoard error:"+e);
			log.info("글이 정상 등록되지 않았습니다");
		}
		
		assertTrue(flag);
		
	}
	
}
