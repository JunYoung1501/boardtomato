package com.tomato.board.membercontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tomato.board.memberservice.IntMemberSerivce;
import com.tomato.board.membervo.MemberVO;

//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	IntMemberSerivce iMemberService;
	
	
	/** 
	 * 가입형식
	 * @return
	 */
	@GetMapping("/join.do")
	public String join() {
		log.info("회원가입폼");
		
		return "member/signup";
	}
	
	/**
	 *  로그인시 마이페이지
	 * @return
	 */
	@RequestMapping("/myPage.do")
	public String myPage() {
		log.info("myPage");
		
		return "myPage";
	}
	
	
	/** 
	 * 회원가입 처리 
	 * @param memberVO
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping(value="/joinProc.do") 
	public String joinProc(MemberVO memberVO , Model model, HttpServletRequest request) {
		log.info("회원가입 처리:"+memberVO);
		
		String msg=iMemberService.insertMember(memberVO);
		
		log.info("--msg:"+msg);
		//service insert msg 참 / 거짓 참 => login page 거짓 =>msg 오류 처리 페이지 
		if(msg.equals("회원정보가입에 성공하였습니다"))
		{
			return "redirect:/";
		}
		else{
			model.addAttribute("error_msg", msg);
			model.addAttribute("move_page", request.getContextPath()+"/member/join.do");
			return "/error/error";
		}
	}
	

}
