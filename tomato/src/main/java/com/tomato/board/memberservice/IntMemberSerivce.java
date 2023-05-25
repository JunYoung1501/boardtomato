package com.tomato.board.memberservice;

import java.util.List;

import com.tomato.board.membervo.MemberVO;

public interface IntMemberSerivce {
	/*데이터 주입*/
	String insertMember(MemberVO member);
	/*아이디 조회*/	
	MemberVO getMember(String id);
	/*회원가입 주입*/
	String updateMember(MemberVO member);
	/*계정삭제*/	
	String deleteMember(String id);
	/*계정조회*/
	List<MemberVO> getAllMembers();
	boolean hasMember(String id);
	MemberVO getOneMember(MemberVO member);
	boolean isMember(String field,String value);
	boolean isPhone (String id,String phone);
}
