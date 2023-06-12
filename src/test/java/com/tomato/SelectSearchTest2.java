package com.tomato;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.tomato.board.boardservice.BoardService;
import com.tomato.board.boardvo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class SelectSearchTest2 {

	@Autowired
	BoardService boardService;
	
	Map<String, Object> map;
	List<BoardVO> list;
	
	@BeforeEach
	public void setUp() {
		map=new HashMap<>();
		 
		map.put("orderKey", "board_num");
		map.put("orderDirection","DESC");
		map.put("searchKey", "board_content");
		map.put("searchWord","자바");
		map.put("page",1);	
		map.put("limt",5);
		map.put("isReplyContain", "false");
		
	}
	
	
	@Test
	public void test() {
		log.info("getBoardTest:");
		 list = boardService.getBoardBySearch(map.get("searchKey").toString(),
												   map.get("searchWord").toString(),
												   map.get("orderKey").toString(),
												   map.get("orderDirection").toString(),
												   1,
												   5,
												   map.get("isReplyContain").toString());
		
		 list.forEach(x->{log.info(x+"");});
		assertEquals(2,list.size());
	}
	
}
