package com.tomato.common.vo;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	
	/** 회원 아이디*/
	private String username;
	
	/** 회원 ROLE(역할)*/
	private String role;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	//전재 조건 
	//role (역할) =권한 
	@Override
	public String getAuthority() {
		return this.role;
	}

	@Override
	public String toString() {
		return "Role [username=" + username + ", role=" + role + "]";
	}

}
