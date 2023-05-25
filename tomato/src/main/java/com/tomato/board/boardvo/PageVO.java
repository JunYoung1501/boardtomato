package com.tomato.board.boardvo;

import lombok.Data;

@Data
public class PageVO {
	
	private int page; // 현재 페이지
	private int maxPage; // 총 페이지
	private int limit;// 페이지 당 회원 정보 수
	private int startPage; // 시작 페이지
	private int endPage; // 마지막 페이지
	private String orderKey; // 추가 사항(게시판)
	private int listCount; // 페이지당 게시글수
	private int prevPage; // 이전 페이지
	private int nextPage; // 다음 페이지
	private String searchKey; // 검색 구분
	private String searchWord; // 검색어
	private String orderDirection;//추가 사항 (게시판)
	
}
