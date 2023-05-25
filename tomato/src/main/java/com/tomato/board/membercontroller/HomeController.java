package com.tomato.board.membercontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tomato.common.vo.CustomUser;
import com.tomato.common.vo.Role;

import groovyjarjarpicocli.CommandLine.Model;
//import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Slf4j
@Controller
public class HomeController {
	
	/*메인 페이지 */
	@GetMapping("/")
	public String home() { 

		log.info("home:");
		
		// 인증 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		log.info("home login auth-1 : " + (auth == null ? "인증안됨" : auth.toString()));
		log.info("home login auth-2 : " + (auth == null ? "인증안됨" : auth.getPrincipal().toString()));
		// log.info("home login auth-3 : " + auth.getAuthorities());
		
		return "home";
	}
	
	/* 로그인 */
	@RequestMapping("/login.do")
	public String login(ModelMap model) {
	    	
		log.info("loginForm");
		
		// javateacher
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
//		
//		log.info("login auth-1 : " + auth);
//		log.info("login auth-2 : " + auth.getPrincipal()==null ? "인증안됨" : auth.getPrincipal().toString());
//		log.info("login auth-3 : " + auth.getAuthorities());
		
		return "member/loginForm";
		// return "layout/layoutLogin"; 
	}
	
	// logout
	@RequestMapping("/logout.do")
	public String logout(Model model,
						 HttpServletRequest request,
						 HttpServletResponse response) {
					
		log.info("logout.do");
				
		Authentication auth 
	    	= SecurityContextHolder.getContext()
	    						   .getAuthentication();
		log.info("auth : "+auth);
	    
	    // logout !
	    if (auth != null) {    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		
		return "member/loginForm"; 
	}
	
	/* 로그인 에러 */
	@RequestMapping("/loginError.do")
    public String loginError(ModelMap model, HttpSession session, HttpServletRequest request) {
		
		log.info("로그인 에러");
		
		// Spring CustomProvider 인증(Auth) 에러 메시지 처리
//		Object secuSess = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
//	
//		// 에러 패치 : dialect + spring security 5.7.x over에 따른 에러 패치 조치 
//		if (secuSess != null) {
//		
//			log.info("#### 인증 오류 메시지 : " + secuSess);
//			log.info("#### 인증 오류 메시지 : " + secuSess.toString());
//		
//		} 
		
		log.info("에러 메시지-1 : "+ request.getAttribute("error"));
		log.info("에러 메시지-2 : "+ request.getAttribute("msg"));
	
		model.addAttribute("error", request.getAttribute("error"));
		model.addAttribute("msg", request.getAttribute("msg"));
		
		// model.addAttribute("error", "true");
		// model.addAttribute("msg", secuSess);
	
		// return "/member/loginForm";
		// 에러 패치(URL "/") : dialect 버그 패치
		return "member/loginForm";
    } //
	
	// @GetMapping("/403") : POST 방식 에러 유발
	@RequestMapping("/403")
	public String access() {
		return "/access";
	}
	
}
