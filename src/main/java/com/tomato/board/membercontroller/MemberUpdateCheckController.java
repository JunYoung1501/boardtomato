package com.tomato.board.membercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomato.board.memberservice.IntMemberSerivce;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberUpdateCheckController {

	@Autowired
	IntMemberSerivce iMemberService;
	
	/** 이메일 중복점검
	 * 
	 * @param id
	 * @param email
	 * @param successMsg
	 * @param failMsg
	 * @return
	 */
	@GetMapping(value="/emailUpdateCheck.do",produces="text/plain;charset=utf-8")
	public ResponseEntity<String> emailcheck(@RequestParam("id") String id,
											  @RequestParam("email") String email,
											  @Value("${error.memberVO.emailcheck.success}") String successMsg,
					                          @Value("${error.memberVO.email.check.fail}") String failMsg){
		log.info("emailUpdatecheck");
		String msg= iMemberService.isUpdateMember(id,"email", email) == false ? successMsg : failMsg;
		return ResponseEntity.ok(msg);
	}
	
	/** 
	 * 휴대폰 중복 점검
	 * @param id
	 * @param Phone
	 * @param phoneOk
	 * @param phoneNo
	 * @return
	 */
	@GetMapping(value="/phoneUpdateCheck.do",produces="test/plan;charset=utf-8")
	public ResponseEntity<String> phoneCheck(@RequestParam("id") String id,
											 @RequestParam("phone") String Phone,
											 @Value("${error.memberVO.phonecheck.success}")String phoneOk,
											 @Value("${error.memberVO.phonecheck.fail}")String phoneNo){
		log.info("phoneUpdateCheck:");
		String msg = iMemberService.isUpdateMember(id,"phone", Phone) == false ? phoneOk: phoneNo ;
		return ResponseEntity.ok(msg);
	}
	
}
