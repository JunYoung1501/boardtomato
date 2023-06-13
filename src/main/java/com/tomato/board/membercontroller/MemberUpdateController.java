package com.tomato.board.membercontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tomato.board.memberservice.MemberService;
import com.tomato.board.membervo.MemberVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원정보 처리 컨트롤러
 *  
 * @author 문준영
 *
 */
@Controller
@Slf4j
@RequestMapping("/member")
public class MemberUpdateController {
	
	@Autowired
	MemberService  memberService;
	
	/**
	 *  회원 정보 보기
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/memberView.do")
	public String memberView(@RequestParam("id") String id , Model model)
	{
		log.info("회원 정보 조회");
		
		// 기존 회원 조회
		MemberVO member = memberService.getMember(id);
		log.info("member:"+ member);
		
		model.addAttribute("memberVO" , member);
		
		return  "member/viewuser";
	}
	
	/**
	 * 회원 정보 수정 
	 * @return
	 */
	@GetMapping("/memberUpdate.do")
	public String memberUpdate(@RequestParam("id") String id , Model model ,HttpSession session)
	{
		log.info("회원 정보 수정(폼)");
		
		// 기존 회원 조회
		MemberVO member = memberService.getMember(id);
		log.info("member:"+ member);
		
		if(session.getAttribute("defaultMemberVO") == null) {
			session.setAttribute("defaultMemberVO", member)	;
		}
		
		model.addAttribute("memberVO" , member);
		
		return  "member/updateuser";
	}
	
	/**
	 * 회원정보 수정처리 
	 * 
	 * @param request
	 * @param session
	 */
	@PostMapping("/memberUpdateProc.do")
	public String memberUpdateProc(@ModelAttribute("memberVO") MemberVO updateMemberVO,
									Model model,
									HttpServletRequest request,
									//SessionStatus sessionStatus,
									HttpSession session) 
	{
		String msg=""; // 메세지
		String movePath=request.getContextPath()+"/member/memberUpdate.do?id="+updateMemberVO.getId(); //에러시 이동 페이지
		
		log.info("회원 정보:" +updateMemberVO);
		
		MemberVO defaultMemberVO = (MemberVO)session.getAttribute("defaultMemberVO");
		
		log.info("기존 정보:"+defaultMemberVO);
		log.info("수정 정보:"+updateMemberVO);
		
		
		//기존 데이터와 비교시 데이터 변경되었을때
		if (defaultMemberVO.equals(updateMemberVO) == false) {
			
			log.info("기존 데이터:{}"+defaultMemberVO.getId());
			log.info("신규 수정 데이터:{}"+updateMemberVO.getId());
			
			if (memberService.updateMember(updateMemberVO) == true) {
				msg = "회원 정보 수정 성공하였습니다";
				movePath=request.getContextPath()+"/member/memberView.do?id="+updateMemberVO.getId(); //성공시 이동
				// 성공시 세션(기존회원 정보)  삭제
				session.removeAttribute("defaultMemberVO");
			}
			else {
				msg = "회원 정보 수정 실패하였습니다";
			}
			
		}
		else {
			//변경사항이 없을때
			msg = "변경된 내용이 없습니다";
			movePath=request.getContextPath()+"/member/memberView.do?id="+updateMemberVO.getId(); //성공시 이동
		}
	
		model.addAttribute("error_msg",msg);
		model.addAttribute("move_page",movePath);
		
		return "/error/error";
	}
	
	
}
