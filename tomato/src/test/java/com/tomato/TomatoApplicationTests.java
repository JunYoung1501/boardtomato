package com.tomato;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TomatoApplicationTests {

	@Test
	@GetMapping("/toamto/teststrap")
	public String test() {
		log.info("test:");
		return "/teststrap";
	}

}
