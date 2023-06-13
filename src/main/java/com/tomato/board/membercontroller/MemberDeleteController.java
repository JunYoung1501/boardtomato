package com.tomato.board.membercontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tomato.board.memberservice.MemberService;
import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.Role;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원 삭제 컨트롤러
 * @author 문준영
 *
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberDeleteController {

	@Autowired
	MemberService memberService;
	
	/**
	 * 회원 삭제(휴면계정)
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/memberDelete.do")
	public String memberDelete(@RequestParam("id") String id, Model model , HttpServletRequest request ,HttpServletResponse response) {
		
		log.info("memberDelete:");
		
		String movePath=request.getContextPath()+"/member/myPage.do"; //에러시 이동 페이지
		// 기존의 role 획득
		MemberVO member = memberService.getMember(id);
		List<String> roles = memberService.selectMemberRole(id);
		
		// admin => ROLE_ADMIN, user => ROLE_USER
		
		
	    Role role = new Role(); 
	    role.setUsername(id);
		
	    String msg="";
	    
	    Authentication auth 
    	= SecurityContextHolder.getContext()
    						   .getAuthentication();
	    log.info("auth : "+auth);
    
	    
		// 관리자 권한
		if (roles.contains("ROLE_ADMIN") == true ) { 

			role.setRole("ROLE_ADMIN");
			memberService.deleteRole(role);	
			role.setRole("ROLE_USER");
			memberService.deleteRole(role);	
			member.setStatus(0); // 휴면계정으로 치환 
			log.info("회원정보:" + member);
			memberService.updateMember(member); // 회원정보에 반영
			 
			msg += "회원 탈퇴하셨습니다";
			movePath =request.getContextPath()+"/home.do"; 
			// logout !
		    if (auth != null) {    
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		    }
		
		// 사용자 권한  
		} else if(roles.contains("ROLE_USER") == true ) {
			
			
			role.setRole("ROLE_USER");
			memberService.deleteRole(role);				 
			msg += "회원 탈퇴하셨습니다.";
			member.setStatus(0); // 휴면계정으로 치환 
			log.info("회원정보:" + member);
			memberService.updateMember(member); // 회원정보에 반영
			movePath =request.getContextPath()+"/home.do"; 
			// logout !
		    if (auth != null) {    
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		    }
		} 
		
		else {
			msg += "회원 탈퇴가 실패되었습니다";
		}
		
		
		model.addAttribute("error_msg",msg);
		model.addAttribute("move_page",movePath);
		
		return "/error/error";
	}
	
}
