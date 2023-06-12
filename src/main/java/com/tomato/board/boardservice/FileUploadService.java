/**
 * 
 */
package com.tomato.board.boardservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tomato.board.boardvo.FileVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadService implements IntFileUploadService {
	
	
	private static Logger log = LoggerFactory.getLogger(IntFileUploadService.class);
	

	/*
	 * 참고) Resource Interface 구현체들
	 * 
	 * 1. UrlResource : URL을 기준으로 리소스(자원) 로딩. http/https/ftp/file/jar 프로토콜 지원.
     *
     * 2. ClassPathResource : classpath: 접두사 지원, classpath 기준으로 자원 로딩.
	 *
	 * 3. FileSystemResource : 파일 시스템을 기준으로 자원 로딩.
	 *
	 * 4. ServletContextResource : 웹 어플리케이션 컨텍스트 루트(Context Path)에서 상대 경로로 자원 검색.
	 * 
	 */
	@Value("${fileupload.path}")
	private String UPLOAD_PATH;
	
	
	@Autowired
	private IntFileNamingEncoder fileNamingEncoder;
	
	/**
	 *  업로드 파일 저장 
	 */
	@Override
	public FileVO storeUploadFile(int boardNum, MultipartFile file) {
		
		log.info("첨부 파일 저장 서비스");
		
		FileOutputStream fos = null;
		String fileName = "";
		FileVO fileVO = new FileVO();
		String encodedFileName = "";
		String result = "파일 저장에 성공하였습니다. ";

		//////////////////////////////////////////////////////////////////////////////////////////
		//
		// 지역 저장소(상대 경로)에 저장
		// 
		// 주의) 프로젝트 포함 경로에 프로젝트에서 사용할 이미지 등을 제외한 전용 파일 저장소로 사용하고자 할 경우
		// 프로젝트 백업(backup)시 용량이 과다하게 될 수 있으므로 업로드 파일 전용 저장소용으로는 프로젝트 
		// 폴더 외의 다른 경로를 권장. 
		//
		//////////////////////////////////////////////////////////////////////////////////////////
		// 
		// 1. Context Path 바로 하위 경로에 파일 저장소를 둘 경우
		// 
		// ex) ...(전략)...  /SpringBootBoard/upload/
		
		// spring legacy project
	 	// String savePath = servletContext.getRealPath("/resources/upload/");  
		
		// 주의) spring boot와 spring legacy project의 경로 차이에 의한 경로 작성
		// spring boot : ServletContext에서 기존 legacy와는 다르게 webapp 경로 배제됨.
		// 
		// ex) spring boot : ...(전략)... \\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\SpringBootBoard\\WEB-INF\\classes\\static\\upload
		// ex) spring legacy : ...(전략)... \\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\SpringBoard\\resources\\upload\\
		
		// String savePath2 = servletContext.getRealPath("/resources/upload/");
		// 주의) ...(전략)... tmp0\\wtpwebapps\\  ...(후략)... 중간 경로에서 webapp 경로가 포함되어 있으므로 치환시 유의 !  
		// String savePath = servletContext.getRealPath("/resources/upload/").replace("/webapp/", "/");
		
		////////////////////////////////////////////////////////////////////////////////
		// 
		// 2. static 경로 하위에 둘 경우 
		// 
		// String savePath = servletContext.getRealPath("/WEB-INF/classes/static/upload");
		
		// 절대 경로 활용
		String savePath = UPLOAD_PATH;
		
		log.info("실제 저장 경로 : "+savePath);
		
		// 업로드 파일 처리
		if (file.isEmpty() || file == null) {
			
			 result = "첨부 파일이 없습니다. ";
			 log.debug(result);
		
		} else { // 파일 유효성 점검
			
			 // 저장 폴더 존재 점검
			 Path path = Paths.get(savePath);
			 
			 if (Files.exists(path)) {
				 result = "파일 업로드 저장소(폴더)가 존재합니다.";
				 log.info(result);
				 
			 } else {
				 result = "파일 업로드 저장소(폴더)가 존재하지 않습니다.";
				 log.error(result);
				 // 폴더 생성
				 try {
					 Files.createDirectory(path);	
				 } catch (Exception e) {
					 result = "파일 업로드 저장소(폴더)가 생성되지 않았습니다.";
					 log.error(result);
				 }
			 } //
		   
		     fileName = file.getOriginalFilename();
		     
		     log.info("###### 신규 글번호 : {}", boardNum);
		     
		     try {
		    	 	// 업로드 파일 형식 변환(시작) : 추가
		    	 	encodedFileName = fileNamingEncoder.enFilenameUUID(fileName);
					log.info("실제 업로드 파일명 : {}", fileName);
					// 업로드 파일 형식 변환(끝) : 추가
		    	 
		    	 	byte[] bytes = file.getBytes();
		    	 	
		    	 	log.info("### savePath : " + savePath);

		            File outFileName = new File(savePath + "/" + encodedFileName);
		            fos = new FileOutputStream(outFileName);
		             
		            fos.write(bytes);
		         
		     } catch (IOException e) {
		    	 
		    	 result = "파일 처리중 오류가 발생하였습니다. ";
		         log.info(result);
		         e.printStackTrace();
		         		        		 
		     } finally { // 자원 반납
		           
				 try {    
				       if (fos!=null) fos.close();
				       
				 } catch (IOException e) {
					 result = "파일 처리중 오류가 발생하였습니다. ";
					 log.info("FileUploadService storeUploadFile IOE : " + result);
				     e.printStackTrace();
				 }
				 
		     } // try
		     
		} // 업로드 파일 처리
		
		fileVO.setMsg(result);
		fileVO.setFileName(fileName);
		fileVO.setEncodeFileName(encodedFileName);
		
		return fileVO;
	} //

	/**
	 * 첨부된  파일 삭제
	 * */
	@Override
	public String deleteUploadFile(String uploadSubPath, String encodingFilename) {
		
		log.info("FileUploadService.deleteUploadFile(오버로딩)");
		String msg = ""; // 메시지
		
		// 지역 저장소(상대 경로)에 저장
	 	String deletePath = UPLOAD_PATH + uploadSubPath;
	 	
	 	log.info("첨부 이미지 저장 경로 : " + deletePath + encodingFilename);
		
	 	try {
			
	 		// 교정
	 		if (Files.deleteIfExists(Paths.get(deletePath + encodingFilename)) == true) {
	 			msg = "파일 삭제에 성공하였습니다.";
	 		} else {
	 			msg = "파일 삭제에 실패하였습니다.";
	 		}
			
		} catch (IOException e) {
			log.error("업로드 파일 삭제 에러 : " + e);
			msg = "파일 삭제에 실패하였습니다.";
			e.printStackTrace();
		}
		
		return msg;
	}

}