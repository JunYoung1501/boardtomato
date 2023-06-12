package com.tomato.board.boardservice;

import java.util.List;

import com.tomato.board.boardvo.BoardVO;

public interface IntBoardSerivce {

	/*게시글 저장*/
	public boolean insertBoard(BoardVO boardvo);

	/*게시글 수정*/
	public String updateBoard(BoardVO boardvo);
	
	/*댓글 수정*/
	public String updateReply(BoardVO boardvo);

	/*게시글 삭제*/
	public String deleteBoard(BoardVO boardvo);

	/*게시글 조회*/	
	List<BoardVO> getAllBoard();
	
	boolean hasBoard(String boardNum);
	
	boolean isBoardContent(String field,String value);

}
