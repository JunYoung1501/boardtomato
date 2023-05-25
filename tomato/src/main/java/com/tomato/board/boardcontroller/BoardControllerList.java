package com.tomato.board.boardcontroller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tomato.board.boardservice.BoardService;
import com.tomato.board.boardvo.BoardVO;
import com.tomato.board.boardvo.PageVO;

import lombok.extern.slf4j.Slf4j;

/* 게시판 리스트 컨트롤러 */
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardControllerList {
	
	@Autowired
	private BoardService boardService;
	
	
	/*게시판 리스트 */	
	@GetMapping("/boardlist.do/{currentPage}")
	public String boardlist(@PathVariable Optional<Integer> currentPage, Model model ) {
		
		log.info("boarlist:");
		
		int limit =10;// 페이지당 글수 
		List<BoardVO> articleList; 
		
		int page = currentPage.isPresent() ? currentPage.get() : 1 ;  // 현재 page
		
		// 총페이지수
		int listCount = boardService.selectCountAllBoard(false); //댓글 미포함 전체 게시글 수
		articleList= boardService.getArticleList(page, limit);
		
		// 총 페이지 수
   		int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
   		int startPage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
//   	    int endPage = startPage + 10; //
   	    int endPage= maxPage;
//   	    if (endPage > maxPage) endPage = maxPage;
   	    
   	    int prevPage = page == 1 ? 1 : page-1 ; // 이전 페이지 
   	    int nextPage = page == endPage ? page : page+1; // 다음 페이지
   	    
   	    PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage); //마지막 페이지
		pageVO.setListCount(listCount); //페이지 계산 (페이지 전체)
		pageVO.setMaxPage(maxPage); // 최대 페이지
		pageVO.setPage(page);  // 현재 페이지
		pageVO.setStartPage(startPage); // 페이지 계산 (페이지 시작)
		pageVO.setPrevPage(prevPage); // 이전 페이지
		pageVO.setNextPage(nextPage); // 이후 페이지 
		
		log.info("pageVO:"+pageVO);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("articleList", articleList);

		return "board/boardlist";
	}
	
	/* 검색 페이지 */
	@GetMapping("/boardSearch.do")
	public  String boardsearch(@RequestParam(value="page", defaultValue="1") int page, // 현재 페이지
							   @RequestParam(value="limit", defaultValue="5") int limit, // 페이지 제한수 
							   @RequestParam(value="orderKey", defaultValue="board_num") String orderKey,
							   @RequestParam(value="orderDirection", defaultValue="DESC") String orderDirection,
							   @RequestParam(value="searchKey", defaultValue="") String searchKey,
							   @RequestParam(value="searchWord", defaultValue="") String searchWord,
							   @RequestParam(value="isReplyContain", defaultValue="false") String isReplyContain,
			Model model)  {
		log.info("전체 게시글 검색");
		
		log.info("searchKey : {}", searchKey);
		log.info("searchWord : {}", searchWord);
		log.info("isReplyContain : {}", isReplyContain);
		
		Map<String, Object> map = new HashMap<>();
		
		List<BoardVO> articleList; 
		
		// 추가 : 댓글들 검색 포함 여부 (기본값 : 댓글 미포함하도록 검색)
		map.put("isReplyContain", isReplyContain);
		
		// 검색 여부에 따른 인자 차이 
		if (searchKey.equals("") == false) {
			map.put("searchKey", searchKey);
			map.put("searchWord", searchWord.trim());			
		} else {
			map.put("searchKey", "");
			map.put("searchWord", "");
		}
		 
		map.put("orderKey", orderKey);
		map.put("orderDirection",orderDirection);
		map.put("page",1);
		map.put("limt",5);
		
		PageVO pageVO =new PageVO();
		pageVO.setPage(page); // 현재 페이지
		pageVO.setLimit(limit); // 페이지 제한 
		pageVO.setStartPage(1); // 시작 페이지 1
		
		log.info("-----:");
		map.forEach((x,y) -> {log.info(x+ "=" + y); });
		
		
		// 변경 
		// : 총 페이지 수에서 댓글 갯수 제외
		// 검색 유무에 따라 조회수 차이
		int listCount = searchKey.equals("") == true ?
						  boardService.selectCountAllBoard(false) : // 정정 : +1 (X)
						  boardService.selectCountSearchingBoards(map);
			
		
		log.info("listCount:"+listCount);
		articleList = boardService.getBoardBySearch(searchKey, searchWord, orderKey, orderDirection, page, limit, isReplyContain);
		
		int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int startPage = (((int) ((double)page /limit + 0.9)) - 1) * limit + 1;
		int endPage = maxPage;
	   	    
	   	int prevPage = page == 1 ? 1 : page-1 ; // 이전 페이지 
	   	int nextPage = page == endPage ? page : page+1; // 다음 페이지
	   	
		log.info("maxPage:"+maxPage);
		log.info("articleList size:" +articleList.size());
		
		pageVO.setMaxPage(maxPage); // 페이지 최대점
		pageVO.setPrevPage(prevPage); // 이전 페이지
		pageVO.setNextPage(nextPage); // 이후 페이지
		pageVO.setPage(page); // 현재 페이지
		pageVO.setLimit(limit); // 페이지 제한
		pageVO.setEndPage(endPage); // 마지막 펭지 
		
		model.addAttribute("pageVO", pageVO); 
		model.addAttribute("articleList", articleList);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchWord", searchWord);
		
		return "board/boardlist";
	}
}
