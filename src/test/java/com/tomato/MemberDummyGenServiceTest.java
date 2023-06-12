package com.tomato;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tomato.common.service.MemberDummyGenService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberDummyGenServiceTest {

	@Autowired
	MemberDummyGenService  	memberDummyGenService;
	
	@Test
	public void test() {
		log.info("열명분 데이터:");
		assertEquals(100,memberDummyGenService.memberDummyGen());
		
	}
	
}
