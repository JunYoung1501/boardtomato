/**
 * 
 */
package com.tomato.board.boardservice;

import org.springframework.web.multipart.MultipartFile;

import com.tomato.board.boardvo.FileVO;

/* 파일 업로드 인터페이스 */
public interface IntFileUploadService {
	
	FileVO storeUploadFile(int boardNum, MultipartFile file);
	
	/**
	 * 삽입 이미지 파일 삭제 서비스
	 *
	 *  uploadPath 업로드된 파일 경로
	 *  file 삭제할 삽입 이미지 업로드 파일명(인코딩된 파일명)
	 *  삭제 결과 메시지
	 */
	public String deleteUploadFile(String uploadPath, String encodingFilename);

}