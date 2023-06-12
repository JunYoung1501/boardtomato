package com.tomato.board.boardvo;

import lombok.Data;

@Data
public class PageVO {
	
	/**
	 *  현재 페이지
	 */
	private int page;
	
	/**
	 *  총 페이지
	 */
	private int maxPage; 
	
	/**
	 *  페이지 제한
	 */
	private int limit;
	
	/**
	 *  시작 페이지
	 */
	private int startPage; 
	
	/**
	 *  마지막 페이지
	 */
	private int endPage; 
	
	/**
	 *  추가 사항(게시판)
	 */
	private String orderKey; 
	
	/**
	 *  페이지당 수 
	 */
	private int listCount; 
	
	 /**
	  *  이전 페이지
	  */
	private int prevPage; 
	
	 /**
	  *  다음 페이지
	  */
	private int nextPage; 
	
	 /**
	  *  검색어 구분 
	  */
	private String searchKey; 
	
	 /**
	  *  검색어 
	  */
	private String searchWord; 
	
	 /**
	  *  추가 사항(게시판)
	  */
	private String orderDirection;
	
}
