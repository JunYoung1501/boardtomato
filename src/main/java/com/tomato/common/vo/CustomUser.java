package com.tomato.common.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tomato.board.membervo.MemberVO;

public class CustomUser implements UserDetails {

	/**
	 *  회원 아이디
	 * 
	 */
	private String username;
	
	/**
	 *  회원 비밀번호
	 * 	
	 */
    private String password;
	
    
    /**
     *  Spring Security related fields 
     */
    private List<Role> authorities;
    
    /**
     * 계정 존재 하징낳을떄
     */
    private boolean accountNonExpired = true;
    
    /**
     *  계정 휴면일때
     */
    private boolean accountNonLocked = true;
    
    /**
     * 
     */
    private boolean credentialsNonExpired = true;
    
    /**
     * 
     */
    private boolean enabled = true;
    
    public CustomUser() {}
    
    /**
     * 회원 활동 여부 
     * @param memberVO
     */
    public CustomUser(MemberVO memberVO) {
  		this.username = memberVO.getId();
  		this.password = memberVO.getPwd();
  		this.enabled = memberVO.getStatus()==1 ?  true : false;
  	}

     /**
      *  고객 권한(User)
      */
  	public CustomUser(String username, String password, boolean enabled) {
  		this.username = username;
  		this.password = password;
  		this.enabled = enabled;
  	}
    
  	/**
  	 * 권한 추가
  	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	//toString()
	@Override
	public String toString() {
		return "CustomUser [username=" + username + ", password=" + password + ", authorities=" + authorities
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + "]";
	}

}
