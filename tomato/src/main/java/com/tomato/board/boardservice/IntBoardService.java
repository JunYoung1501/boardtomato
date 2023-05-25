package com.tomato.board.boardservice;

import java.util.List;
import java.util.Map;

import com.tomato.board.boardvo.BoardVO;

/* 게시판 서비스 인터페이스 */
public interface IntBoardService {
	
	/*게시글 저장*/
	public boolean insertBoard(BoardVO boardVO); 
	
	/*게시판 시퀀스 값 조회 */
	public int getBoardNumByLastSeq();
	boolean isBoard(int boardNum);
	
	/*개별 게시글 조회*/	
	public BoardVO getBoard(int boardNum);
	BoardVO getOneBoard(BoardVO board);
	
	/*전체 게시글 수 (댓글 포함 여부 추가)*/
	public int selectCountAllBoard(boolean isReplyContain);
	
	
	/* 조회수 갱신*/
	public boolean updateReadcount(int boardNum);
	
	/*게시글(원글)의 댓글 조회*/
	public List<BoardVO> selectReplyBoards(int boardNum);
	
	
	/* 전체 게시글 목록 조회 (페이징 적용)*/
	public List<BoardVO> getArticleList(int page, int limit);
	
	/*검색 게시글 검색 */	
	public List<BoardVO> getBoardBySearch(String searchKind, 
			   String searchWord, 
			   String orderKey,
			   String orderDirection,
			   int page,
			   int limit,
			   String isReplyContain);
	
	/*개별 게시글 수정*/
	public boolean updateBoard(BoardVO boardVO);
	
	
	/*개별 댓글 수정*/
	public boolean updateReplyBoard(BoardVO boardVO);
	
	
	/*게시글 삭제(원글/댓글)*/
	public boolean deleteBoard(int boardId);
	
	/*개별 게시글 및 해당 댓글 모두 삭제*/
	public boolean deleteOriginalReplyBoards(int boardNum);
	
	/*검색 게시글 수 */	
	public int selectCountSearchingBoards(Map<String, Object> map);
	
}
