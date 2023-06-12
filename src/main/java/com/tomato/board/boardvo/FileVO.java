package com.tomato.board.boardvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
	
	/**
	 * 게시판 아이디
	 * 
	 */
	private String boardID;
	
	/**
	 * 원파일명  
	 */
	private String fileName;
	
	/**
	 * 암호화 파일명 
	 */
	private String encodeFileName;
	
	/**
	 * 파일 처리 결과 메세지
	 * 
	 */
	private String msg;

}