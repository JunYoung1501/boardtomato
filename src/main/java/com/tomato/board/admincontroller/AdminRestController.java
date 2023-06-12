package com.tomato.board.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomato.board.memberservice.MemberService;
import com.tomato.board.membervo.MemberVO;
import com.tomato.common.vo.Role;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminRestController {

	@Autowired
	MemberService memberService;

	/**
	 *  회원 역할(Role) 변경 
	 * @param id   회원 아이디
	 * @param role1  어드민권한
	 * @param role2  사용자 권한
	 * @return msg로 출력
	 */
	@PostMapping(value="memberRoleUpdate.do", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> memberRoleUpdate(@RequestParam(value="id", required=true) String id,
												  @RequestParam("role1") String role1,
												  @RequestParam("role2") String role2)
	{
		log.info("memberRoleUpdate:");
		HttpHeaders reponseHeaders = new HttpHeaders();
		reponseHeaders.add("Content-Type", "text/plain; charset=UTF-8");
		
		log.info("id : {}, role1 : {}, role2 : {}", id, role1, role2);
		
		String msg = "";

		// 기존의 role 획득
		MemberVO member = memberService.getMember(id);
//		String roles = member == null ? "" : member.getRole();
		List<String> roles = memberService.selectMemberRole(id);
		
		//log.info("roles(기존 롤들) : {}", roles);
		
		// admin => ROLE_ADMIN, user => ROLE_USER
		role1 = role1.equals("admin") ? "ROLE_ADMIN" : "";
		role2 = role2.equals("user") ? "ROLE_USER" : "";
		
		
		  Role role = new Role(); 
		  role.setUsername(id);
		
		// 관리자 권한이 신규 할당되었고, 기존에 관리자 권한이 없으면
		if (role1.equals("ROLE_ADMIN") && roles.contains("ROLE_ADMIN") == false) { 

			// 관리자 롤 삽입
			  role.setRole(role1);
			  memberService.insertRole(role);
			 
			msg += "관리자 권한이 할당되었습니다.";
		
		// 기존에 관리자 권한이 있고, 관리자 권한이 회수되었다면(미할당) 
		} else if (role1.equals("") && roles.contains("ROLE_ADMIN") == true) {
			
			// 관리자 롤 삭제
			role.setRole("ROLE_ADMIN");
			memberService.deleteRole(role);				 
			msg += "관리자 권한이 삭제되었습니다.";
		} 
		
		// 회원 권한이 신규 할당되었고, 기존에 회원 권한이 없으면
		if (role2.equals("ROLE_USER") && roles.contains("ROLE_USER") == false) {
			
			role.setRole(role2);
			memberService.insertRole(role);
			
			msg += "회원 권한이 할당되었습니다.";

		// 기존에 회원 권한이 있고, 회원 권한이 회수되었다면(미할당)
		} else if (role2.equals("") && roles.contains("ROLE_USER") == true) {
				
			role.setRole("ROLE_USER");
			memberService.deleteRole(role);
			member.setStatus(0); // 휴면계정으로 치환 
			log.info("회원정보:" + member);
			memberService.updateMember(member); // 회원정보에 반영
			
			msg += "회원 권한이 삭제되었습니다.";

		}	
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
	} //
	
	
//	
	
}
