package edu.kh.travel.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Country;
import edu.kh.travel.board.model.service.BoardService;
import edu.kh.travel.board.model.service.EditBoardService;
import edu.kh.travel.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("editBoard")
@Controller
@RequiredArgsConstructor
@Slf4j
public class EditBoardController {
//	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}") // /board/1/1998?cp=1 이런 식으로 요청 온다(상세 조회 요청 주소 모양)
//	@GetMapping("/{selectContinent:[A-Z]{2}}")
	private final EditBoardService service;
	private final BoardService boardService;
//	@GetMapping("{contiCode:[A-Z]{2}}/insert")
	@GetMapping("boardWrite")
	public String insertBoard(
			@RequestParam("contiCode") String contiCode,
			Model model
			) {
		//해당 대륙에 있는 대륙 이름,코드 얻어오는 서비스 호출
		List<Country> countryList2 = service.countryList(contiCode);
		model.addAttribute("contiCode", contiCode);
		model.addAttribute("countryList2", countryList2);
		
		List<Country> countryList = boardService.countryList(contiCode);
		//넘어온 contiCode 값에 맞는 게시판의 게시글 목록 조회하는 서비스 호출
		model.addAttribute("countryList", countryList);
		return "board/boardWrite";
	}
	
	@PostMapping("insert")
	public String insertBoard(
			/*@ModelAttribute*/ Board inputBoard,
			@RequestParam("countryNameSelect") String countryCode,
			@SessionAttribute("loginMember") Member loginMember, //게시글을 누가 썼는지 알려고
			@RequestParam("images") List<MultipartFile> images, //제출되는 name값이 같으면 배열이나 리스트로 반환된다!!!
			//파일을 아무것도 선택 안한경우 이 리스트가 안만들어지는 게 아니라 빈칸으로 파일 개수대로 제출된다
			RedirectAttributes ra, //게시글 작성 성공/실패 시 작성한 게시글의 상세조회로 리다이렉트 시 request scope로 데이터 전달용
			@RequestParam("contiCode") String contiCode
			) throws IllegalStateException, IOException {
		// 1. boardCode, 로그인한 회원 번호를 inputBoard에 세팅하기(필요한 데이터 하나로 묶기)
				inputBoard.setCountryCode(countryCode);
				inputBoard.setMemberNo(loginMember.getMemberNo());
				
				// 2. 서비스 메서드 호출 후 결과 반환 받기
				// insert 할거여서 result가 int로 나오는데 그걸로 안할거다
				//  -> 성공 시 [상세 조회]를 요청할 수 있도록 삽입된 게시글 번호 반환 받기
				//여기서는 서비스 호출 결과를 result가 아닌 boardNo로 받겠다
				int boardNo = service.boardInsert(inputBoard, images);
				
				// 3. 서비스 결과에 따라 message, 리다이렉트 경로 지정하기
				String path = null;
				String message=null;
				if(boardNo>0) {
					//실패하면 0이 반환돼서
					//성공 시 무조건 0보다 큰 게 반환된다
					
					//http://localhost/board/AS/6?cp=1
					path="/board/"+contiCode+"/"+boardNo; //상세 조회하는 경로
					message="게시글이 작성되었습니다.";
				}else {
					//실패한 경우
					path="insert";
					message="게시글 작성 실패..";
				}
				ra.addFlashAttribute("message", message);
				
				
				//게시글 작성(insert) 성공 시 작성된 글 상세 조회로 리다이렉트
				//상세조회하려면 boardCode, boardNo가 필요했다
				//작성한 글의 boardNo 가져와야된다
				return "redirect:"+path;
	}
	//게시글 수정 화면으로 전환
	@GetMapping("{contiCode:[A-Z]{2}}/{boardNo:[0-9]+}/update")
	public String update(
			@PathVariable("contiCode") String contiCode,
			@PathVariable("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember,
			Model model,
			RedirectAttributes ra
			
			) {
		/* //현재 주소 : http://localhost/board/AS/6?cp=1
        location.href=location.pathname.replace('board', 'editBoard') //get방식
                        +"/update"+ location.search;
		 * 
		 * */
		//해당 게시글 존재하는지 조회
		Map<String, Object> map = new HashMap<>();
		map.put("contiCode", contiCode);
		map.put("boardNo", boardNo);
		
		Board board = boardService.selectOne(map);
		String message = null;
		String path = null;
		if(board ==null) {
			message="게시글이 존재하지 않습니다.";
			path="redirect:/board/"+contiCode+"/"+boardNo; //상세 조회하는 경로
			ra.addFlashAttribute("message", message);
		}else if(board.getMemberNo() !=loginMember.getMemberNo()) {
			message="자신이 작성한 글만 수정할 수 있습니다.";
			path="redirect:/board/"+contiCode+"/"+boardNo; //상세 조회하는 경로
			ra.addFlashAttribute("message", message);
		}else {
			//정상적인 경우
			//해당 대륙에 있는 대륙 이름,코드 얻어오는 서비스 호출
			List<Country> countryList2 = service.countryList(contiCode);
			path = "board/boardUpdate";
			model.addAttribute("board", board);
			List<Country> countryList = boardService.countryList(contiCode);
			//넘어온 contiCode 값에 맞는 게시판의 게시글 목록 조회하는 서비스 호출
			model.addAttribute("countryList", countryList);
			model.addAttribute("countryList2", countryList2);
		}
		return path;
	}
	
	//게시글 수정
	@PostMapping("{contiCode:[A-Z]{2}}/{boardNo:[0-9]+}/update")
	public String update(
			@PathVariable("contiCode") String contiCode,
			@PathVariable("boardNo") int boardNo,
			Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra,
			//새로 추가돼서 넘어온 두 개!
			@RequestParam(value="deleteOrder", required=false) String deleteOrder ,
			@RequestParam(value="querystring", required=false,defaultValue = "") String querystring ,
			@RequestParam("countryNameSelect") String countryCode
			) throws IllegalStateException, IOException {
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		inputBoard.setCountryCode(countryCode);
		
		int result = service.boardUpdate(inputBoard, images, deleteOrder);
		String message=null;
		String path=null;
		if(result>0) {
			message="게시글이 수정 되었습니다.";
			path = "redirect:/board/"+contiCode+"/"+boardNo; //상세 조회하는 경로
		}else {
			message="게시글 수정 실패...";
			path ="redirect:update"; //수정 화면으로 전환
		}
		ra.addFlashAttribute("message", message);
		return path;
	}
	
	//게시글 삭제
	@PostMapping("{contiCode:[A-Z]+}/{boardNo:[0-9]+}/delete")
	public String boardDelete(
			@PathVariable("contiCode") String contiCode,
			@PathVariable("boardNo") int boardNo,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra
			) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("contiCode", contiCode);
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMember.getMemberNo());
		
		int result = service.boardDelete(map);
		String message= null;
		String path = null;
		if(result>0) {
			path = String.format("/board/%s", contiCode);
			message="삭제 되었습니다.";
		}else {
			path = String.format("/board/%s/%d?cp=%d", contiCode,boardNo,cp);
			message="삭제 실패";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:"+path;
	}
}
