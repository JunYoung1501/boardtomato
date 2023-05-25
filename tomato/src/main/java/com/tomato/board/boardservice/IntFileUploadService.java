/**
 * 
 */
package com.tomato.board.boardservice;

import org.springframework.web.multipart.MultipartFile;

import com.tomato.board.boardvo.FileVO;

/* 파일 업로드 인터페이스 */
public interface IntFileUploadService {
	
	FileVO storeUploadFile(int boardNum, MultipartFile file);

}