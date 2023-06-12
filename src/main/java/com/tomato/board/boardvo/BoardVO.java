package com.tomato.board.boardvo;

import java.util.Date;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardVO {
	/**
	 *  게시글 번호
	 */
	private int boardNum;
	
	/**
	 *  작성자 아이디
	 */
	private String boardId;
	
	/**
	 *  게시글 제목
	 */
	private String boardTitle;
	
	/**
	 *  게시글 내용
	 */
	private String boardContent;
	
	/**
	 *  게시글 첨부파일
	 */
	private String boardOriginalFile;
	
	/**
	 *  게시글 첨부파일 암호화
	 * 	
	 */
	private String boardFile;
	
	/**
	 *  게시글 답글 원 게시글
	 * 	
	 */
	private int boardReRef;
	
	/**
	 *  게시글 답글 레벨
	 */
	private int boardReLev;
	
	/**
	 *  게시글 답글 순서
	 */
	private int boardReSeq;
	
	/**
	 *  게시글 조회수
	 * 
	 */
	private int boardReadCount;
	
	/**
	 *  게시글 작성일
	 * 
	 */
	private Date boardDate;
	
	public BoardVO() {}
	
	//boardVO -> boardDTO
	public  BoardVO(BoardDTO boardDTO) {
		
		this.boardNum = boardDTO.getBoardNum();
		this.boardId = boardDTO.getBoardId();
		this.boardTitle = boardDTO.getBoardTitle();
		this.boardContent = boardDTO.getBoardContent();
		this.boardOriginalFile =  boardDTO.getBoardOriginalFile() == null ? "" :
				boardDTO.getBoardOriginalFile().getOriginalFilename();
		this.boardFile =  boardDTO.getBoardFile() == null ? "" :
				boardDTO.getBoardFile().getOriginalFilename();
		this.boardReRef = boardDTO.getBoardReRef();
		this.boardReLev = boardDTO.getBoardReLev();
		this.boardReSeq = boardDTO.getBoardReSeq();
		this.boardReadCount = boardDTO.getBoardReadCount();
		this.boardDate = boardDTO.getBoardDate();
		
	}
	
	//boardVO 정보 넘기기
	public BoardVO(BoardVO boardVO) {
		log.info("BoardVO 넘어가는거:");
		this.boardNum=(int)boardNum;
		this.boardId=(String)boardId;
		this.boardTitle=(String)boardTitle;
		this.boardContent=(String)boardContent;
		this.boardOriginalFile=boardVO.getBoardOriginalFile() == null ? "" : boardVO.getBoardOriginalFile();
		this.boardReRef = boardVO.getBoardReRef();
		this.boardReLev = boardVO.getBoardReLev();
		this.boardReSeq = boardVO.getBoardReSeq();
		this.boardReadCount = boardVO.getBoardReadCount();
		this.boardDate = boardVO.getBoardDate();
		
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

	@Override
	public int hashCode() {
		return Objects.hash(boardContent, boardFile, boardId, boardNum, boardOriginalFile, boardReLev, boardReRef,
				boardReSeq, boardTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardVO other = (BoardVO) obj;
		return Objects.equals(boardContent, other.boardContent) && Objects.equals(boardFile, other.boardFile)
				&& Objects.equals(boardId, other.boardId) && boardNum == other.boardNum
				&& Objects.equals(boardOriginalFile, other.boardOriginalFile) && boardReLev == other.boardReLev
				&& boardReRef == other.boardReRef && boardReSeq == other.boardReSeq
				&& Objects.equals(boardTitle, other.boardTitle);
	}
	
	
}
