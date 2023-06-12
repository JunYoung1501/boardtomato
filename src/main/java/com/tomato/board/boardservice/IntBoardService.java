package com.tomato.board.boardservice;

import java.util.List;
import java.util.Map;

import com.tomato.board.boardvo.BoardVO;

/** 게시판 서비스 인터페이스 */
public interface IntBoardService {
	
	/**
	 * 게시글 저장
	 * @param 게시글 저장 입니다
	 * @return flag 방식 
	 * 		   true 일때  게시글 저장
	 * 	       false 이면 게시글 저장 실패
	 * 
	 */
	public boolean insertBoard(BoardVO boardVO); 
	
	/**
	 * 게시판 시퀀스 값 조회 
	 * @return  int boardNum 
	 * 			게시판 조회시 개별 게시글에 적용된
	 * 			게시판의 고유 번호(boardNum)
	 * 	
	 */
	public int getBoardNumByLastSeq();
	/**
	 * 	게시판 존재 유무 
	 * @param boardNum
	 * @return
	 */
	boolean isBoard(int boardNum);
	
	/**개별 게시글 조회
	 * 
	 * @param BoardVO 
	 * @return boardNum
	 */
	public BoardVO getBoard(int boardNum);
	
	/**전체 게시글 수 (댓글 포함 여부 추가)
	 * 
	 * @param isReplyContain
	 * @return getListCount 
	 */
	public int selectCountAllBoard(boolean isReplyContain);
	
	
	/**
	 *  게시글 조회수 갱신
	 * @param boardNum
	 */
	public void updateReadcount(int boardNum);
	

	/** 전체 게시글 목록 조회 (페이징 적용)
	 * 
	 * @param page
	 * @param limit
	 * @return pagingBoard
	 */
	public List<BoardVO> getArticleList(int page, int limit);
	
	/**검색 게시글 검색 
	 * 	
	 * @param searchKind
	 * @param searchWord
	 * @param orderKey
	 * @param orderDirection
	 * @param page
	 * @param limit
	 * @param isReplyContain
	 * @return selectBoardsSearch(map)
	 */
	public List<BoardVO> getBoardBySearch(String searchKind, 
			   String searchWord, 
			   String orderKey,
			   String orderDirection,
			   int page,
			   int limit,
			   String isReplyContain);
	
	/**개별 게시글 수정
	 * 
	 * @param boardVO
	 * @return result
	 *         성공시 적용 
	 *         실패시 오류 메세지 및 전송 실패
	 */
	public boolean updateBoard(BoardVO boardVO);
	
	/** 게시판 제목 점검용 
	 * 
	 * @param title
	 * @return
	 */
	BoardVO getBoardTitle(String title);
	/**
	 * 게시판 내용 점검용
	 */
	BoardVO getBoardContent(String content);
	/**
	 * 게시판 첨부파일 점검용
	 * @param orignalFile
	 * @return
	 */
	BoardVO getBoardOrignalFile(String orignalFile);

	/** 문자열에 포함된 이미지 파일명(확장자 포함) 만을 골라서 목록(리스트) 생성
	 * 	
	 * @param str
	 * @param imgUploadPath
	 * @return 배열처리 
	 * 
	 */
	public List<Integer> getImageList(String str, String imgUploadPath);
	
	/**개별 댓글 수정
	 * 
	 * @param boardVO
	 * @return
	 */
	public boolean updateReplyBoard(BoardVO boardVO);
	
	
	/**게시글 삭제(원글/댓글)
	 * 
	 * @param boardId
	 * @return
	 */
	public boolean deleteBoard(int boardId);
	
	/**개별 게시글 및 해당 댓글 모두 삭제*/
	public boolean deleteOriginalReplyBoards(int boardNum);
	
	/**검색 게시글 수 
	 * 
	 * @param map
	 * @return
	 */
	public int selectCountSearchingBoards(Map<String, Object> map);
	
	/** 게시판 내용중 삽입 이미지 삭제 
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteContentImg(int id);
	
	/** 게시글 원글 댓글 가져오기
	 * 
	 * @param boardReRef
	 * @return
	 */
	public List<BoardVO> boardReplySelect(int boardReRef);
	
}
