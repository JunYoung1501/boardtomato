package com.tomato.board.boardcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomato.board.boardservice.BoardService;
import com.tomato.board.boardvo.BoardVO;
import com.tomato.common.vo.CustomUser;

/**
 * 게시판 댓글 컨트롤러
 * 댓글 작성 /조회 /삭제 
 * @author 문준영
 *
 */
@RestController
@RequestMapping("/board")
public class BoardReplyController {

	private static Logger log = LoggerFactory.getLogger(BoardReplyController.class);	
	
	@Autowired
	BoardService boardService;
	
	/**
	 * 댓글 작성 
	 * @param boardNum
	 * @param boardId
	 * @param boardContent
	 * @return
	 */
	@PostMapping("/boardReplyWriterProc.do")
	public ResponseEntity<List<BoardVO>> boardReplyWriterProc(@RequestParam("boardNum") int boardNum,
									   @RequestParam("boardId") String boardId,
									   @RequestParam("boardContent") String boardContent) {
		
		log.info("댓글 작성 처리");
		
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardNum(boardService.getBoardNumByLastSeq()); // 게시글 아이디 시퀀스
		boardVO.setBoardId(boardId);// 계정 아이디
		boardVO.setBoardTitle(" "); // 게시판 제목 생략
		boardVO.setBoardReRef(boardNum);//원글 번호
		boardVO.setBoardReLev(1);// 레벨1 만 적용
		boardVO.setBoardContent(boardContent);
		boardVO.setBoardOriginalFile(""); // 댓글의 첨부 생략
		boardVO.setBoardFile("");// 댓글의 첨부 생략
		
		log.info("BoardVO: "+ boardVO);
		
		if (boardService.insertBoard(boardVO) == true) {
			
			// 원글의 기존 댓글을 모두 가져와야함
			List<BoardVO>  boardList =  boardService.boardReplySelect(boardNum);
			
			
			for (BoardVO boardVO2 : boardList) {
				log.info("boardVO2"+ boardVO2);
			}
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
			
			return new ResponseEntity<List<BoardVO>>(boardList, responseHeaders, HttpStatus.OK); // 
		} //
		
		return null;
	} ///
	
	/**
	 * 댓글 조회
	 * @param boardNum
	 * @return
	 */
	@PostMapping(value="boardReplyList.do", produces="application/json; charset=UTF-8")
	public ResponseEntity<List<BoardVO>> boardReplyList(@RequestParam("boardNum") int boardNum) {
		
		log.info("댓글 조회");
		
		List<BoardVO> boardList = boardService.boardReplySelect(boardNum);
			
		return new ResponseEntity<>(boardList, HttpStatus.OK);
	} //
	
	/**
	 * 댓글 수정 
	 * @param boardNum
	 * @param boardReRef
	 * @param boardContent
	 * @param boardId
	 * @param model
	 * @return
	 */
	@PostMapping(value="boardReplyUpdate.do",produces="application/json; charset=UTF-8")
	public ResponseEntity<Map<String, Object>> boardReplyUpdateProc(@RequestParam("boardNum") int boardNum,
												  @RequestParam("boardReRef") int boardReRef,
												  @RequestParam("boardContent") String boardContent,
												  @RequestParam("boardId") String boardId,
												  Model model){
		
		log.info("댓글 수정 처리:");
		boolean updateSuccess = false;
		String msg ="";
		Map<String, Object> map = new HashMap<>();
		
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardNum(boardNum);
		boardVO.setBoardContent(boardContent);
		boardVO.setBoardTitle("댓글:");
		boardVO.setBoardReRef(boardReRef);
		boardVO.setBoardId(boardId);
		
		log.info("boardVO:"+boardVO);
		
		// Spring Security principal(인증) 정보 조회
		Object principal = SecurityContextHolder.getContext()
										.getAuthentication()
										.getPrincipal();
		// 로그인한 사용자
		String loginUser = ((CustomUser)principal).getUsername();
		
		// 실제 댓글 작성자
		String replyWriter = boardService.getBoard(boardNum).
				getBoardId();
		
		log.info("로그인 사용자 : " + loginUser);
		log.info("댓글 작성자 : " + replyWriter);
		
		if (loginUser.equals(replyWriter) == true) {
			
			updateSuccess = boardService.updateReplyBoard(boardVO);
			
			log.info("댓글 수정 성공 여부 : " + updateSuccess);
			
			if (updateSuccess == false) { // 댓글 삭제 실패
	
				msg = "댓글 수정에 실패하였습니다.";
				
			} else {
				
				msg = "댓글 수정에 성공하였습니다.";
			}
		
		} else {
			
			msg = "댓글 작성자만 수정이 가능합니다.";
		} //
		
		log.info("msg : " + msg);
		
		// 원글의 기존 댓글을 모두 가져와야함
		List<BoardVO>  boardList = boardService.boardReplySelect(boardReRef);
		map.put("msg", msg);
		map.put("boardList", boardList);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}//
	
	/**
	 * 댓글 삭제
	 * @param boardNum
	 * @param boardReRef
	 * @return
	 */
	@GetMapping(value="boardReplyDelete.do" , produces="application/json; charset=UTF-8")
	public ResponseEntity<Map<String, Object>> boardReplyDeleteProc(@RequestParam("boardNum") int boardNum,
															@RequestParam("boardReRef") int boardReRef)
	{
		log.info("댓글 삭제 처리");
		
		String msg = "";
		Map<String, Object> map = new HashMap<>();
		
		boolean deleteSuccess = false;// 댓글 삭제 성공 여부
		
		// Spring Security principal(인증) 정보 조회
		Object principal = SecurityContextHolder.getContext()
										.getAuthentication()
										.getPrincipal();
		// 로그인한 사용자
		String loginUser = ((CustomUser)principal).getUsername();
		
		// 실제 댓글 작성자
		String replyWriter = boardService.getBoard(boardNum).
				getBoardId();
		
		log.info("로그인 사용자 : " + loginUser);
		log.info("댓글 작성자 : " + replyWriter);
		
		
		if (loginUser.equals(replyWriter) == true) {
			
			deleteSuccess = boardService.deleteBoard(boardNum);
			
			log.info("댓글 삭제 성공 여부 : " + deleteSuccess);
			
			if (deleteSuccess == false) { // 댓글 삭제 실패
	
				msg = "댓글 삭제에 실패하였습니다.";
				
			} else {
				
				msg = "댓글 삭제에 성공하였습니다.";
			}
		
		} else {
			
			msg = "댓글 작성자만 삭제가 가능합니다.";
		} //
		
		log.info("msg : " + msg);
			
		// 원글의 기존 댓글을 모두 가져와야함
		List<BoardVO>  boardList = boardService.boardReplySelect(boardReRef);
				
		map.put("msg", msg);
		map.put("boardList", boardList);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	} //
		
}

