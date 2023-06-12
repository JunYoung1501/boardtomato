package com.tomato;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.tomato.board.boardservice.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class SelectSearchTest {

	@Autowired
	BoardService boardService;
	
	Map<String, Object> map;
	
	@BeforeEach
	public void setUp() {
		map=new HashMap<>();
		 
		map.put("orderKey", "board_num");
		map.put("orderDirection","DESC");
		map.put("searchKey", "board_content");
		map.put("searchWord","자바");
//		map.put("page",1);
//		map.put("limt",5);
		map.put("isReplyContain", "false");
		
	}
	
	
	@Test
	public void test() {
		log.info("test:");
		int result = boardService.selectCountSearchingBoards(map);
		log.info("result:"+result);
		assertEquals(2,result);
	}
	
}
