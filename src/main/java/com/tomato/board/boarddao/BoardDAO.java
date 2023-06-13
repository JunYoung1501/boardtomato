package com.tomato.board.boarddao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tomato.board.boardvo.BoardVO;

@Repository
public class BoardDAO {

	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 게시글 작성*/
	public void insertBoard(BoardVO boardVO) { 
		sqlSession.insert("com.tomato.mapper.board.insertBoard",boardVO);
	}
	
	
	
	/**
	 *  게시글 리스트 페이징
	 * @param page
	 * @param limit
	 * @return
	 */
	  
	public List<BoardVO> pagingBoard(int page, int limit) {
		HashMap<String,Integer> map =new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		return sqlSession.selectList("com.tomato.mapper.board.getboardPaging", map);
	}
	
	/** 게시글 수정 댓글x
	 * 
	 * @param boardVO
	 */
	public void updateBoard(BoardVO boardVO) {
		sqlSession.update("com.tomato.mapper.board.updateBoard",boardVO);
	}
	
	/** 게시된 댓글 수정
	 * 
	 * @param boardVO
	 */
	public void updateBoardRe(BoardVO boardVO) {
		sqlSession.update("com.tomato.mapper.board.updateBoardRe",boardVO);
	}
	
	
	/**
	 *  최근 시퀀스로 게시글번호 가져오기
	 * @return
	 */
	public int getBoardByLastSeq(){
		
		return	sqlSession.selectOne("com.tomato.mapper.board.getBoardNumByLastSeq");
	}
	
	
	/**
	 *  게시글 조회
	 * @param boardVO
	 */
	public void searchBoard(BoardVO boardVO) {
		sqlSession.select(null, null);
	}
	
	/** 개별 게시글 조회
	 * 
	 * @param boardNum
	 * @return
	 */
	public BoardVO  getBoard(int boardNum) {
		return sqlSession.selectOne("com.tomato.mapper.board.getBoard",boardNum);
	}
	
	/** 게시글 존재여부
	 * 	
	 * @param boardNum
	 * @return
	 */
	public boolean isBoard(int boardNum) {
		return (int)sqlSession.selectOne("com.tomato.mapper.board.isBoard",boardNum) == 1 ? true: false;
	}
	
	/**
	 *  총 게시글 수 조회 */
	public int getListCount(String isReplyContain) {
		return (int)sqlSession.selectOne("com.tomato.mapper.board.getListCount" , isReplyContain);
	}
	
	/**
	 * 게시글 삭제*/
	public void deleteBoard(int boardNum) {
		sqlSession.delete("com.tomato.mapper.board.deleteBoard",boardNum);
	}
	
	/** 
	 *  개별 게시글/댓글  모두 삭제
	 * @param boardVO
	 */
	public void deleteOriginalReplyBoards(BoardVO boardVO) {
		sqlSession.delete("deleteOriginalReplyBoards",boardVO);
	}
	
	/**
	 *  전체 검색 게시글 수
	 * @param map
	 * @return
	 */
	public int selectCountBoardsSearch(Map<String, Object> map) {
		return (int)sqlSession.selectOne("com.tomato.mapper.board.selectCountBoardsSearch" , map);
	}
	
	/**  
	 * 전체 검색 게시글 조회 
	 * @param map
	 * @return
	 */
	public List<BoardVO> selectBoardsSearch(Map<String, Object> map) {
		return sqlSession.selectList("com.tomato.mapper.board.selectBoardsSearch" , map);
	}
	
	/**
	 *  게시판 조회수 수정 
	 * @param boardNum
	 */
	public void updateBoardReadCount(int boardNum) {
		sqlSession.update("com.tomato.mapper.board.updateBoardReadCount",boardNum);
	}
	
	/** 
	 * 게시판 내용중 삽입 이미지 삭제 
	 * @param id
	 */
	public void deletContentImg(int id) {
		sqlSession.delete("com.tomato.mapper.board.deleteContentImg",id);
	}
	
	/** 
	 * 게시판 체크용
	 * @param boardVO
	 * @return
	 */
	public BoardVO select(BoardVO boardVO) {
		
		return null;
	}
	
	/**  
	 * 게시글 댓글들 조회
	 * @param boardReRef
	 * @return
	 */
	public List<BoardVO> getboardReplySelect (int boardReRef) {
		
		return	sqlSession.selectList("com.tomato.mapper.board.getboardReplySelect", boardReRef);
	}
}
