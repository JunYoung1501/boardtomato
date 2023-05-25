package com.tomato.board.membervo;

import java.math.BigDecimal;
import java.sql.Date;

//import jakarta.persistence.Column;

public class MemberVO {
	/* id */
	private String id;
	/* pwd */
	private String pwd;
	/* name */
	private String name;
	/* phone */
	private String phone;
	/* email */
	private String email;
	/* 생일 */
	private String birthday;
	/* 우편주소 */
	private String postadd;
	/* 집주소 */
	private String address;
	/* 상세주소 */
	private String detailaddr;
	/*가입날짜*/	
	private Date joinday;
	/*활동 여부
	 * ex) 1 = 활동 , 2 = 휴면 , 3 = 탈퇴
	 * */	
	private int status;
	//@Column(name="user_id", precision=19, scale=0)
	//private BigDecimal userId;
	
//	@Column(name="password")
//	private String password;
	
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
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", phone=" + phone + ", email=" + email
				+ ", birthday=" + birthday + ", postadd=" + postadd + ", address=" + address + ", detailaddr="
				+ detailaddr + ", joinday=" + joinday + ", status=" + status + "]";
	}

}
