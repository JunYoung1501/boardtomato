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
//@EnableGlobalMethodSecurity(sercuredEnabled=true)
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	CustomProvider customProvider;

//	@Autowired
//	private UserDetailsService userDetailsService;
	//private DataSource dataSourcre;
	
//	public WebSercurityConfig(UserDetailService userDetailService,
//							  DataSource dataSource) {
//		log.info("생성자 주입 wirting:");
//		this.dataSourcre =dataSource;
//		this.userDetailService=userDetailService;
//	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
		
    	// swagger config
    	return (web) -> web.ignoring().antMatchers(
    			"/webjars/**","/bootstrap/**","/jquery/**","/resources/**","/summernote/**","/images/**","/","/upload/**","/bootstrap_icons/**");
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		log.info("configure :");
		
	//	http.userDetailsService(userDetailsService);
		http.userDetailsService(customProvider);
		http.authenticationProvider(customProvider);
		http.headers().frameOptions().sameOrigin(); 
		
		//http.csrf().disable();//임시
		
		http
		.authorizeHttpRequests()
		// 해당 url을 허용한다.//bootstrap js 오류로인한 추가						    
        .antMatchers("/","/images/**","/bootstrap/**","/bootstrap_icons/**","/loginError.do","/member/join.do",
        		"/member/joinProc.do","/member/idcheck.do"
        		,"/member/emailcheck.do", "/member/emailcheck.do"
        		,"/member/phonecheck.do","/test.do","/board/**",
        		"/board/boardWriterProc.do","/board/boardlist.do/*","/board/image.do").permitAll()
        // admin 폴더에 경우 admin 권한이 있는 사용자에게만 허용 
       // .requestMatchers("/admin/**").hasAuthority("ADMIN")
        // user 폴더에 경우 user 권한이 있는 사용자에게만 허용
        //.requestMatchers("/member/**").hasAuthority("USER")
       // .requestMatchers("/member/update.do").hasAuthority("USER")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login.do")
        .successHandler(new CustomAuthenticationSuccess()) // 로그인 성공 핸들러 
        .failureHandler(new CustomAuthenticationFailure()) // 로그인 실패 핸들러
        .permitAll()
        .and()
      // .logout()
      .logout()
        .logoutUrl("/logout.do") // javateacher 추가
        .logoutSuccessUrl("/login.do") // javateacher 추가
        .permitAll()
        .and()
       .exceptionHandling().accessDeniedPage("/403"); // 권한이 없을경우 해당 url로 이동
    
    // 추가된 부분 : remember-me 관련
    // remember-me cookie
    // => 사용자이름 + cookie expired time(만료 시간) + 패쓰워드 => Base64(Md5방식) 암호화
    /*
     * base64(username + ":" + expirationTime + ":" +
              md5Hex(username + ":" + expirationTime + ":" password + ":" + key))
     */
//    http.rememberMe()
//        .key("tomato")
//        .userDetailsService(userDetailsService)
//        .tokenRepository(getJDBCRepository())
//        .alwaysRemember(true) // 로그인시 remember-me choeckbox의 체크와 무관하게 실행
//        .tokenValiditySeconds(60*60*24); // 24시간(1일)
		
		
		http.headers().frameOptions().sameOrigin();
	
		
		
		
		return http.build();
	}
  
	// 추가된 remember-me 관련 메서드
//	private PersistentTokenRepository getJDBCRepository() {
//	  
//	  JdbcTokenRepositoryImpl repo 
//	  	= new JdbcTokenRepositoryImpl();
//	  
//	  repo.setDataSource(dataSource);
//	  
//	  return repo;
//	}
}//
