package com.tomato.board.boardcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.tomato.board.boardservice.BoardService;
import com.tomato.board.boardservice.FileNamingEncoder;
import com.tomato.board.boardservice.FileUploadService;
import com.tomato.board.boardvo.BoardDTO;
import com.tomato.board.boardvo.BoardVO;
import com.tomato.board.boardvo.FileVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 게시글 수정 컨트롤러
 * @author 문준영
 *
 */
@Controller
@SessionAttributes("boardDTO")
@Slf4j
@RequestMapping("/board")
public class BoardUpdateController {

	@Autowired
	FileNamingEncoder encoder;
	
	@Autowired 
	FileUploadService fileUploadService;
	
	@Autowired
	BoardService boardService;
	
	/**
	 *  게시글 수정 
	 * @param boardNum
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/boardUpdate.do")
	public String boardUpdate(@RequestParam("boardNum") int boardNum,Model model,HttpSession session) {
		
		log.info("게시글 수정(폼)");
		
		//기존 게시글 조회
		BoardVO board =boardService.getBoard(boardNum);
		log.info("board:"+board);
			
		/*
		 * if (session.getAttribute("defaultBoardDTO") == null) {
		 * session.setAttribute("defaultBoardDTO", new BoardDTO(board)); }
		 */
		if (session.getAttribute("defaultBoardVO") == null) {
			session.setAttribute("defaultBoardVO", board);
		}
		
		model.addAttribute("boardDTO", new BoardDTO(board));
		model.addAttribute("boardOriginalFileName",board.getBoardOriginalFile()); //업로드 파일명
		model.addAttribute("boardFileName",board.getBoardFile()); //실제 업로드 파일명
		
