package com.tomato.board.membervo;

import java.sql.Date;


//import jakarta.persistence.Column;

public class MemberVO {
	/**
	 *  id 
	 */
	private String id;
	
	/** 
	 * pwd 
	 */
	private String pwd;
	
	/**
	 *  pwd1 
	 *  비밀번호 확인차
	 */
	private String newPwd1;
	
	/**
	 *  pwd2
	 *  pw1 과 pwd2 의 동일 확인차
	 */
	private String newPwd2;
	
	/**
	 *  name  
	 *  회원 성명
	 */
	private String name;
	
	/**
	 *  phone
	 * 회원 휴대전화
	 */
	private String phone;

	/**
	 *  email
	 *  회원 이메일 
	 */
	private String email;
	
	/**
	 * 회원  생일 
	 * 
	 */
	private String birthday;
	
	/**
	 *  회원 우편주소
	 * 
	 */
	private String postadd;
	
	/** 집주소 
	 * 구 지번 
	 */
	private String address;
	
	/**
	 *  상세주소
	 * 
	 */
	private String detailaddr;
	
	/**
	 * 가입날짜
	 * 	
	 */
	private Date joinday;
	
	/**
	 * Role(역할)
	 * ADMIN / USER
	 */
	private String role;
	
	/**활동 여부
	 * ex) 1 = 활동 , 0 = 휴면 , 2 = 탈퇴
	 * */	
	private int status;
	
	
	public MemberVO() {
	}



	/* getter setter */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPostadd() {
		return postadd;
	}
	public void setPostadd(String postadd) {
		this.postadd = postadd;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailaddr() {
		return detailaddr;
	}
	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}
	public Date getJoinday() {
		return joinday;
	}
	public void setJoinday(Date joinday) {
		this.joinday = joinday;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getNewPwd1() {
		return newPwd1;
	}



	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1;
	}

	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", newPwd1=" + newPwd1 + ", newPwd2=" + newPwd2 + ", name="
				+ name + ", phone=" + phone + ", email=" + email + ", birthday=" + birthday + ", postadd=" + postadd
				+ ", address=" + address + ", detailaddr=" + detailaddr + ", joinday=" + joinday + ", role=" + role
				+ ", status=" + status + "]";
	}

	
}
