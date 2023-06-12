package com.tomato.board.boardservice;

import java.util.UUID;

import org.springframework.stereotype.Service;

/* 파일 암호화 서비스 */
@Service("fileNamingEncoder")
public class FileNamingEncoder implements IntFileNamingEncoder {

	/**
	 * 실제 저장 파일명 암호화 처리(UUID)<br> 
	 * 효과) 업로드 파일들의 중복 방지<br>
	 *  
	 * @param fileName 원본 파일명 ex) abcd.pdf 
	 * @return 업로드할 파일명 ex) abcd_암호화코드(UUID).pdf
	 */
	@Override
	public final String enFilenameUUID(String fileName) {
	
		// 파일명 인코딩(암호화) : UUID
		UUID uuid = UUID.randomUUID();
		String []fileNames = fileName.split("\\.");
		String pureFileName = "";
		
		// 확장자를 제외한 나머지 이름들 모두 연결(병합)
		for (int i=0; i<fileNames.length-1; i++) {
			pureFileName += fileNames[i];
			// 마지막에 토큰은 "." 미부착 !
			if (i<fileNames.length-2)
				pureFileName += ".";
		}
		
		int len = fileName.split("\\.").length;
		String extName = fileNames[len-1];
		
		return uuid.toString() + "." + extName;
	} //
	
}