package com.tomato.board.memberservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomato.board.memberdao.MemberDAO;
import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService implements IntMemberSerivce {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	MemberDAO memberDAO;
	
	
	/*
	 * 정보 주입시 
	 * 패스워드 암호화 
	 * Role 
	 * 1 활동 2휴면 3탈퇴 
	 * */
	@Transactional
	@Override
	public String insertMember(MemberVO member) {
		String msg ="";
		
		try {
			//패스워드 암호화
			String encodedPassword=	passwordEncoder.encode(member.getPwd());
			log.info("password:"+encodedPassword);
			log.info("passwordEncoded:"+member.getPwd());
			member.setPwd(encodedPassword);
			
			memberDAO.insert(member);
			Role role = new Role();
			role.setUsername(member.getId());
			role.setRole("ROLE_USER");//사용자 가입
								
			memberDAO.insertRole(role);
			
			msg="회원정보가입에 성공하였습니다";
		}catch(Exception e) {
			log.error("insert:"+e);
			msg="회원정보등록에 실패했습니다";
		}
		return msg;
	}
	
	@Transactional(readOnly = true)
	@Override
	public MemberVO getMember(String id) {
		
		return memberDAO.select(id);
	}
	
	@Transactional
	@Override
	public String updateMember(MemberVO member) {
		String msg ="";
		
		try {
			memberDAO.update(member);
			msg="회원정보수정에 성공하였습니다";
		}catch(Exception e) {
			log.error("update:"+e);
			msg="회원정보수정에 실패했습니다";
		}
		return msg;
	}
	@Transactional
	@Override
	public String deleteMember(String id) {
		String msg ="";
		
		try {
			memberDAO.delete(id);
			msg="회원정보삭제에 성공하였습니다";
		}catch(Exception e) {
			log.error("delete:"+e);
			msg="회원정보삭제에 실패했습니다";
		}
		return msg;

	}
	
	@Transactional(readOnly = true)
	@Override
	public List<MemberVO> getAllMembers() {
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean hasMember(String id) {
		return false;
	}
	
	@Transactional(readOnly = true)
	@Override
	public MemberVO getOneMember(MemberVO member) {
	
		return memberDAO.select(member);
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean isMember(String field, String value) {
		
		return memberDAO.isMember(field, value);
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean isPhone(String id, String phone) {
		String tempPhone= memberDAO.select(id).getPhone(); 
		return (tempPhone== null ? "": tempPhone).equals(phone);
	}

}
