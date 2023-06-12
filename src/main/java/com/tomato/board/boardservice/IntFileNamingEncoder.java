package com.tomato.board.boardservice;

/* 파일 암호화 인터페이스 */
public interface IntFileNamingEncoder {

	/**
	 * 실제 저장 파일명 암호화 처리(UUID)<br> 
	 * 효과) 업로드 파일들의 중복 방지<br>
	 *  
	 * @param fileName 원본 파일명 ex) abcd.pdf 
	 * @return 업로드할 파일명 ex) abcd_암호화코드(UUID).pdf
	 */
	String enFilenameUUID(String fileName);

}