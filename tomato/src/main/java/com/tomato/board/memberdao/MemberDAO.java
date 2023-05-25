package com.tomato.board.memberdao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.Role;


@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession sqlSession;
	/*DB에 데이터 주입*/
	public void insert(MemberVO memberVO) {
		sqlSession.insert("com.tomato.mapper.member.insert",memberVO);
	}
	/*회원Role 주입*/
	public void insertRole(Role role) {
		sqlSession.insert("com.tomato.mapper.member.insertRole",role);
	}
	/*아이디 검색*/
	public MemberVO select(String id) {
		return sqlSession.selectOne("com.tomato.mapper.member.select",id);
	}
	/*아이디 패스워드 이메일 중복 여부 */
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
	
	/*회원정보 수정*/
	public  void update(MemberVO memberVO) {
		sqlSession.update("com.tomato.mapper.member.update",memberVO);
	}
	/*회원정보 삭제*/
	public void delete(String id) {
		sqlSession.delete("com.tomato.mapper.member.delete",id);
	}
	/*
	 * hasMember 아이디  가져와서
	 * isMember 아이디 필드 단위로 중복 확인 
	 * */
	public boolean hasMember(String id) {
		
		return	(int)sqlSession.selectOne("com.tomato.mapper.member.hasMember",id) ==1 ? true : false ;
	}
	
	public boolean isMember(String field, String value) {
		HashMap<String,String> map= new HashMap<>();
		map.put("fld", field);
		map.put("val", value);
		return (int)sqlSession.selectOne("com.tomato.mapper.member.isMember",map) ==1 ? true : false ;
	}
	
	
}
