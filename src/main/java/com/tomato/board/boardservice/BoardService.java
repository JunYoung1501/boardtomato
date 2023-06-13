package com.tomato.board.boardservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tomato.board.boarddao.BoardDAO;
import com.tomato.board.boardvo.BoardVO;

import lombok.extern.slf4j.Slf4j;


/**
 * 게시판 서비스
 *
 */
@Slf4j
@Service
public class BoardService implements IntBoardService {

	@Autowired
	BoardDAO boardDAO;
	
	/**
	 * 게시글 저장
	 * 
	 */
	@Transactional
	@Override
	public boolean insertBoard(BoardVO boardVO) {
	
		boolean flag = false;
		log.info("---BoardService.insert:"+boardVO);
		
		try {
			boardDAO.insertBoard(boardVO);
			log.info("글이 정상 등록 되었습니다");
		    flag = true;
		}catch (Exception e) {
			log.error("insertBoard error:"+e);
			log.info("글이 정상 등록되지 않았습니다");
		}
		
		return flag;
	}//insertBoard
	
	/**
	 * 게시글 시퀀스 
	 */
	@Transactional(readOnly = true)
	@Override
	public int getBoardNumByLastSeq() {
		
		log.info("Service getBoardNumByLastSeq");
		
		return boardDAO.getBoardByLastSeq();
	}//getBoardNumByLastSeq
	
	
	/**
	 *  게시판 존재 유무 
	 */
	@Transactional(readOnly =true)
	@Override
	public boolean isBoard(int boardNum) {
		return boardDAO.isBoard(boardNum);
	}
	
	/**
	 *전체 게시글수 (댓글 포함 여부 추가)
	 */
	@Transactional(readOnly =true)
	@Override
	public int selectCountAllBoard(boolean isReplyContain) {
		return boardDAO.getListCount(Boolean.toString(isReplyContain));
	}
	
	/**
	 * 게시글 조회수 갱신
	 */
	@Transactional
	@Override
	public void updateReadcount(int boardNum) {
		boardDAO.updateBoardReadCount(boardNum);
	}

	/**
	 *  개별 게시글 조회 
	 */
	@Override
	public BoardVO getBoard(int boardNum) {
		return boardDAO.getBoard(boardNum);
	}
	
	/**게시글 수정
	 *  
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateBoard(BoardVO boardVO) {
		log.info("BoardService.updateBoard");
		
		boolean result = false;
		
		try {
			boardDAO.updateBoard(boardVO);
			result =true;
			
		} catch(Exception e) {
			log.error("updateBoard error" +e);
			
			result = false;
		}
		
		return result;
	}
	/**
	 * 게시글 댓글 업데이트
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateReplyBoard(BoardVO boardVO) {
		
		log.info("BoardService.updateReplyBoard");
		
		boolean result = false;
		
		try {
			boardDAO.updateBoardRe(boardVO);
			result =true;
			
		} catch(Exception e) {
			log.error("updateReplyBoard error" +e);
			
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 게시글 삭제
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean deleteBoard(int boardNum) {
		log.info("BoardService.deleteBoard");
		
		boolean result = false;
		
		try {
			boardDAO.deleteBoard(boardNum);
			result =true;
			
		} catch(Exception e) {
			log.error("deleteBoard error" +e);
			
			result = false;
		}
		
		return result;
	}
	
	
	/**
	 * 게시글 댓글 삭제
	 */
	@Override
	public boolean deleteOriginalReplyBoards(int boardNum) {
		return false;
	}

	/**게시글 조회
	 * 리스트화
	 */
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

	/**
	 * 전체 게시글 목록 조회( 페이징 적용)
	 * 
	 */
	@Override
	@Transactional(readOnly=true)
	public List<BoardVO> getArticleList(int page, int limit) {
		return boardDAO.pagingBoard(page, limit);
	}
	
	//검색어
	@Override
	@Transactional(readOnly=true)
	public int selectCountSearchingBoards(Map<String, Object> map) {
		return boardDAO.selectCountBoardsSearch(map);
	}
	
	/**게시글 중 이미지 삽입
	 * 
	 */
	@Override
	public List<Integer> getImageList(String str, String imgUploadPath) {

		log.info("BoardService.getImageList");
		List<Integer> imgIdList =new ArrayList<>();
		
		if(str.contains(imgUploadPath) == false) {
			//미포함
			log.info("이미지가 전혀 포함되지 않았습니다.");
		}
		else {
			
			int imgLen= StringUtils.countOccurrencesOf(str, imgUploadPath);
			
			log.info("imgLen : " + imgLen);
			
			// 이미지 검색 카운터 설정 : 이미지 검색할 횟수
			int count = 0;
			
			int initPos = str.indexOf(imgUploadPath);
			log.info("첫 발견 위치 : " + initPos);
			
			// 추출된 문자열 : 반복문에서 사용
			String subStr = str;
			
			while (count < imgLen) {
				
				initPos = subStr.indexOf(imgUploadPath);
				
				// 이미지 파일 테이블 아이디 추출 
				initPos += imgUploadPath.length();
				log.info("이미지 테이블 아이디 시작 위치 추출  : " + initPos);
				
				// 추출된 문자열
				// ex)<img src="/tomato/board/image.do/12" style="width: 662.453px;">
				subStr = subStr.substring(initPos);
				
				log.info(subStr);
				
				// 이미지 파일 끝 검색하여 이미지 파일명/확장자 추출
				// 이미지 끝 검색 : 검색어(" )
				log.info("끝 위치 : " + subStr.indexOf("\" "));
				
				// 이미지 테이블 아이디 추출
				int lasIndex = subStr.indexOf("\" ");
				
				int imgFileId = Integer.parseInt(subStr.substring(0, lasIndex));
				
				//12 
				log.info("이미지 테이블 아이디 : " + imgFileId);
				
				count++; // 이미지 추출되었으므로 카운터 증가
				
				imgIdList.add(imgFileId); // 리스트에 추가
			} //while
		}//if
		
		return imgIdList;
	}
	
	
	/**
	 * ajax 폼점검용 
	 * title
	 * content
	 * orignalFile  */
	
	@Transactional
	@Override
	public BoardVO getBoardTitle(String title) {
		return null;
	}
	
	/**게시글 가져오기
	 * 
	 */
	@Override
	public BoardVO getBoardContent(String content) {
		return null;
	}
	
	/**첨부파일 가져오기
	 * 
	 */
	@Override
	public BoardVO getBoardOrignalFile(String orignalFile) {
		return null;
	}
	
	/**게시글 중 포함된 이미지 삭제
	 * 
	 */
	@Transactional
	@Override
	public boolean deleteContentImg(int id) {
		
		boolean result = false;
		
		try {
			boardDAO.deletContentImg(id);
			result =true;
			
		} catch(Exception e) {
			log.error("deletContentImg error" +e);
			
			result = false;
		}
		
		return result;
	}
	
	/**게시글 원글 에 댓글 가져오기
	 * 
	 */
	@Transactional(readOnly=true)
	@Override
	public List<BoardVO> getboardReplySelect(int boardReRef) {
		
		return boardDAO.getboardReplySelect(boardReRef);
	}

}
