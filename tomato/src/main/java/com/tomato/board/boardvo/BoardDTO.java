package com.tomato.board.boardvo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDTO {
	/*게시글 번호*/	
	private int boardNum;
	/*작성자 아이디*/	
	private String boardId;
	/*게시글 제목*/
	private String boardTitle;
	/*게시글 내용*/
	private String boardContent;
	/*게시글 첨부파일*/
	private MultipartFile boardOriginalFile;
	/*게시글 첨부파일 암호화*/	
//	private MultipartFile boardFile;
	/*게시글 답글 원 게시글*/	
	private int boardReRef;
	/*게시글 답글 레벨*/
	private int boardReLev;
	/*게시글 답글 순서*/
	private int boardReSeq;
	/*게시글 조회수*/
	private int boardReadCount;
	/*게시글 작성일*/
	private Date boardDate;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardDTO [boardNum=").append(boardNum).append(", boardId=").append(boardId)
				.append(", boardTitle=").append(boardTitle).append(", boardContent=").append(boardContent)
				.append(", boardOriginalFile=").append(boardOriginalFile).append(", boardReRef=").append(boardReRef)
				.append(", boardReLev=").append(boardReLev).append(", boardReSeq=").append(boardReSeq)
				.append(", boardReadCount=").append(boardReadCount).append(", boardDate=").append(boardDate)
				.append("]");
		return builder.toString();
	}
	
	
	
}
