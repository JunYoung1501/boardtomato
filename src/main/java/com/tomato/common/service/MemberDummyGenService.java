package com.tomato.common.service;

import java.util.Arrays;
import java.util.Collections;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomato.board.memberservice.MemberService;
import com.tomato.board.membervo.MemberVO;


/**
 *  회원 더미 데이터
 * 
 *
 */
@Service
public class MemberDummyGenService {
	
	private Logger log = LoggerFactory.getLogger(MemberDummyGenService.class);  
	
	@Autowired
	MemberService memberService;
	
	private String makeName() {
		
		String name = "";
		
		String first[] = {"김","이","박","최","주","임","엄","성","남궁","독고","황","황보","송","오","유","류","윤","장","정","추"};  
        String middle[] = {"","숙","갑","영","순","선","원","우","이","운","성"};  
    	String last[] = {"영","수","희","빈","민","정","순","주","연","영"}; 
    	
    	name = first[(int)Math.floor(Math.random() * first.length)];
		name += middle[(int)Math.floor(Math.random() * middle.length)];
		name += last[(int)Math.floor(Math.random() * last.length)];
		
		return name;
	}
	
	private String[] makeAddress() {
		
		Map<String, String> map = new HashMap<>();
		String[] result = new String[2];
		
		map.put("07031", "서울 동작구 관악로30길 27 (사당동, 관악푸르지오)");
		map.put("03188", "서울 종로구 종로 24-4 (서린동)");
		map.put("06761", "서울 서초구 과천대로 790 (방배동)");
		map.put("07761", "서울 강서구 가로공원로 174-5 (화곡동, 씨티하우스)");
		map.put("01370", "서울 도봉구 노해로 133 (쌍문동)");
		map.put("04023", "서울 마포구 월드컵로 31-7 (합정동, 그린랜드빌리지)");
		map.put("08825", "서울 관악구 관악산나들길 66 (신림동)");
		map.put("08577", "서울 금천구 가산로 5 (독산동, 호암빌)");
		map.put("04920", "서울 광진구 천호대로 499 (중곡동)");
		map.put("01803", "서울 노원구 공릉로 104-6 (공릉동, 우리맨션)");
		
		List<Object> list = Arrays.asList(map.keySet().toArray());
		Collections.shuffle(list);
		
		String key = list.get(0).toString();
		result[0] = key;
		result[1] = map.get(key);
		
		return result;
	}
	
	// ex) oooo-oo-oo
	public String makeBirthday() {
		
		int year;
		int month;
		int date;
		
		year = 1935 + (int)(Math.random() * 80);
		month = 1 + (int)(Math.random() * 11);
		
		date = (month == 2) ? 1 + (int)(Math.random() * 26) 
			 : 1 + (int)(Math.random() * 29);
		
		String temp = year + ""  
		            + (month < 10 ? "0" + month : month) 	
		            + (date < 10 ? "0" + date : date);
		
		log.info("temp : {}", temp);		
		
		return temp;
	}
	
	public int memberDummyGen() {
		
		log.info("더미 회원 정보 생성");
		
		MemberVO memberVO = null;
		String id = "";
		int cnt = 0;
		
		for (int i=1; i<=100; i++) {
		
			
			memberVO = new MemberVO();
			
			id = "project" + i;
			
			memberVO.setId(id);
			//memberVO.("ROLE_USER");
			memberVO.setPwd("!Abcde1234"); // 버그 패치 (이중 암호화)
			memberVO.setName(this.makeName());
			
			memberVO.setEmail("project"+i+"@naver.com");
			
			int phone2 = 1000 + (int)(Math.random()* 100); 
			memberVO.setPhone("010"+ phone2+(1000+i));
			
			String[] address = this.makeAddress();
			memberVO.setPostadd(address[0]);
			memberVO.setAddress(address[1]);
			memberVO.setDetailaddr("가1");
			
			memberVO.setBirthday(this.makeBirthday());
			memberVO.setStatus(1);
			
			
			log.info("memberVO : {}", memberVO);

			String flag = memberService.insertMember(memberVO);
			
			if (flag.equals("회원정보가입에 성공하였습니다")) {
				cnt++;
			}
			
			log.info("cnt : {} ", cnt);
		} // for
		
		return cnt;
		
	} //

}
