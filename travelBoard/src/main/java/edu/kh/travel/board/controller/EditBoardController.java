package edu.kh.travel.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Country;
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
	
//	@GetMapping("{contiCode:[A-Z]{2}}/insert")
	@GetMapping("boardWrite")
	public String insertBoard(
			@RequestParam("contiCode") String contiCode,
			Model model
			) {
		//해당 대륙에 있는 대륙 이름,코드 얻어오는 서비스 호출
		List<Country> countryList = service.countryList(contiCode);
		model.addAttribute("contiCode", contiCode);
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
}
