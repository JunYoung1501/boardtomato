package com.tomato.board.boarddao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tomato.board.boardvo.BoardVO;

@Repository
public class BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	public void insertBoard(BoardVO boardVO) {
		sqlSession.insert("com.tomato.mapper.board.insert",boardVO);
	}
	
	public void updateBoard(BoardVO boardVO) {
		sqlSession.update("com.tomato.mapper.board.update",boardVO);
	}
	

	public void updateReply(BoardVO boardVO) {
		sqlSession.update("com.tomato.mapper.board.updateReply",boardVO);
	}
	
	public void deleteBoard(BoardVO boardVO) {
		sqlSession.delete("com.tomato.mapper.board.delete", boardVO);
	}
	
	public boolean hasBoard(String boarNum) {
		return sqlSession.selectOne("com.tomato.mapper.board.hasBoard",boarNum);
	}
	
	public boolean isBoard(String field,String value) {
		HashMap<String,String> map =new HashMap();
		map.put("fld", field);
		map.put("val", value);
		return (int)sqlSession.selectOne("com.tomato.mapper.board.isBoard",map)==1 ? true : false;
	}
}
