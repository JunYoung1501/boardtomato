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

/**
 * 회원 가입 컨트롤러
 * 중복 점검
 * @author 문준영
 *
 */
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberCheckController {

	@Autowired
	IntMemberSerivce iMemberService;
	
	/** 
	 * 아이디 중복 점검 
	 * @param ID
	 * @param successMsg
	 * @param failMsg
	 * @return
	 */
	@GetMapping(value="/idcheck.do",produces="text/plain;charset=utf-8")
	public ResponseEntity<String> idcheck(@RequestParam("id") String ID ,
										  @Value("${error.memberVO.idcheck.success}") String successMsg,
										  @Value("${error.memberVO.idcheck.fail}") String failMsg){
		log.info("idcheck:"+ID);
		log.info("아이디결과:"+iMemberService.getMember(ID));
		String msg= iMemberService.getMember(ID) == null ? successMsg :failMsg;
		return ResponseEntity.ok(msg);
	}
	/** 
	 * 이메일 중복점검
	 * @param Email
	 * @param successMsg
	 * @param failMsg
	 * @return
	 */
	@GetMapping(value="/emailcheck.do",produces="text/plain;charset=utf-8")
	public ResponseEntity<String> emauilcheck(@RequestParam("email") String Email,
											  @Value("${error.memberVO.emailcheck.success}") String successMsg,
					                          @Value("${error.memberVO.email.check.fail}") String failMsg){
		log.info("emailcheck");
		log.info("아이디결과:"+iMemberService.getMember(Email));
		String msg= iMemberService.isMember("email", Email) == false ? successMsg : failMsg;
		return ResponseEntity.ok(msg);
	}
	/**
	 *  휴대폰 중복 점검
	 * @param Phone
	 * @param phoneOk
	 * @param phoneNo
	 * @return
	 */
	@GetMapping(value="/phonecheck.do",produces="test/plan;charset=utf-8")
	public ResponseEntity<String> phoneCheck(@RequestParam("phone") String Phone,
											 @Value("${error.memberVO.phonecheck.success}")String phoneOk,
											 @Value("${error.memberVO.phonecheck.fail}")String phoneNo){
		log.info("phoneCheck:");
		log.info("결과:"+iMemberService.isMember("phone", Phone));
		String msg = iMemberService.isMember("phone", Phone) == false ? phoneOk: phoneNo ;
		return ResponseEntity.ok(msg);
	}
	
}
