package com.tomato.board.boardcontroller;




import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.board.boardservice.BoardService;
import com.tomato.board.boardservice.IntFileUploadService;
import com.tomato.board.boardvo.BoardDTO;
import com.tomato.board.boardvo.BoardVO;
import com.tomato.board.boardvo.FileVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	IntFileUploadService fileUploadService;
	
	/**게시글 쓰기
	 * 
	 * @param textMulti 게시글 안에 내용 ex) 글 , 이미지 , 웹페이지 , 유튜브 링크 etc ..
	 * @param model
	 * @return
	 */
	@GetMapping("/boardWrite.do")
	public String boardWriteText(@RequestParam(value="textMulti",defaultValue="text")
	String textMulti, Model model){
		
		log.info("게시글 작성:");
		
		String returnPath = textMulti.equals("text") ? "/board/insertboard" 
													 : "/board/boardmulti";
		BoardDTO boardDTO= new BoardDTO();
		boardDTO.setBoardNum(2);
		model.addAttribute("boardDTO",boardDTO);
		
		return returnPath;
	}//boardWriterText

	/**게시글 처리
	 * 
	 * @param boardId 회원 아이디
	 * @param boardDTO  
	 * @param file  첨부 파일 (암호화)
	 * @param requset 
	 * @param model
	 * @return
	 */
	@PostMapping("/boardWriterProc.do")
	public String boardWriterProc(@RequestParam("boardId") String boardId,
			@ModelAttribute("boardDTO") BoardDTO boardDTO ,
			@RequestParam(value="boardOriginalFile", required=false) MultipartFile file ,
			HttpServletRequest requset,Model model) 
	{	
		log.info("test:--{}",boardId);
		log.info("게시글 처리 :{}" , boardDTO);
		
		boolean flag = false;
		String movePath="";
		String msg = "";

		int boardNum =  boardService.getBoardNumByLastSeq();
		
		log.info("file--:{}",file.getOriginalFilename());
		log.info("fileEmpty--{}",file.isEmpty());

		// 첨부 파일 처리
	    FileVO fileVO = fileUploadService.storeUploadFile(boardNum, file);
		
	    // 저장 VO 생성
		BoardVO boardVO = new BoardVO(boardDTO);
		boardVO.setBoardOriginalFile(!file.isEmpty() && file != null ? fileVO.getFileName() : "");
		boardVO.setBoardFile(!file.isEmpty() && file != null ? fileVO.getEncodeFileName() : "");
	    
		boardVO.setBoardNum(boardNum);
		boardVO.setBoardReRef(boardNum);
		log.info("boardVO:"+boardVO);
		flag = boardService.insertBoard(boardVO);
		
		if (flag == false) {
			msg += "글쓰기에 실패하였습니다.";
			movePath=requset.getContextPath()+"/board/boardWrite.do";
		} else {
			msg += "글쓰기에 성공하였습니다.";
			// 작성후 게시글 보기로 이동
			movePath=requset.getContextPath()+"/board/boardRead.do?boardNum="+boardVO.getBoardNum();
		}
		
		model.addAttribute("error_msg",msg);
		model.addAttribute("move_page",movePath);
		
		return "/error/error";
	}
	
	/**
	 *  게시판 리스트 보이기(읽기)
	 * @param boardNum 글번호(시퀀스)
	 * @param page 현재 페이지
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/boardRead.do")
	public String boardRead(@RequestParam("boardNum") int boardNum,
							@RequestParam(value="page",defaultValue = "1") int page, Model model) throws JsonProcessingException {
		
		log.info("게시글 조회 Id:"+boardNum);
		
		String msg="";
		String movePath="";
		
		if(boardService.isBoard(boardNum)== false )
		{	
			msg="해당 게시글이 없습니다";
			movePath="/tomato";//임시조치
			log.info("--msg:"+msg);
		}
		else
		{	
			log.info("break1:");
			//게시글 조회수 업데이트 
			boardService.updateReadcount(boardNum);
			log.info("break2:");
			BoardVO boardVO= boardService.getBoard(boardNum);
			log.info("break3:");
			log.info("boardVO:--"+boardVO);
		
			//댓글들 조회
			List<BoardVO>  boardList =  boardService.boardReplySelect(boardNum);
			for (BoardVO boardVO2 : boardList) {
				log.info("boardVO2:"+ boardVO2);
			}
			
			model.addAttribute("page",page);
			model.addAttribute("boardVO",boardVO );
			model.addAttribute("boardList", new ObjectMapper().writeValueAsString(boardList)); //JSON 변환
			
			return "/board/viewboard";
		}
		
		model.addAttribute("error_msg",msg);
		model.addAttribute("move_page",movePath);
		
		return "/error/error";
		
	}

	

}
