package com.tomato.board.memberservice;

import java.util.List;
import java.util.Map;

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
	
	
	/**
	 * 정보 주입시 
	 * 패스워드 암호화 
	 * Role 
	 * 1 활동 0휴면 
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
	
	/** 회원 아이디 가져오기 */
	@Transactional(readOnly = true)
	@Override
	public MemberVO getMember(String id) {
		
		return memberDAO.selectMember(id);
	}
	
	/** 회원 정보 수정 
	 * 
	 */
	@Transactional
	@Override
	public boolean updateMember(MemberVO member) {
		String msg ="";
		boolean result = false;
		try {
			memberDAO.updateMember(member);
			result =true;
			//msg="회원정보수정에 성공하였습니다";
		}catch(Exception e) {
			log.error("update:"+e);
			//msg="회원정보수정에 실패했습니다";
			result =false;
		}
		return result;
	}
	
	/**
	 *   회원 정보 삭제 
	 */
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
	
	/**
	 *   회원 정보 유무 
	 */
	@Transactional(readOnly = true)
	@Override
	public boolean hasMember(String id) {
		return memberDAO.hasMember(id);
	}
	
	/**
	 *   가입시 회원 중복 여부
	 */
	@Transactional(readOnly = true)
	@Override
	public MemberVO getOneMember(MemberVO member) {
	
		return memberDAO.select(member);
	}
	
	/**
	 *   회원정보 중복 여부 map 화 
	 */
	@Transactional(readOnly = true)
	@Override
	public boolean isMember(String field, String value) {
		
		return memberDAO.isMember(field, value);
	}
	
	/**
	 *   회원 휴대폰 번호 중복 조회 
	 */
	@Transactional(readOnly = true)
	@Override
	public boolean isPhone(String id, String phone) {
		String tempPhone= memberDAO.selectMember(id).getPhone(); 
		return (tempPhone== null ? "": tempPhone).equals(phone);
	}
	
	/**
	 *   검색어로 회원 찾기 email , phone , status
	 */
	@Transactional(readOnly = true)
	@Override
	public String isLikeSerachWord(String searchKey) {
		log.info("MemberService.isLikeSearchWord");
		
		String result ="";
		
		if(searchKey.equals("email") ||
		   searchKey.equals("phone") ||
		   searchKey.equals("status")) 
		{
		 result = "=";	
		}
		else {
			result ="like";
		}
		
		return result;
	}
	
	
	/**
	 *   전체 회원 검색
	 */
	@Transactional(readOnly =true)
	@Override
	public List<MemberVO> selectAllMembers(int page, int limit) {
		return memberDAO.selectAllMembers(page, limit);
	}
	
	/**
	 *  전체 회원 수 
	 */
	@Transactional(readOnly =true)
	@Override
	public int selectAllCountMember() {
		return memberDAO.selectAllCountMember();
	}
	
	/**
	 *  회원 검색 페이징 
	 * 
	 */
	@Transactional(readOnly =true)
	@Override
	public List<MemberVO> selectMemberSearching(int page, int limit, String searchKey, String searchWord) {
		return memberDAO.selectMemberSearching(page, limit, searchKey, searchWord);
	}
	
	/**
	 *  회원 검색
	 * 
	 */
	@Transactional(readOnly =true)
	@Override
	public int selectMembersCountSearching(String searchKey, String searchWord) {
		return memberDAO.selectMembersCountSearching(searchKey, searchWord);
	}
	
	/**
	 * 회원 Role(역할) 주입
	 */
	@Transactional
	@Override
	public void insertRole(Role role) {
		memberDAO.insertRole(role);
	}
	
	/**
	 * 회원 Role(역할) 삭제
	 */
	@Transactional
	@Override
	public void deleteRole(Role role) {
		memberDAO.deleteRole(role);
	}
	
	/**
	 * 회원 Role(역할) 검색
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> selectMemberRole(String username) {
		return memberDAO.selectMemberRole(username);
	}
	
	/**
	 * 회원정보 수정 여부
	 */
	@Transactional(readOnly = true)
	@Override
	public boolean isUpdateMember(String id,String field, String value) {
		
		return memberDAO.isUpdateMember(id,field, value);
	}
	

}
