package com.tomato.common.service;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.DisabledException;
import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.CustomUser;
import com.tomato.common.vo.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomProvider implements AuthenticationProvider, UserDetailsService{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/** 회원 아이디 유무 
	 * 
	 * 
	 */
	@Override
	public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername:"+username);
		 
		MemberVO memberVO = new MemberVO();
		CustomUser customUser =null;
		
		try {
	    	memberVO=(MemberVO)jdbcTemplate.queryForObject(
	    			 "SELECT * FROM member WHERE id=?", 
				     new BeanPropertyRowMapper<MemberVO>(MemberVO.class),
				     new Object[]{ username });
	    	
	    	customUser = new CustomUser(memberVO);
	    	
	    	//보완
	    	if(customUser.isEnabled()== false) {
        		throw new DisabledException("휴면/삭제계정입니다");
        	}
	    	
	    } catch (EmptyResultDataAccessException e) {
	    	log.info("error1");
	    //	return null;
	    } catch(DisabledException e ) {
	    	log.info("loadUserByUsername_error:"+e);
	    }
		return customUser;
	}
	
	/**
	 * 사용자 Role(역할) 정의
	 * 
	 * @param username
	 * @return
	 */
	private List<Role> loadUserRole(String username) {
		
		log.info("loadUserRole");
    	
		try {
			return (List<Role>)jdbcTemplate.query(
	   			 	"SELECT username, role FROM user_roles WHERE username=?", 
				     new BeanPropertyRowMapper<Role>(Role.class),
				     new Object[]{ username });
			
		} catch (EmptyResultDataAccessException e) {
			log.info("error2");
	    	return null;
	    }
		
	}
	
	/** 
	 * 회원 암호화 및 일치 여부 
	 * 
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("authenticate");
		
		String username = authentication.getName();
        String password = "";

        CustomUser customUser = null;
        Collection<? extends GrantedAuthority> authorities = null;
        
        try {
        		if (username.trim().equals("")) {
        			throw new UsernameNotFoundException("회원 아이디를 입력하십시오."); 
        		}
        		
        		// 회원 여부 점검
	        	if (this.loadUserByUsername(username) == null) {
	        		throw new UsernameNotFoundException("회원 아이디가 없습니다.");
	        	}
	        	
	        	customUser = this.loadUserByUsername(username);
	            
	        	//휴면/삭제 점검
	        	if(customUser.isEnabled()== false) {
	        		throw new DisabledException("휴면/삭제계정입니다");
	        	}
	        	log.info("user(사용자 현황) : " + customUser);

	        	// 비밀번호 비교
	            password = (String) authentication.getCredentials(); // 비교할 비밀번호 
	            
	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            
	            if (passwordEncoder.matches(password, customUser.getPassword())) 
	            	log.info("비밀번호 일치 !");	
	            else 
	            	throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
	            
	            // 롤(Role) 목록 확보
	            List<Role> roles = this.loadUserRole(username); 
	            log.info("roles.size:"+roles.size());
	            if(roles.size()==0) {
	            	throw new DisabledException("휴면/삭제계정입니다");
	            }
	         //   log.info("롤(Role) : " + roles.get(0));
	            
	            customUser.setAuthorities(roles);
	            
	            authorities = customUser.getAuthorities();
	            
	            log.info("authorities : " + authorities);
            
        } catch(UsernameNotFoundException e) {
            log.info("사용계정 예외:"+e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
            log.info(e.toString());
            throw new BadCredentialsException(e.getMessage());
        } catch(DisabledException e) {
            throw new DisabledException(e.getMessage());
        } catch(Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        log.info("authorities2 : " + authorities);
        return new UsernamePasswordAuthenticationToken(customUser, password, authorities);
	}
	
	/**
	 *  회원 권한 
	 * 
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
