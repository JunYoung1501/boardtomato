package com.tomato.board.memberservice;

import java.util.List;
import java.util.Map;

import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.Role;

public interface IntMemberSerivce {
	
	/**
	 * 데이터 주입
	 * 
	 * @param member
	 * @return
	 */
	String insertMember(MemberVO member);
	
	/**
	 *  아이디 조회
	 * @param id
	 * @return
	 */
	MemberVO getMember(String id);
	
	/**
	 *  회원 정보 수정
	 * @param member
	 * @return
	 */
	boolean updateMember(MemberVO member);
	/**
	 *  계정삭제
	 * @param id
	 * @return
	 */
	String deleteMember(String id);
	
	/**
	 * 회원 중복(id) 유무
	 * @param id
	 * @return
	 */
	boolean hasMember(String id);
	
	/**
	 * 회원 조회 
	 * @param member
	 * @return
	 */
	MemberVO getOneMember(MemberVO member);
	
	/**
	 * 회원 확인
	 * @param field
	 * @param value
	 * @return
	 */
	boolean isMember(String field,String value);
	
	/**
	 * 회원 휴대전화 번호 중복 여부
	 * @param id
	 * @param phone
	 * @return
	 */
	boolean isPhone (String id,String phone);
	
	/**
	 * 회원 정보수정시 중복점검 ex) 이메일, 휴대폰 
	 * @param id
	 * @param field
	 * @param value
	 * @return
	 */
	boolean isUpdateMember(String id , String field, String value);
	
	/**
	 * 회원 Role(역할) 조회
	 * @param username
	 * @return
	 */
	public List<String> selectMemberRole (String username);
	
	/**
	 * 전체 회원수 검색 
	 * @return
	 */
	public int selectAllCountMember();
	
	/**
	 * 전체 회원조회 페이징  
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<MemberVO>  selectAllMembers(int page , int limit );
	
	/**
	 * 동등/유사 검색 구분 선택 
	 * @param searchKey
	 * @return
	 */
	public String isLikeSerachWord(String searchKey);
	
	/**
	 *  회원 검색창 검색 
	 * @param page
	 * @param limit
	 * @param searchKey
	 * @param searchWord
	 * @return
	 */
	public List<MemberVO> selectMemberSearching(int page, int limit, String searchKey , String searchWord);
	
	/**
	 *  회원 검색 키워드 
	 * @param searchKey
	 * @param searchWord
	 * @return
	 */
	public int selectMembersCountSearching(String searchKey, String searchWord );
	
	/**
	 *  Role(역할) 주입
	 * @param role
	 */
	public void insertRole(Role role);
	
	/**
	 *  Role(역할) 삭제
	 * @param role
	 */
	public void deleteRole(Role role);
}
