package com.tomato.board.boardvo;

import java.util.Date;

public class BoardVO {
	/*게시글 번호*/	
	private int boardNum;
	/*작성자 아이디*/	
	private String boardId;
	/*게시글 제목*/
	private String boardTitle;
	/*게시글 내용*/
	private String boardContent;
	/*게시글 첨부파일*/
	private String boardOriginalFile;
	/*게시글 첨부파일 암호화*/	
	private String boardFile;
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
	
	public BoardVO() {
		
	}
	
	public  BoardVO(BoardDTO boardDTO) {
		
		this.boardNum = boardDTO.getBoardNum();
		this.boardId = boardDTO.getBoardId();
		this.boardTitle = boardDTO.getBoardTitle();
		this.boardContent = boardDTO.getBoardContent();
		this.boardOriginalFile = boardDTO.getBoardOriginalFile().getOriginalFilename();
		//this.boardFile = boardDTO.getBoardFile().getOriginalFilename();
		this.boardReRef = boardDTO.getBoardReRef();
		this.boardReLev = boardDTO.getBoardReLev();
		this.boardReSeq = boardDTO.getBoardReSeq();
		this.boardReadCount = boardDTO.getBoardReadCount();
		this.boardDate = boardDTO.getBoardDate();
		
	}
	
	

	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardOriginalFile() {
		return boardOriginalFile;
	}
	public void setBoardOriginalFile(String boardOriginalFile) {
		this.boardOriginalFile = boardOriginalFile;
	}
	public String getBoardFile() {
		return boardFile;
	}
	public void setBoardFile(String boardFile) {
		this.boardFile = boardFile;
	}
	public int getBoardReRef() {
		return boardReRef;
	}
	public void setBoardReRef(int boardReRef) {
		this.boardReRef = boardReRef;
	}
	public int getBoardReLev() {
		return boardReLev;
	}
	public void setBoardReLev(int boardReLev) {
		this.boardReLev = boardReLev;
	}
	public int getBoardReSeq() {
		return boardReSeq;
	}
	public void setBoardReSeq(int boardReSeq) {
		this.boardReSeq = boardReSeq;
	}
	public int getBoardReadCount() {
		return boardReadCount;
	}
	public void setBoardReadcount(int boardReadCount) {
		this.boardReadCount = boardReadCount;
	}
	public Date getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}
	@Override
	public String toString() {
		return "BoardVO [boardNum=" + boardNum + ", boardId=" + boardId + ", boardTitle=" + boardTitle
				+ ", boardContent=" + boardContent + ", boardOriginalFile=" + boardOriginalFile + ", boardFile="
				+ boardFile + ", boardReRef=" + boardReRef + ", boardReLev=" + boardReLev + ", boardReSeq=" + boardReSeq
				+ ", boardReadCount=" + boardReadCount + ", boardDate=" + boardDate + "]";
	}
	
	
}
