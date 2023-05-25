package com.tomato.board.boardservice;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomato.board.boarddao.BoardDAO;
import com.tomato.board.boardvo.BoardVO;
import com.tomato.board.boardvo.FileVO;
import com.tomato.board.boardvo.PageVO;

import lombok.extern.slf4j.Slf4j;


/*게시판 서비스 */
@Slf4j
@Service
public class BoardService implements IntBoardService {

	@Autowired
	BoardDAO boardDAO;
	
	private boolean flag = false;
	
	
	/*게시글 저장*/
	@Transactional
	@Override
	public boolean insertBoard(BoardVO boardVO) {
	
		boolean flag = false;
		log.info("---BoardService.insert:"+boardVO);
		
		try {
			boardDAO.insertBoard(boardVO);
			//MemberDAO memberDAO =new MemberDAO();
			log.info("글이 정상 등록 되었습니다");
		    flag = true;
		}catch (Exception e) {
			log.error("insertBoard error:"+e);
			log.info("글이 정상 등록되지 않았습니다");
		}
		
		return flag;
	}//insertBoard
	
	@Transactional(readOnly = true)
	@Override
	public int getBoardNumByLastSeq() {
		
		log.info("Service getBoardNumByLastSeq");
		
		return boardDAO.getBoardByLastSeq();
	}//getBoardNumByLastSeq
	
	
	
	@Transactional(readOnly =true)
	@Override
	public boolean isBoard(int boardNum) {
		return boardDAO.isBoard(boardNum);
	}
	
	@Transactional(readOnly =true)
	@Override
	public int selectCountAllBoard(boolean isReplyContain) {
		return boardDAO.getListCount(Boolean.toString(isReplyContain));
	}

	@Override
	public boolean updateReadcount(int boardNum) {
		return false;
	}

	@Override
	public List<BoardVO> selectReplyBoards(int boardNum) {
		return null;
	}

	@Override
	public BoardVO getBoard(int boardNum) {
		return boardDAO.getBoard(boardNum);
	}

	@Override
	public boolean updateBoard(BoardVO boardVO) {
		return false;
	}

	@Override
	public boolean updateReplyBoard(BoardVO boardVO) {
		return false;
	}

	@Override
	public boolean deleteBoard(int boardNum) {
		return false;
	}

	@Override
	public boolean deleteOriginalReplyBoards(int boardNum) {
		return false;
	}

	
	@Override
	public BoardVO getOneBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<BoardVO> getBoardBySearch(String searchKey, String searchWord, 
										 String orderKey, String orderDirection, 
										 int page, int limit,
										 String isReplyContain) {
		Map<String, Object> map = new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		map.put("orderKey", orderKey);
		map.put("orderDirection", orderDirection);
		map.put("page", page);
		map.put("limit", limit);
		map.put("isReplyContain", isReplyContain);
		
		return boardDAO.selectBoardsSearch(map);
	}

	@Override
	@Transactional(readOnly=true)
	public List<BoardVO> getArticleList(int page, int limit) {
		return boardDAO.pagingBoard(page, limit);
	}
	
	@Override
	@Transactional(readOnly=true)
	public int selectCountSearchingBoards(Map<String, Object> map) {
		return boardDAO.selectCountBoardsSearch(map);
	}

	

}
