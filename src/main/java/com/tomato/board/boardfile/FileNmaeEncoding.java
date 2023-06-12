package com.tomato.board.boardfile;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 *  파일  암호화 UUID 
 *
 */
@Slf4j
@Service
public class FileNmaeEncoding implements IntFileNameEncoding {

	@Override
	public String enFilename(String fileName) {
		log.info("파일명 인코딩(UUID)");
		UUID uuid= UUID.randomUUID();
		return uuid.toString()+"_"+fileName;
	}

}
