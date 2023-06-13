package com.tomato.board.admincontroller;

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

import com.tomato.board.boardvo.PageVO;
import com.tomato.board.memberservice.MemberService;
import com.tomato.board.membervo.MemberVO;

import lombok.extern.slf4j.Slf4j;

/**
 *  관리자 회원관리 컨트롤러
 *  
 *  회원검색 
 * @author 문준영
 *
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

	@Autowired 
	private MemberService memberService;
	
	
	/**회원 리스트
	 * @return "admin/memberlist"
	 * 인자 currentPage(현재 체이지),
	 * model 
	 */
	@RequestMapping("/memberlist.do/{currentPage}")
	public String memberList(@PathVariable Optional<Integer> currentPage, Model model) {
		
		log.info("MemberList:");
		
		int limit=5; //페이지당 회원수
		List<MemberVO> memberList;	
		
		int page = currentPage.isPresent() ? currentPage.get() : 1 ;  // 현재 page
		
		//총 페이지수
		int listCount = memberService.selectAllCountMember();
		memberList = memberService.selectAllMembers( page,  limit);
		
		int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int startPage = (((int) ((double)page / limit + 0.9)) - 1) * limit + 1;
		int endPage= maxPage;
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
		pageVO.setLimit(limit); // 페이지당 회원수
		
		
		log.info("pageVO:"+pageVO);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("memberList", memberList);
		
		return "admin/memberlist";
	}
	
	/**
	 *  전체 회원 검색 
	 * @param page 현재 페이지
	 * @param limit  페이지 제한
	 * @param searchKey  검색 ex) 회원 아이디  회원 이름
	 * @param searchWord   검색어
	 * @param model
	 * @return
	 */
	@GetMapping("/memberSearch.do")
	public  String boardsearch(@RequestParam(value="page", defaultValue="1") int page, // 현재 페이지
			   @RequestParam(value="limit", defaultValue="5") int limit, // 페이지 제한수 
			   @RequestParam(value="searchKey", defaultValue="") String searchKey,
			   @RequestParam(value="searchWord", defaultValue="") String searchWord,
			   Model model)  {
		
		log.info("전체 회원 검색");
		
		log.info("searchKey : {}", searchKey);
		log.info("searchWord : {}", searchWord);
		
		Map<String, Object> map = new HashMap<>();
		
		List<MemberVO> memberList;
		
		
		PageVO pageVO =new PageVO();
		pageVO.setPage(page); // 현재 페이지
		pageVO.setLimit(limit); // 페이지 제한 
		pageVO.setStartPage(1); // 시작 페이지 1
		
		
		log.info("-----:");
		map.forEach((x,y) -> {log.info(x+ "=" + y); });
		int listCount =  memberService.selectMembersCountSearching(searchKey, searchWord);
		

		log.info("listCount:"+listCount);
		memberList = memberService.selectMemberSearching(page, limit, searchKey, searchWord);
		
		int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int startPage = (((int) ((double)page /limit + 0.9)) - 1) * limit + 1;
		int endPage = maxPage;
			    
		int prevPage = page == 1 ? 1 : page-1 ; // 이전 페이지 
		int nextPage = page == endPage ? page : page+1; // 다음 페이지
			
		log.info("maxPage:"+maxPage);
		log.info("memberList size:" +memberList.size());
		
		pageVO.setMaxPage(maxPage); // 페이지 최대점
		pageVO.setPrevPage(prevPage); // 이전 페이지
		pageVO.setNextPage(nextPage); // 이후 페이지
		pageVO.setPage(page); // 현재 페이지
		pageVO.setLimit(limit); // 페이지 제한
		pageVO.setEndPage(endPage); // 마지막 페이지
		
		model.addAttribute("pageVO", pageVO); 
		model.addAttribute("memberList", memberList);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchWord", searchWord);
		
		return "admin/memberlist";
	}
	
}
