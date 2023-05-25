package com.tomato.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFailure implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("로그인 실패 / 에러 처리:");
		
		// 에러 처리
		String msg = "";
		
		// javateacher : dialect + spring security 5.7.x over에 따른 버그 패치
		if (exception instanceof BadCredentialsException) {
			// msg = exception.getMessage();
			msg = "비밀번호가 일치하지 않습니다.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			msg = "회원 아이디가 없습니다.";
		} else if (exception instanceof UsernameNotFoundException) {
			msg = "회원 아이디를 입력하십시오.";
		}
			
		// request.setAttribute("username", request.getParameter("username"));
		
		// 에러 메시징
		request.setAttribute("msg", msg);
		request.setAttribute("error", "true");
		
		request.getRequestDispatcher("/loginError.do").forward(request, response);
	}
}
