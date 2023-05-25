package com.tomato.board.boardservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomato.board.boarddao.BoardDAO;
import com.tomato.board.boardvo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardSerive implements IntBoardSerivce {

	@Autowired
	BoardDAO boardDAO;
	
	@Transactional
	@Override
	public boolean insertBoard(BoardVO boardvo) {
		String msg="";
		try {
			log.info("insertBoard:");
			boardDAO.insertBoard(boardvo);
			msg="게시글을 등록 하였습니다";
			return true;
		}catch(Exception e) {
			log.info("FailInsertBoard:");
			msg="게시글을 등록하지 못하였습니다";
			return false;
		}
		
			}//
	

	@Transactional
	@Override
	public String updateBoard(BoardVO boardvo) {
		String msg="";
		try {
			boardDAO.updateBoard(boardvo);
			msg="게시판 수정이 완료됬습니다";
		}catch(Exception e) {
			log.error("update:"+e);
			msg="게시판 수정이 실패하였습니다";
		}
		
		return msg;
	}
	
	@Transactional
	@Override
	public String deleteBoard(BoardVO boardvo) {
		String msg="";
		try {
			boardDAO.deleteBoard(boardvo);
			msg="게시글이 삭제되었습니다";
		}catch(Exception e) {
			log.error("deleteBoard:"+e);
			msg="게시글 삭제에 실패하였습니다";
		}
		return msg;
	}
	
	@Transactional(readOnly= true)
	@Override
	public List<BoardVO> getAllBoard() {
	
		return null;
	}
	
	@Transactional(readOnly= true)
	@Override
	public boolean hasBoard(String boardNum) {
		return  false;
	}
	
	@Transactional(readOnly= true)
	@Override
	public boolean isBoardContent(String field, String value) {
		
		return false;
	}
	@Override
	public String updateReply(BoardVO boardvo) {
		String msg="";
		try {
			boardDAO.updateReply(boardvo);
			msg="댓글 수정이 완료됬습니다";
		}catch(Exception e) {
			log.error("update:"+e);
			msg="댓글 수정이 실패하였습니다";
		}
		
		return msg;
	}

}
