package com.tomato.board.memberdao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.Role;


@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	/** DB에 데이터 주입
	 * 
	 * @param memberVO
	 */
	public void insert(MemberVO memberVO) {
		sqlSession.insert("com.tomato.mapper.member.insert",memberVO);
	}
	
	/** 회원Role 주입
	 * 
	 * @param role
	 */
	public void insertRole(Role role) {
		sqlSession.insert("com.tomato.mapper.member.insertMemberRole",role);
	}
	
	/** 회원Role 삭제
	 * 
	 * @param role
	 */
	public void deleteRole(Role role) {
		sqlSession.delete("com.tomato.mapper.member.deleteMemberRole",role);
	}
	
	/** 아이디 패스워드 이메일 중복 여부 
	 * 
	 * @param memberVO
	 * @return
	 */
	public MemberVO select(MemberVO memberVO) {
		
	
		String subSql = "id = '"+memberVO.getId()+"' "; // 1record
		
		if(memberVO.getId()!=null && memberVO.getEmail()!=null) { //1record
		
			subSql += "and email = '"+memberVO.getEmail()+"' ";		
		} 
		else if (memberVO.getId()!=null && memberVO.getPwd()!=null) { //1record
			
			subSql += "and pwd = '"+memberVO.getPwd()+"' ";
		} 
		else if (memberVO.getPwd()!=null && memberVO.getEmail()!=null) { // 1record
			
			subSql =  "pwd = '"+memberVO.getPwd()+"' "
					+ "and email = '"+memberVO.getEmail()+"'";
		} 
		
		return sqlSession.selectOne("com.tomato.mapper.member.selectByFlds",subSql);
	}
	
	/** 회원정보 수정
	 * 
	 * @param memberVO
	 */
	public  void update(MemberVO memberVO) {
		sqlSession.update("com.tomato.mapper.member.update",memberVO);
	}
	/** 회원정보 삭제
	 * 
	 * @param id
	 */
	public void delete(String id) {
		sqlSession.delete("com.tomato.mapper.member.delete",id);
	}
	
	/**
	 * 회원정보 존재여부 
	 *  
	 * @param id
	 * @return
	 */
	public boolean hasMember(String id) {
		return	(int)sqlSession.selectOne("com.tomato.mapper.member.hasMember",id) ==1 ? true : false ;
	}
	
	/** 
	 * 회원 검색 
	 * 
	 * @param id
	 * @return
	 */
	public MemberVO selectMember (String id) {
		return sqlSession.selectOne("com.tomato.mapper.member.selectMember",id);
	}
	
	/** 
	 * 회원 존재 유무 ex) 가입시
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean isMember(String field, String value) {
		HashMap<String,String> map= new HashMap<>();
		map.put("fld", field);
		map.put("val", value);
		return (int)sqlSession.selectOne("com.tomato.mapper.member.isMember",map) ==1 ? true : false ;
	}
	
	/** 
	 * 회원 존재 유무 ex) 수정시
	 * 
	 * @param id
	 * @param field
	 * @param value
	 * @return  ex) 1 중복됨 0 사용가능
	 */
	public boolean isUpdateMember(String id,String field, String value) {
		HashMap<String,String> map= new HashMap<>();
		map.put("id", id);
		map.put("fld", field);
		map.put("val", value);
		return (int)sqlSession.selectOne("com.tomato.mapper.member.isUpdateMember",map) ==1 ? true : false ;
	}
	
	
	/** 전체 회원수
	 * 
	 * @return
	 */
	public int 	selectAllCountMember() {
		return (int)sqlSession.selectOne("com.tomato.mapper.member.selectAllCountMember");
	}
	
	/** 롤 조회
	 * 
	 * @param username
	 * @return
	 */
	public List<String> selectMemberRole(String username){
		return sqlSession.selectList("com.tomato.mapper.member.selectMemberRole",username);
	}
	
	/** 회원 정보 페이징 
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<MemberVO> selectAllMembers(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		return sqlSession.selectList("com.tomato.mapper.member.selectMembersByPaging",map);
	}
	/** 회원 검색  페이징 
	 * 
	 * @param page
	 * @param limit
	 * @param searchKey
	 * @param searchWord
	 * @return
	 */
	public List<MemberVO> selectMemberSearching(int page, int limit, String searchKey , String searchWord){
		HashMap<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		return sqlSession.selectList("com.tomato.mapper.member.selectMemberSearching",map);
	}
	
	/** 회원 검색 
	 * 
	 * @param searchKey
	 * @param searchWord
	 * @return
	 */
	public int  selectMembersCountSearching(String searchKey , String searchWord){
		HashMap<String, Object> map = new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		return sqlSession.selectOne("com.tomato.mapper.member.selectMembersCountSearching" , map);
	}
	
	
}
