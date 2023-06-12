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
	
	/** 메인 페이지 
	 * 
	 * @return
	 */
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
	
	/**
	 *  로그인 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/login.do")
	public String login(ModelMap model) {
	    	
		log.info("loginForm");
		return "member/loginForm";
	}
	
	/**
	 * 로그인 처리 
	 * 
	 * @return
	 */
	@RequestMapping("/loginProc.do")
	public String loginProc() {
		log.info("loginProc");
		
		log.info("logout.do");
		
		Authentication auth 
	    	= SecurityContextHolder.getContext()
	    						   .getAuthentication();
		log.info("auth : "+auth);
		
		return "home";
	}
	
	
	/** logout 
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
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
	
	/** 
	 * 로그인 에러 
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginError.do")
    public String loginError(ModelMap model, HttpSession session, HttpServletRequest request) {
		
		log.info("로그인 에러");
		log.info("에러 메시지-1 : "+ request.getAttribute("error"));
		log.info("에러 메시지-2 : "+ request.getAttribute("msg"));
	
		model.addAttribute("error", request.getAttribute("error"));
		model.addAttribute("msg", request.getAttribute("msg"));
		
		return "member/loginForm";
    } //
	
	/**
	 *  403 에러 페이지 
	 * @return
	 */
	@RequestMapping("/403")
	public String access() {
		return "/access";
	}
	
}