		return "board/boardUpdate";
	}
	
	/**
	 *  게시글 수정 전송 
	 * @param boardDTO
	 * @param boardOriginalFile
	 * @param model
	 * @param request
	 * @param sessionStatus
	 * @param session
	 * @return
	 */
	@PostMapping("/boardUpdateProc.do")
	public String boardUpdateProc(@ModelAttribute("boardDTO") BoardDTO boardDTO , 					  
								  @RequestParam("boardOriginalFile") MultipartFile boardOriginalFile,
								  Model model,
								  HttpServletRequest request, 
								  SessionStatus sessionStatus,
								  HttpSession  session) {
		log.info("게시글 수정 처리:");
		log.info("boardDTO:"+boardDTO);
		
		String msg=""; // 메세지
		String movePath=request.getContextPath()+"/board/boardRead.do?boardNum="+boardDTO.getBoardNum(); // 에러 메세지 인쇄 후 이동 경로

		log.info("첨부 파일 : {}", boardOriginalFile);
		log.info("첨부 파일명 : {}", boardOriginalFile != null ? boardOriginalFile.getOriginalFilename() : "");
		
		//기존 게시글 확보
		BoardVO defaultBoardVO = (BoardVO)session.getAttribute("defaultBoardVO");
		
		log.info("기존 정보 : " + defaultBoardVO);
		log.info("수정 정보 : " + boardDTO);
		log.info("첨부 파일 존재 여부 : {}", boardOriginalFile.getOriginalFilename());
		
		BoardVO updateBoardVO = new BoardVO(boardDTO);
		
		log.info("boardOriginalFile.getOriginalFilename().trim().equals():"
				+boardOriginalFile.getOriginalFilename().trim().equals(""));
		
		// 첨부 파일이 있다면...
		if (boardOriginalFile.getOriginalFilename().trim().equals("") == false)
		{
			log.info("게시글 작성 처리(첨부 파일) : {}", boardOriginalFile.getOriginalFilename().trim());
			
			// 기존 파일 및 인코딩 파일
			updateBoardVO.setBoardOriginalFile(boardOriginalFile.getOriginalFilename());
			log.info("업로드 파일:"+boardOriginalFile);
			
			// 파일 업로드
			FileVO fileVO = fileUploadService.storeUploadFile(boardDTO.getBoardNum(), 
													boardOriginalFile);
			updateBoardVO.setBoardFile(fileVO.getEncodeFileName());
		    msg += fileVO.getMsg();
			// 기존 파일 삭제 처리 (정책에 따라 차이) 
		    //"" 기존 업로드 파일 경로(fileupload.path=D:/filestate/)의 하위 저장 경로("") 표시 
		    //기존 경로 바로 아래 경로 저장
		    //기존의 파일이 없으면 삭제를 안하고 넘어가고 삭제할 파일이 있으면 지운다
		    log.info("defaultBoardDTO.getBoardFile():"+defaultBoardVO.getBoardFile());
		    
		    if( defaultBoardVO.getBoardFile() != null) {
			    log.info("defaultBoardVO.getBoardFile():"+ 
			    defaultBoardVO.getBoardFile());
				msg += fileUploadService.deleteUploadFile("",defaultBoardVO.getBoardFile());
		    }
		    
			log.info("msg : {}", msg);
			
		} else { 
			
			// 첨부 파일이 없다면...
			// 기존에 첨부 파일이 있다면 기존 파일을 지우지 말고 그대로 반영
			// 별도의 파일 조작(업로드, 삭제) 없음
			// 기존 파일 및 인코딩 파일
			
			String tempBoardOriginalFile = 	
					defaultBoardVO.getBoardOriginalFile() != null ? 
					defaultBoardVO.getBoardOriginalFile() : "" ;
			String tempBoardFile = 
					defaultBoardVO.getBoardFile() != null ?
					defaultBoardVO.getBoardFile() : "";
			
			updateBoardVO.setBoardOriginalFile(tempBoardOriginalFile);
			updateBoardVO.setBoardFile(tempBoardFile);
			
		}		
	
		
		log.info("기존 데이터VO: {}",defaultBoardVO);
		log.info("신규 수정 데이터VO : {}", updateBoardVO);
		log.info("defaultBoardVO.equals(updateBoardVO):"+defaultBoardVO.equals(updateBoardVO));
		
		// 기존 데이터와 비교시 데이터 변경되었을때
		if(defaultBoardVO.equals(updateBoardVO) == false) {
			
			log.info("기존 데이터: {}",defaultBoardVO.getBoardContent());
			log.info("신규 수정 데이터 : {}", updateBoardVO.getBoardContent());
			
			//게시글 내용 삽입 이미지 처리 (삭제)
			
			// 기존 데이터의 삽입 이미지 목록
			//ex) <img src="/tomato/board/image.do/12" style="width: 662.453px;">
			List<Integer> defaultImgList 
				= boardService.getImageList(defaultBoardVO.getBoardContent(), request.getContextPath()+"/board/image.do/");
			
			// 신규 수정 데이터의 삽입 이미지 목록
			List<Integer> updateImgList
				= boardService.getImageList(updateBoardVO.getBoardContent(), request.getContextPath()+"/board/image.do/");
			
			// 삭제할 삽입 이미지 파일 테이블 아이디 목록
			List<Integer> deleteExpectedImgList = new ArrayList<>();
			
			log.info("----------------------------------");
			
			for (int s : defaultImgList) {
				log.info("--- 기존 업로드 이미지 아이디: " + s);
			} //
							
			for (int s : updateImgList) {
				log.info("--- 신규 업로드 이미지 아이디 : " + s);
			} //
			
			log.info("----------------------------------");
			
			log.info("updateImgList-1 : " + (updateImgList.size()));
			
			// 기존에 이미지가 되어 있지만
			// 신규에는 이미지가 없을 때는 기존 이미지 모두 삭제
		
			if (updateImgList.size() == 0) {
				
				log.info("기존 모든 이미지 삭제");
				deleteExpectedImgList.addAll(defaultImgList);
				
			} else { // 신규에 이미지 포함시 선택 삭제 
			
				log.info("기존 모든 이미지 선별적 삭제");
				
				for (int s : defaultImgList) {
					
					if (updateImgList.contains(s) == false) { // 
						log.info("실제 삭제할 기존 이미지 파일 : " + s);
						deleteExpectedImgList.add(s);
					} //
					
				} // for
				
			} // if
			
			log.info("--------------- 삭제할 이미지 -------------------");

			// 대상 삽입 이미지 파일 삭제 : 삭제할 이미지 있으면 삭제
			if (deleteExpectedImgList.size() > 0) {
				
				for (int s : deleteExpectedImgList) {
					log.info("boardService.deleteContentImg:"+boardService.deleteContentImg(s));
				}
			}
			
			log.info("updateBoardVO:"+updateBoardVO);
			
			// 게시글 수정
			if (boardService.updateBoard(updateBoardVO) ==false) {
				msg += "글수정에 실패하였습니다.";
				movePath = request.getContextPath()+"/board/boardUpdate.do?boardNum="+updateBoardVO.getBoardNum();
			}
			else {
				msg += "글수정에 성공하였습니다.";
				// 작성후 게시글 보기로 이동
				movePath = request.getContextPath()+"/board/boardRead.do?boardNum="+updateBoardVO.getBoardNum();
				
				session.removeAttribute("defaultBoardVO");//기존 세션 게시글 데이터 삭제
			}
			
			
		} // // 기존 데이터와 비교시 데이터 변경되었을때
	
		else {
			//변경사항이 없을때
			msg = "변경된 내용이 없습니다";
		}

		model.addAttribute("error_msg",msg);
		model.addAttribute("move_page",movePath);
		
		return "/error/error";
	}
	
}
