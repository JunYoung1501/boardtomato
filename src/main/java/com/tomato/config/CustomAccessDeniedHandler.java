package com.tomato.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

import com.tomato.common.vo.CustomUser;
import com.tomato.common.vo.Role;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	/**
	 * 인증 확인 절차 
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException e) throws IOException, ServletException {
		
		Object principal = null ;
		log.info("인증정보:"+SecurityContextHolder.getContext().getAuthentication());
		
		if( SecurityContextHolder.getContext().getAuthentication() != null) {
			
			CustomUser customUser = (CustomUser)principal;
			List<Role> roles = (List<Role>)customUser.getAuthorities();
			
			log.info("접근 권한(인가) 에러 처리:" + e);
			log.info("인증 여부 : " + request.authenticate(response));
			log.info("현재 접근 권한 : " + roles.get(0).getAuthority());
			log.info("현재 접근 롤 : " + roles.get(0).getRole());
		}
		else {
			log.info("인증안됨:"+e);
			request.setAttribute("msg", "인증되지 않았습니다.");
			//request.setAttribute("move_page", "/home.do");
			//RequestDispatcher rd = request.getRequestDispatcher("/error/error");
			//rd.forward(request, response);
			
		}
		
		// 에러 메시징
		request.setAttribute("msg", "접근 권한이 없습니다.");
		request.setAttribute("error", "true");
		
		redirectStrategy.sendRedirect(request, response, "/loginError.do");
	} //
}