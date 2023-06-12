package com.tomato.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tomato.common.service.CustomProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	CustomProvider customProvider;

	/**
	 * 비밀번호 암호화
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 *  웹 시큐리티 모든 허용
	 * @return
	 */
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
		
    	// swagger config
    	return (web) -> web.ignoring().antMatchers(
    			"/webjars/**","/bootstrap/**","/jquery/**",
    			"/resources/**","/summernote/**","/images/**",
    			"/","/upload/**","/bootstrap_icons/**","/board/image.do","/error/**");
	}
	
	/**
	 * 각 상황별 시큐리티 허용 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		log.info("configure :");
		
	//	http.userDetailsService(userDetailsService);
		http.userDetailsService(customProvider);
		http.authenticationProvider(customProvider);
		http.headers().frameOptions().sameOrigin(); 
		
		//http.csrf().disable();//임시
		
		http
		.csrf().ignoringAntMatchers("/member/join.do","/member/joinProc.do","/loginProc.do");
		// csrf 토큰 미생성 오류 패치 
		
		http
		.authorizeHttpRequests()
		// 해당 url을 허용한다.//bootstrap js 오류로인한 추가						    
        .antMatchers("/images/**",
        			"/bootstrap/**",
        			"/bootstrap_icons/**",
        			"/loginError.do",
        			"/login.do",
        			"/member/join.do",
        			"/member/joinProc.do",
        			"/member/idcheck.do",
        			"/member/emailcheck.do", 
        			"/member/phonecheck.do",
        		"/board/boardlist.do/**",
        		"/board/boardSearch.do").permitAll()
        
        // admin 폴더에 경우 admin 권한이 있는 사용자에게만 허용 
        .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
        // user 폴더에 경우 user 권한이 있는 사용자에게만 허용
       .antMatchers("/board/boardWriterProc.do","/board/boardRead.do",
    		   	"/board/boardUpdate.do",
    		   	"/board/boardReplyWriterProc.do",
       			"/board/boardReplyUpdate.do",
       			"/board/boardReplyDelete.do",
       			"/member/memberUpdate.do",
       			"/member/memberDelete.do",
       			"/member/myPage.do",
       			"/member/memberUpdateProc.do",
       			"/member/emailUpdateCheck.do", 
    			"/member/phoneUpdateCheck.do")
      //  .hasAuthority("ROLE_USER") //5.7.1 버전 사용시 hasRole 은 사용 지양
       .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/loginProc.do")
        .successHandler(new CustomAuthenticationSuccess()) // 로그인 성공 핸들러 
        .failureHandler(new CustomAuthenticationFailure()) // 로그인 실패 핸들러
        .permitAll()
        .and()
      // .logout()
      .logout()
        .logoutUrl("/logout.do") // 로그아웃시 
        .logoutSuccessUrl("/login.do") // 로그인 성공시  
        .permitAll()
        .and()
        .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    
		http.headers().frameOptions().sameOrigin();
		
		return http.build();
	}
  
}//
