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
	//private static final String NameSpace="com.tomato.mapper.board";
	
	/*게시글 작성*/
	public void insertBoard(BoardVO boardVO) { 
		sqlSession.insert("com.tomato.mapper.board.insertBoard",boardVO);
	}
	
	/*게시글 첨부파일 업로드*/
//	public void insertBoardFile(FileVO fileVO) {
//		sqlSession.insert("com.tomato.mapper.boardfile.insertBoardFile",fileVO);
//	}
	
	
	/*게시글 리스트 페이징*/
	public List<BoardVO> pagingBoard(int page, int limit) {
		HashMap<String,Integer> map =new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		return sqlSession.selectList("com.tomato.mapper.board.boardPaging", map);
	}
	
	/*게시글 수정 댓글x*/
	public void updateBoard(BoardVO boardVO) {
		sqlSession.update("com.tomato.mapper.board.updateBoard",boardVO);
	}
	
	/*최근 시퀀스로 게시글번호 가져오기*/
	public int getBoardByLastSeq(){
		
		return	sqlSession.selectOne("com.tomato.mapper.board.getBoardNumByLastSeq");
	}
	
	
	/*게시된 댓글 수정*/
	public void updateBoardRe(BoardVO boardVO) {
		sqlSession.update("com.tomato.mapper.board.updateBoardRe",boardVO);
	}
	
	/*게시글 조회*/
	public void searchBoard(BoardVO boardVO) {
		sqlSession.select(null, null);
	}
	
	/*개별 게시글 조회*/
	public BoardVO  getBoard(int boardNum) {
		return sqlSession.selectOne("com.tomato.mapper.board.getBoard",boardNum);
	}
	
	/*게시글 존재여부*/	
	public boolean isBoard(int boardNum) {
		return (int)sqlSession.selectOne("com.tomato.mapper.board.isBoard",boardNum) == 1 ? true: false;
	}
	
	/* 총 게시글 수 조회 */
	public int getListCount(String isReplyContain) {
		return (int)sqlSession.selectOne("com.tomato.mapper.board.getListCount" , isReplyContain);
	}
	
	
	/*게시글 삭제*/
	public void deleteBoard(BoardVO boardVO) {
		sqlSession.delete("",boardVO);
	}
	
	/*게시된 댓글 삭제*/
	public void delteBoardRe(BoardVO boardVO) {
		sqlSession.delete("",boardVO);
	}
	
	/*전체 검색 게시글 수 */
	public int selectCountBoardsSearch(Map<String, Object> map) {
		return (int)sqlSession.selectOne("com.tomato.mapper.board.selectCountBoardsSearch" , map);
	}
	
	/*전체 검색 게시글 조회 */
	public List<BoardVO> selectBoardsSearch(Map<String, Object> map) {
		return sqlSession.selectList("com.tomato.mapper.board.selectBoardsSearch" , map);
	}
	
}
